package com.example.eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

}

//spring.application.name=cart-api
//		server.port=8084
//
//		spring.data.mongodb.host=localhost
//		spring.data.mongodb.port=27017
//		spring.data.mongodb.database=training
//		spring.data.mongodb.repositories.enabled=true
