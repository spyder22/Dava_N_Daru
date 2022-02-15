package com.example.order_api.feignClients;

import com.example.order_api.payloads.request.UserOrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-api")
public interface UserServiceClient {

    @GetMapping("/user/orderIds/{email}")
     List<String> getOrderIdsByUserEmail(@PathVariable(value = "email") String email);

    @PostMapping("/user/addOrder")
     void addOrderIdToUser(@RequestBody UserOrderDto userOrderDto);

}
