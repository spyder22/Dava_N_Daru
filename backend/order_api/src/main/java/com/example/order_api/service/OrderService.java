package com.example.order_api.service;

import com.example.order_api.dto.Details;
import com.example.order_api.dto.OrderDto;
import com.example.order_api.dto.ProductMerchantDto;
import com.example.order_api.dto.Products;
import com.example.order_api.entity.Order;
import com.example.order_api.payloads.request.OrderMailId;
import com.example.order_api.payloads.request.ProductMerchantId;
import com.example.order_api.payloads.response.OrderItems;
import com.example.order_api.payloads.response.OrderOfUser;
import com.example.order_api.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    private Order convertOrderDtoToOrder(OrderDto orderDto)
    {
        Order order=new Order();
        BeanUtils.copyProperties(orderDto,order);
        return order;
    }

    public void executeOrder(OrderDto orderDto)
    {
        try {
            orderRepository.save(convertOrderDtoToOrder(orderDto));
        }
        catch (Exception ex)
        {
            System.out.println("error adding order");
            //return something to tell if order execution failed
            ex.printStackTrace();
        }

    }


    public OrderOfUser getAllOrdersByUserEmail(String userEmail)
    {
        //get orderids from user table
        List<Long> orderIds=null;
        if(orderIds !=null)
        {
            OrderOfUser orderOfUser=new OrderOfUser();
            List<OrderItems> orderItems=new ArrayList<>();
            for(Long id:orderIds)
            {
                OrderMailId orderMailId=new OrderMailId(id,userEmail);
                orderItems.add(getOrderItems(orderMailId));
            }
            orderOfUser.setUserEmail(userEmail);
            orderOfUser.setOrderList(orderItems);
            return orderOfUser;
        }
        return null;


    }
    public OrderItems getOrderItems(OrderMailId orderMailId)
    {
        Long orderId=orderMailId.getOrderId();

        Optional<Order> response=orderRepository.findById(orderId);
        if(response.isPresent())
        {
            double grandTotal=0;
            Order order=response.get();
            OrderItems orderItem=new OrderItems();

            List<Details> details=order.getDetails();
            if(details!=null) {
                List<Products> productsList = new ArrayList<>();
                for (Details item : details) {
                    ProductMerchantId productMerchantId = new ProductMerchantId(item.getProductId(), item.getMerchantId());
                    // TODO: 26/01/22 get ProductMerchantDto from productApi findByProductMerchantId()
                    ProductMerchantDto productMerchantDto = null;
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

    private Products convertFromProductMerchantDtoToProducts(ProductMerchantDto productMerchantDto,Long quantity)
    {
        Products products=new Products();

        try
        {
            products.setImage(productMerchantDto.getImage());
            products.setMerchantId(productMerchantDto.getMerchant().getMerchantId());
            products.setPrice(productMerchantDto.getMerchant().getPrice());
            products.setProductName(productMerchantDto.getProductName());
            products.setProductId(productMerchantDto.getProductId());
            products.setQuantity(quantity);
            products.setUsp(productMerchantDto.getMerchant().getUsp());
            products.setProductRating(productMerchantDto.getMerchant().getProductRating());
            products.setTotal(quantity*productMerchantDto.getMerchant().getPrice());
            return products;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;


    }



}
