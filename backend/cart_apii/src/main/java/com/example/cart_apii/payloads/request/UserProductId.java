package com.example.cart_apii.payloads.request;

public class UserProductId {
    private String userEmail;
    private String productId;

    public UserProductId() {
    }

    public UserProductId(String userEmail, String productId) {
        this.userEmail = userEmail;
        this.productId = productId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
