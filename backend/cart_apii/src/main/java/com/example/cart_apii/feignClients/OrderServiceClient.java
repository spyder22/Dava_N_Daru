package com.example.cart_apii.feignClients;

import com.example.cart_apii.payloads.request.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-api")
public interface OrderServiceClient {

    @PostMapping("/order/execute")
    public String executeOrder(@RequestBody OrderDto orderDto);
}
