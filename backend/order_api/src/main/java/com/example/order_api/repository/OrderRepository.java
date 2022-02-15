package com.example.order_api.repository;

import com.example.order_api.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends MongoRepository<Order,String> {
    List<Order> findByEmail(String email);
}
