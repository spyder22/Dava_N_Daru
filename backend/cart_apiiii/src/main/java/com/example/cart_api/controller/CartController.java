package com.example.cart_api.controller;


import com.example.cart_api.dto.CartDto;
import com.example.cart_api.dto.ProductDto;
import com.example.cart_api.entity.Cart;
import com.example.cart_api.payloads.request.CartItem;
import com.example.cart_api.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/{id}")
    public ProductDto getByProductId(@PathVariable String id)
    {
        return cartService.getProductById(id);
    }


    @PostMapping("/add")
    public void addToCart(@RequestBody CartItem cartItem)
    {
        cartService.addProducts(cartItem);
        System.out.println("Success***********");
    }

    @GetMapping("/get/{mail}")
    public CartDto getCartItems(@PathVariable("mail") String mail)
    {
        return  cartService.getCartProductsByMail(mail);
    }

    @DeleteMapping("/delete")
    public void removeProduct(@RequestBody CartItem cartItem)
    {
        cartService.removeProduct(cartItem);
    }
}
