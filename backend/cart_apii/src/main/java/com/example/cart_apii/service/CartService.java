package com.example.cart_apii.service;

import com.example.cart_apii.dto.AddToCart;
import com.example.cart_apii.dto.CartDto;
import com.example.cart_apii.payloads.request.UserProductId;
import com.example.cart_apii.payloads.response.CartRespnose;
import com.example.cart_apii.payloads.response.ProductMerchantDto;

import java.util.List;

public interface CartService {
      String addToCart(AddToCart addToCart);
      List<CartRespnose> getCartItems();

     CartRespnose getCartItemsByMail(String mail);

     void removeFromCart(UserProductId userProductId);
     void executeOrder(String mail);



}
