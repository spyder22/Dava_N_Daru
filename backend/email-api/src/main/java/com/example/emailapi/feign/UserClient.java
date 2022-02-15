package com.example.emailapi.feign;

import com.example.emailapi.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-api")
public interface UserClient {

    @GetMapping("/usermail/{id}")
    public String getUserEmailById(@PathVariable Integer id);
}
