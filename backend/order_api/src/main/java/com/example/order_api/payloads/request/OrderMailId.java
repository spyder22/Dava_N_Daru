package com.example.order_api.payloads.request;

public class OrderMailId {
    private Long orderId;
    private String userEmail;

    public OrderMailId() {
    }

    public OrderMailId(Long orderId, String userEmail) {
        this.orderId = orderId;
        this.userEmail = userEmail;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
