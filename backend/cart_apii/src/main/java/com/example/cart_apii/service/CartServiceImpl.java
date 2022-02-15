package com.example.cart_apii.service;

import com.example.cart_apii.dto.AddToCart;
import com.example.cart_apii.dto.CartDto;
import com.example.cart_apii.dto.CartProducts;
import com.example.cart_apii.entity.Cart;
import com.example.cart_apii.feignClients.OrderServiceClient;
import com.example.cart_apii.feignClients.ProductServiceClient;
import com.example.cart_apii.payloads.request.*;
import com.example.cart_apii.payloads.response.CartRespnose;
import com.example.cart_apii.payloads.response.ProductMerchantDto;
import com.example.cart_apii.repository.CartRepository;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements  CartService{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    OrderServiceClient orderServiceClient;

    @Autowired
    ProductServiceClient productServiceClient;

    @Autowired
    RabbitTemplate template;

    @Autowired
    Binding orderBinding;


    public Cart findByUserEmail(String userEmail)
    {
        System.out.println(userEmail+"---------findbyuseremail");
        return  cartRepository.findByUserEmail(userEmail);

    }

    private CartDto convertFromAddToCartToCartDto(AddToCart addToCart)
    {
        CartDto cartDto=new CartDto();
        CartProducts cartProducts=new CartProducts();
        cartProducts.setProductId(addToCart.getProductId());
        cartProducts.setMerchantId(addToCart.getMerchantId());
        cartProducts.setQuantity(addToCart.getQuantity());
        cartDto.setUserEmail(addToCart.getUserEmail());
        cartDto.setCartProduct(cartProducts);
        return cartDto;
    }


    private  ProductMerchantDto getProductMerchantDto(ProductMerchantId productMerchantId)
    {
         return productServiceClient.findByProductMerchantId(productMerchantId);
    }



    public Integer isStockPresent(Details details)
    {
        System.out.println(details.getMerchantId()+"--------"+details.getProductId());
        ProductMerchantDto productMerchantDto=getProductMerchantDto(new ProductMerchantId(details.getProductId(),details.getMerchantId()));
        productMerchantDto.getMerchant().getMerchantName();
        if(productMerchantDto.getMerchant().getStock()>=details.getQuantity())
        {
         return 1;
        }
        return 0;


    }


    //todo : see need for the maintaining grand total
    @Override
    @Transactional
    public String addToCart(AddToCart addToCart)
    {
        System.out.println(addToCart+"addtocart-----");
        CartDto cartDto=convertFromAddToCartToCartDto(addToCart);
        Cart cart=findByUserEmail(cartDto.getUserEmail());

        System.out.println(cart);
        ProductMerchantDto responseEntity=getProductMerchantDto(new ProductMerchantId(addToCart.getProductId(),addToCart.getMerchantId()));
        cartDto.getCartProduct().setImage(responseEntity.getImage());
        cartDto.getCartProduct().setProductName(responseEntity.getProductName());
        cartDto.getCartProduct().setPrice(responseEntity.getMerchant().getPrice());

        if(cart!=null)
        {
            double subTotal=0;
            double grandTotal=cart.getGrandTotal();
            List<CartProducts> cartProducts = cart.getCartProductList();
            CartProducts product=cartDto.getCartProduct();
            if(responseEntity.getMerchant().getStock()<cartDto.getCartProduct().getQuantity())
            {
                System.out.println("stock unavialable ");
                return "stock Unavailable";
            }
            cartDto.getCartProduct().setMerchantName(responseEntity.getMerchant().getMerchantName());

            if (!cartProducts.contains(cartDto.getCartProduct())) {
                subTotal=responseEntity.getMerchant().getPrice()*addToCart.getQuantity();
                grandTotal=grandTotal+subTotal;
                cartDto.getCartProduct().setSubtotal(subTotal);
                cartProducts.add(cartDto.getCartProduct());
            }
            else
            {
                int oldQuantity=cartProducts.get(cartProducts.indexOf(cartDto.getCartProduct())).getQuantity();
                int newQuantity=addToCart.getQuantity();
                if(oldQuantity!=newQuantity)
                {
                    subTotal=(newQuantity-oldQuantity)*responseEntity.getMerchant().getPrice();
                    grandTotal=grandTotal+subTotal;
                    subTotal=responseEntity.getMerchant().getPrice()*newQuantity;
                }
                else {
                    subTotal=oldQuantity*responseEntity.getMerchant().getPrice();
                }

                cartDto.getCartProduct().setSubtotal(subTotal);
                cartProducts.set(cartProducts.indexOf(cartDto.getCartProduct()),cartDto.getCartProduct());
            }
            Cart addCart=new Cart();
            BeanUtils.copyProperties(cart,addCart);
            addCart.setCartProductList(cartProducts);
            addCart.setGrandTotal(grandTotal);
            cartRepository.save(addCart);

            System.out.println("successfully update existing Cart");
            return "Update success";
        }
        else
        {
            Cart newCart=new Cart();
            BeanUtils.copyProperties(cartDto,newCart);
            List<CartProducts> cartProductsList=new ArrayList<>();
            CartProducts product=cartDto.getCartProduct();
            double subTotal=0;
            if(responseEntity.getMerchant()!=null)
            {
                subTotal=responseEntity.getMerchant().getPrice()*addToCart.getQuantity();
                product.setMerchantName(responseEntity.getMerchant().getMerchantName());
                System.out.println("subtotal of new product"+subTotal);
                product.setSubtotal(subTotal);
            }
            cartProductsList.add(product);

            newCart.setCartProductList(cartProductsList);
            newCart.setGrandTotal(subTotal);
            cartRepository.save(newCart);

            System.out.println("sucessfully created new item");
            return  "success";

        }
    }



    // todo : add the logic to reduce the grand total value---done
    @Override
    public void removeFromCart(UserProductId userProductId)
    {
        Cart cart=findByUserEmail(userProductId.getUserEmail());
        if(cart!=null)
        {
            List<CartProducts> cartProducts=cart.getCartProductList();
            for(CartProducts product:cartProducts)
            {
                if(product.getProductId().equals(userProductId.getProductId()))
                {
                    double removeSubTotal=product.getSubtotal();
                    cartProducts.remove(cartProducts.indexOf(product));
                    cart.setCartProductList(cartProducts);
                    cart.setGrandTotal(cart.getGrandTotal()-removeSubTotal);
                    cartRepository.save(cart);
                    break;
                }
            }

        }
    }



    @Override
    public List<CartRespnose> getCartItems()
    {
        List<Cart> cartList=cartRepository.findAll();
        if(cartList!=null)
        {
            List<CartRespnose> cartRespnoses=new ArrayList<>();
            for (Cart cart:cartList)
            {
                CartRespnose cartRespnose=new CartRespnose();
                BeanUtils.copyProperties(cart,cartRespnose);
                cartRespnoses.add(cartRespnose);
            }
            return cartRespnoses;
        }
        return null;
    }


    @Override
    public CartRespnose getCartItemsByMail(String mail)
    {

        Cart cart=cartRepository.findByUserEmail(mail);
        if(cart!=null)
        {
            CartRespnose cartRespnose=new CartRespnose();
            BeanUtils.copyProperties(cart,cartRespnose);
            return cartRespnose;
        }
        System.out.println("user doest not exits");
        return null;
    }

    @Override
    public void executeOrder(String mail)
    {
        Cart cart=cartRepository.findByUserEmail(mail);
        if(cart!=null)
        {
            OrderDto orderDto=convertFromCartToOrderDto(cart);
            // TODO: 29/01/22 add to rabbitmq

//            template.convertAndSend(orderBinding.getExchange(),orderBinding.getRoutingKey(),orderDto);

            String status=orderServiceClient.executeOrder(orderDto);
            // TODO: 29/01/22  if sucess delette cart
            if(status.equals("success"))
            {
                cartRepository.delete(cart);
            }
        }
    }


//    @RabbitListener(queues = "order_executed_queue")
    public void deleteCart(String mail)
    {
        Cart cart=cartRepository.findByUserEmail(mail);
        if(cart!=null)
        {
            cartRepository.delete(cart);
        }
    }

//
//    public Long getStock(ProductMerchantId productMerchantId)
//    {
//        System.out.println("getStock");
//        ProductMerchantDto productMerchantDto=getProductMerchantDto(productMerchantId);
//        if(productMerchantDto!=null)
//        {
//            return productMerchantDto.getMerchant().getStock();
//        }
//        return null;
//    }


    private OrderDto convertFromCartToOrderDto(Cart cart)
    {
        OrderDto orderDto=new OrderDto(cart.getUserEmail(),cart.getGrandTotal());
        List<Details> detailsList=new ArrayList<>();

        List<CartProducts> cartProductList=cart.getCartProductList();
        for(CartProducts cartProducts:cartProductList)
        {
            Details details=new Details();
            BeanUtils.copyProperties(cartProducts,details);
            details.setSubTotal(cartProducts.getSubtotal());
            detailsList.add(details);
        }
        orderDto.setDetails(detailsList);
        return orderDto;

    }



}
