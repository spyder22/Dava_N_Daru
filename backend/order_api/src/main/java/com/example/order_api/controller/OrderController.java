package com.example.order_api.controller;

import com.example.order_api.dto.OrderDto;
import com.example.order_api.payloads.request.OrderMailId;
import com.example.order_api.payloads.response.OrderItems;
import com.example.order_api.payloads.response.OrderOfUser;
import com.example.order_api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/execute")
    public void addOrder(@RequestBody OrderDto orderDto)
    {
        orderService.executeOrder(orderDto);
    }

    @GetMapping("/getAll")
    public OrderOfUser getOrdereByUserEmail(@RequestBody String userEmail)
    {
        return  orderService.getAllOrdersByUserEmail(userEmail);

    }


    @GetMapping("/getById")
    public OrderItems getByOrderId(OrderMailId orderMailId)
    {
        return orderService.getOrderItems(orderMailId);
    }




}
