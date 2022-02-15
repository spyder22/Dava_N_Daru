package com.example.order_api.controller;

import com.example.order_api.dto.OrderDto;
import com.example.order_api.payloads.request.BuyNow;
import com.example.order_api.payloads.request.OrderMailId;
import com.example.order_api.payloads.response.OrderItems;
import com.example.order_api.payloads.response.OrderOfUser;
import com.example.order_api.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*",maxAge = 3600)
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderServiceImpl orderServiceImpl;

    @PostMapping("/execute")
    public String executeOrder(@RequestBody OrderDto orderDto)
    {

        System.out.println("execute-------"+orderDto.getEmail());
        return orderServiceImpl.executeOrder(orderDto);
    }
    @PostMapping("/buynow")
    public void buyNow(@RequestBody BuyNow buyNow)
    {
        orderServiceImpl.buyNow(buyNow);
    }


    @GetMapping("/getAll/{email}")
    public OrderOfUser getOrderByUserEmail(@PathVariable String email)
    {
        System.out.println("email======"+email);
        return  orderServiceImpl.getAllOrdersByUserEmail(email);

    }


    @GetMapping("/getById")
    public OrderItems getByOrderId(OrderMailId orderMailId)
    {
        return orderServiceImpl.getOrderItems(orderMailId);
    }

    @PostMapping
    public void sendEmail()
    {
        orderServiceImpl.sendEmailMessage();

    }


}
