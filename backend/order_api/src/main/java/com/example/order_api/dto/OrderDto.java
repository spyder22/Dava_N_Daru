package com.example.order_api.dto;

import java.util.List;

public class OrderDto {

    private Long orderId;

    List<Details> details;

    private Double grandTotal;
}
