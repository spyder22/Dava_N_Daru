package com.example.order_api.service;

import com.example.order_api.dto.*;
import com.example.order_api.entity.Order;
import com.example.order_api.feignClients.ProductServiceClient;
import com.example.order_api.feignClients.UserServiceClient;
import com.example.order_api.payloads.request.*;
import com.example.order_api.payloads.response.OrderItems;
import com.example.order_api.payloads.response.OrderOfUser;
import com.example.order_api.rabbit.OrderRabbit;
import com.example.order_api.rabbit.OrderStatus;
import com.example.order_api.rabbit.config.EmailDto;
import com.example.order_api.rabbit.config.MessagingConfig;
import com.example.order_api.repository.OrderRepository;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductServiceClient productServiceClient;

    @Autowired
    UserServiceClient userServiceClient;

    @Autowired
    RabbitTemplate template;

    @Autowired
    Binding emailBinding;


    @Autowired
    Binding cartBinding;





    private Order convertOrderDtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        return order;
    }

    private UpdateProduct convertToUpdateProduct(ProductMerchantDto productMerchantDto) {
        System.out.println(productMerchantDto.getMerchant().getProductRating());
        UpdateProduct updateProduct = new UpdateProduct();
        updateProduct.setMerchantId(productMerchantDto.getMerchant().getMerchantId());
        updateProduct.setPrice(productMerchantDto.getMerchant().getPrice());
        updateProduct.setProductId(productMerchantDto.getProductId());
        updateProduct.setStock(productMerchantDto.getMerchant().getStock());
        updateProduct.setUsp(productMerchantDto.getMerchant().getUsp());
        updateProduct.setProductRating(productMerchantDto.getMerchant().getProductRating());

        return updateProduct;

    }


    private ProductMerchantDto getProductMerchantDto(ProductMerchantId productMerchantId) {

        return productServiceClient.findByProductMerchantId(productMerchantId);

    }


    @Transactional
    @Override
    public String executeOrder(OrderDto orderDto) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);

        List<Details> detailsList = order.getDetails();
        if (detailsList != null) {
            for (Details details : detailsList) {
                System.out.println("pid---"+details.getProductId()+"  mid---"+details.getMerchantId());
                ProductMerchantDto productMerchantDto = getProductMerchantDto(new ProductMerchantId(details.getProductId(), details.getMerchantId()));

                System.out.println(productMerchantDto+"-----pmdto");
                System.out.println(productMerchantDto.getMerchant()+" pmdto" +"details ----"+details.getQuantity());

                System.out.println("1"+productMerchantDto.getMerchant().getStock());
                System.out.println("2"+productMerchantDto.getOrderCount());


                if (productMerchantDto.getMerchant().getStock() >= details.getQuantity()) {
                    productMerchantDto.getMerchant().setStock(productMerchantDto.getMerchant().getStock() - details.getQuantity());
                    productMerchantDto.setOrderCount(productMerchantDto.getOrderCount() + details.getQuantity());
                    System.out.println("inside---stocke greater");


                    if (productMerchantDto.getMerchant().getProductRating() < 4.9) {
                        productMerchantDto.getMerchant().setProductRating(productMerchantDto.getMerchant().getProductRating() + 0.04);
                    }
                    // todo : move this call in async execution
                    try {
                        UpdateProduct updateProduct=convertToUpdateProduct(productMerchantDto);
                        productServiceClient.updateProduct(updateProduct);

                    } catch (Exception ex) {
                        System.out.println("error updateing product in Product-api");
                        ex.printStackTrace();
                        return "failure";
                    }
                } else {
                    return "stock not available";
                }
            }

            try {
                orderRepository.insert(order);
                userServiceClient.addOrderIdToUser(new UserOrderDto(order.getOrderId(), orderDto.getEmail()));


                // TODO: 29/01/22 send email confirmation using rabbit or email service client
//                template.convertAndSend(emailBinding.getExchange(),emailBinding.getRoutingKey(),new EmailDto("mai nhi btaunga","do not open","kishan8929@gmail.com"));

                System.out.println("*******************Order Executed Successfullyy*****************");
                System.out.println("*********Email Sent*******");
                return "success";
            } catch (Exception e) {
                e.printStackTrace();
                return "failure";
            }
        }
        return "failure";

    }


    @Override
    public OrderOfUser getAllOrdersByUserEmail(String userEmail) {
        //get orderids from user table
        // todo : change this to load using orderRepository.findByEmail
        List<String> orderIds = userServiceClient.getOrderIdsByUserEmail(userEmail);

        if (orderIds != null) {
            OrderOfUser orderOfUser = new OrderOfUser();
            List<OrderItems> orderItems = new ArrayList<>();
            for (String id : orderIds) {
                OrderMailId orderMailId = new OrderMailId(id, userEmail);
                orderItems.add(getOrderItems(orderMailId));
            }
            orderOfUser.setUserEmail(userEmail);
            orderOfUser.setOrderList(orderItems);
            return orderOfUser;
        }
        System.out.println("No items present");
        return null;

    }


    @Override
    public OrderItems getOrderItems(OrderMailId orderMailId) {
        String orderId = orderMailId.getOrderId();

        Optional<Order> response = orderRepository.findById(orderId);
        if (response.isPresent()) {
            double grandTotal = 0;
            Order order = response.get();
            OrderItems orderItem = new OrderItems();

            List<Details> details = order.getDetails();
            if (details != null) {
                List<Products> productsList = new ArrayList<>();
                for (Details item : details) {
                    ProductMerchantDto productMerchantDto = getProductMerchantDto(new ProductMerchantId(item.getProductId(), item.getMerchantId()));
                    if (productMerchantDto != null) {
                        Products temp = convertFromProductMerchantDtoToProducts(productMerchantDto, item.getQuantity());
                        grandTotal = grandTotal + temp.getTotal();
                        productsList.add(temp);

                    }
                }
                orderItem.setUserEmail(orderMailId.getUserEmail());
                orderItem.setOrderId(orderId);
                orderItem.setGrandTotal(grandTotal);
                orderItem.setProductsList(productsList);
                return orderItem;
            }
        }
        return null;
    }



    @Override
    public String buyNow(BuyNow buyNow) {


        ProductMerchantDto productMerchantDto = productServiceClient.findByProductMerchantId(new ProductMerchantId(buyNow.getProductId(), buyNow.getMerchantId()));


        if (productMerchantDto != null) {
            OrderDto orderDto = new OrderDto();
            List<Details> detailsList = new ArrayList<>();
            Details detail = new Details();

            detail.setMerchantId(buyNow.getMerchantId());
            detail.setProductId(buyNow.getProductId());
            detail.setQuantity(buyNow.getQuantity());
            double price = 0;

            MerchantDto merchantDto = productMerchantDto.getMerchant();
            if (merchantDto != null) {
                if (buyNow.getQuantity() <= merchantDto.getStock()) {
                    price = buyNow.getQuantity() * merchantDto.getPrice();
                    detail.setSubTotal(price);


                    detailsList.add(detail);
                    orderDto.setDetails(detailsList);
                    orderDto.setGrandTotal(price);
                    orderRepository.save(convertOrderDtoToOrder(orderDto));

                    if (productMerchantDto.getOrderCount() == null) {
                        productMerchantDto.setOrderCount(0);
                    }

                    productMerchantDto.getMerchant().setStock(productMerchantDto.getMerchant().getStock() - buyNow.getQuantity());
                    productMerchantDto.setOrderCount(productMerchantDto.getOrderCount() + buyNow.getQuantity());

                    if (productMerchantDto.getMerchant().getProductRating() < 4.9) {
                        productMerchantDto.getMerchant().setProductRating(productMerchantDto.getMerchant().getProductRating() + 0.04);
                    }
                    try {
                        productServiceClient.updateProduct(convertToUpdateProduct(productMerchantDto));
                        // TODO: 29/01/22 send email to user using rabbitmq or emailserviceclient
                        template.convertAndSend(emailBinding.getExchange(),emailBinding.getRoutingKey(),new EmailDto("mai nhi btaunga","do not open","kishan8929@gmail.com"));

                        return "sucess";
                    } catch (Exception ex) {
                        System.out.println("error updateing product in Product-api");
                        ex.printStackTrace();
                        return "failure";
                    }
                }
            }
        }

        return "failure";

    }



    private Products convertFromProductMerchantDtoToProducts(ProductMerchantDto productMerchantDto, Long quantity) {
        Products products = new Products();

        try {
            products.setImage(productMerchantDto.getImage());
            products.setMerchantId(productMerchantDto.getMerchant().getMerchantId());
            products.setPrice(productMerchantDto.getMerchant().getPrice());
            products.setProductName(productMerchantDto.getProductName());
            products.setProductId(productMerchantDto.getProductId());
            products.setQuantity(quantity);
            products.setUsp(productMerchantDto.getMerchant().getUsp());
            products.setProductRating(productMerchantDto.getMerchant().getProductRating());
            products.setTotal(quantity * productMerchantDto.getMerchant().getPrice());
            return products;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;

    }


    public void sendEmailMessage()
    {
        template.convertAndSend(emailBinding.getRoutingKey(),new EmailDto("mai nhi btaunga","do not open","kishan8929@gmail.com"));
        System.out.println("done");
    }




}














//    @Transactional
//    @Override
//    public String executeOrder(OrderDto orderDto) {
////        Order order = new Order();
////        BeanUtils.copyProperties(orderDto, order);
////        try {
////            orderRepository.insert(order);
////            userServiceClient.addOrderIdToUser(new UserOrderDto(order.getOrderId(), orderDto.getEmail()));
////        } catch (Exception e) {
////            return "failure";
////        }
////
////        // TODO: 29/01/22 send to queue or use EmailServiceClient
//////        template.convertAndSend(MessagingConfig.Exchange,MessagingConfig.Rounting_Key,new EmailDto("Order Placed"+order.getOrderId(),"success",orderDto.getEmail()));
////
////        return "success";
////
////    }
//
//
//        Order order = new Order();
//        List<Details> orderDtoDetails = orderDto.getDetails();
//        List<Details> executeOrderDetails = new ArrayList<>();
//
////        double grandTotal = 0;
//
//
//        for (Details item : orderDtoDetails) {
////            double subTotal = 0;
//            ProductMerchantId productMerchantId = new ProductMerchantId(item.getProductId(), item.getMerchantId());
//            ProductMerchantDto productMerchantDto = productServiceClient.findByProductMerchantId(productMerchantId);
//
//            if (productMerchantDto.getMerchant().getStock() >= item.getQuantity()) {
//                Details newDetails = new Details();
//                BeanUtils.copyProperties(item, newDetails);
////                subTotal = subTotal + (item.getQuantity() * productMerchantDto.getMerchant().getPrice());
////                newDetails.setSubTotal(subTotal);
//                executeOrderDetails.add(newDetails);
//
////                grandTotal = grandTotal + subTotal;
//                productMerchantDto.getMerchant().setStock(productMerchantDto.getMerchant().getStock() - item.getQuantity());
//                productMerchantDto.setOrderCount(productMerchantDto.getOrderCount() + item.getQuantity());
//
//                if (productMerchantDto.getMerchant() == null) {
//                    productMerchantDto.getMerchant().setProductRating(0);
//                    System.out.println("print null");
//
//                }
//
//                if (productMerchantDto.getMerchant().getProductRating() < 4.9) {
//                    productMerchantDto.getMerchant().setProductRating(productMerchantDto.getMerchant().getProductRating() + 0.04);
//                }
//                try {
//                    productServiceClient.updateProduct(convertToUpdateProduct(productMerchantDto));
//
//                } catch (Exception ex) {
//                    System.out.println("error updateing product in Product-api");
//                    ex.printStackTrace();
//                    return "failure";
//                }
//            } else {
//                //throw exception
//                //transaction should fail
//                System.out.println("Stock not available");
//                return "failure";
//            }
//
//        }
//        order.setDetails(executeOrderDetails);
//
//
//        try {
//            orderRepository.insert(order);
//            System.out.println("order id is " + order.getOrderId());
//
//            userServiceClient.addOrderIdToUser(new UserOrderDto(order.getOrderId(), orderDto.getEmail()));
//
//            template.convertAndSend(MessagingConfig.Exchange, MessagingConfig.Rounting_Key, new EmailDto("Order Placed" + order.getOrderId(), "success", orderDto.getEmail()));
//
//            System.out.println("*******************Order Executed Successfullyy*****************");
//            System.out.println("*********Email Sent*******");
//
//
//            return "success";
//        } catch (Exception ex) {
//            System.out.println("error adding order");
//            //return something to tell if order execution failed
//            ex.printStackTrace();
//            return "failure";
//        }
//    }
//





