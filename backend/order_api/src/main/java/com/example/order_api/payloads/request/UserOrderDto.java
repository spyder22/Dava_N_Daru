package com.example.order_api.payloads.request;

public class UserOrderDto {
    private String orderId;
    private String email;

    public UserOrderDto() {
    }

    public UserOrderDto(String orderId, String email) {
        this.orderId = orderId;
        this.email = email;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
