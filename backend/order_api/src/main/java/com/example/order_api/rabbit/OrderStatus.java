package com.example.order_api.rabbit;

import com.example.order_api.entity.Order;

public class OrderStatus {
    private OrderRabbit order;
    private String status;
    private String message;

    public OrderStatus() {
    }

    public OrderStatus(OrderRabbit order, String status, String message) {
        this.order = order;
        this.status = status;
        this.message = message;
    }

    public OrderRabbit getOrder() {
        return order;
    }

    public void setOrder(OrderRabbit order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
