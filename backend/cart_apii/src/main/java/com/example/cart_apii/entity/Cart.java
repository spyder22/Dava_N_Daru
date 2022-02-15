package com.example.cart_apii.entity;

import com.example.cart_apii.dto.CartProducts;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Cart {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userEmail;

    private Double grandTotal;

    private List<CartProducts> cartProductList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public List<CartProducts> getCartProductList() {
        return cartProductList;
    }

    public void setCartProductList(List<CartProducts> cartProductList) {
        this.cartProductList = cartProductList;
    }
}
