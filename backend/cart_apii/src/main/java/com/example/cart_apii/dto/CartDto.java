package com.example.cart_apii.dto;

public class CartDto {
    private String id;
    private String userEmail;
    private Double grandTotal;
    private CartProducts cartProduct;

    public CartDto() {
    }

    public CartDto(String id, String userEmail, double grandTotal, CartProducts cartProduct) {
        this.id = id;
        this.userEmail = userEmail;
        this.grandTotal = grandTotal;
        this.cartProduct = cartProduct;
    }

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

    public CartProducts getCartProduct() {
        return cartProduct;
    }

    public void setCartProduct(CartProducts cartProduct) {
        this.cartProduct = cartProduct;
    }
}
