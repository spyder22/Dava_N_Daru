package com.example.merchant_apii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class MerchantApiiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerchantApiiApplication.class, args);
	}

}
