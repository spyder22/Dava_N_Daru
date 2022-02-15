package com.example.cart_apii.controller;

import com.example.cart_apii.dto.AddToCart;
import com.example.cart_apii.dto.CartDto;
import com.example.cart_apii.payloads.request.Details;
import com.example.cart_apii.payloads.request.ProductMerchantId;
import com.example.cart_apii.payloads.request.UserProductId;
import com.example.cart_apii.payloads.response.CartRespnose;
import com.example.cart_apii.payloads.response.ProductMerchantDto;
import com.example.cart_apii.service.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import java.util.List;



@CrossOrigin(origins="*",maxAge = 3600)
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartServiceImpl cartServiceImpl;

    @PostMapping("/add")
    public String addToCart(@RequestBody AddToCart addToCart)
    {
        System.out.println("request for addtocart"+addToCart.getUserEmail());
        return cartServiceImpl.addToCart(addToCart);

    }

    @GetMapping("/get/{mail}")
    public CartRespnose getCartItemByMail(@PathVariable String mail)
    {
        System.out.println("getCartItemByMail request  "+mail);
        return cartServiceImpl.getCartItemsByMail(mail);
    }


    @PostMapping("/execute/{mail}")
    public synchronized void executeOrder(@PathVariable String mail)
    {
        cartServiceImpl.executeOrder(mail);
    }

    @GetMapping("/get")
    public List<CartRespnose> getCartItems()
    {
        return cartServiceImpl.getCartItems();
    }

    @DeleteMapping("/remove")
    public void removeProduct(@RequestBody UserProductId userProductId)
    {
        cartServiceImpl.removeFromCart(userProductId);
    }

//    @PostMapping("/stock")
//    public Long getStockByProductMerchantId(@RequestBody  ProductMerchantId productMerchantId)
//    {
//        System.out.println(productMerchantId.getMerchantId()+"-------"+productMerchantId.getProductId());
//        return cartServiceImpl.getStock(productMerchantId);
//
//    }

    @GetMapping("/stock")
    public Integer stockPresent(@RequestBody Details details)
    {
        return cartServiceImpl.isStockPresent(details);

    }



}
