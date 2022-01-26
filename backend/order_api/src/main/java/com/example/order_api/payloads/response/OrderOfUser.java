package com.example.order_api.payloads.response;

import com.example.order_api.entity.Order;

import java.util.List;

public class OrderOfUser {
    private String userEmail;
    private List<OrderItems> orderList;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<OrderItems> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderItems> orderList) {
        this.orderList = orderList;
    }
}
