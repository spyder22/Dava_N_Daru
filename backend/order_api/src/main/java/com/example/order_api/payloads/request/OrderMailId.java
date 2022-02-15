package com.example.order_api.payloads.request;

public class OrderMailId {
    private String orderId;
    private String userEmail;

    public OrderMailId() {
    }

    public OrderMailId(String orderId, String userEmail) {
        this.orderId = orderId;
        this.userEmail = userEmail;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
