package com.example.order_api.service;

import com.example.order_api.dto.OrderDto;
import com.example.order_api.payloads.request.BuyNow;
import com.example.order_api.payloads.request.OrderMailId;
import com.example.order_api.payloads.response.OrderItems;
import com.example.order_api.payloads.response.OrderOfUser;

public interface OrderService {


    OrderOfUser getAllOrdersByUserEmail(String userEmail);
    OrderItems getOrderItems(OrderMailId orderMailId);
    String buyNow(BuyNow buyNow);
    String executeOrder(OrderDto orderDto) ;


}
