package com.example.cart_apii.payloads.response;

import com.example.cart_apii.dto.CartProducts;

import java.util.List;

public class CartRespnose {
    private String id;

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

    public CartRespnose() {
    }

    public CartRespnose(String id, String userEmail, Double grandTotal, List<CartProducts> cartProductList) {
        this.id = id;
        this.userEmail = userEmail;
        this.grandTotal = grandTotal;
        this.cartProductList = cartProductList;
    }
}
