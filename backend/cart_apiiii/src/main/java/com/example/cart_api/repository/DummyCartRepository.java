package com.example.cart_api.repository;

import com.example.cart_api.entity.DummyCart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DummyCartRepository extends MongoRepository<DummyCart,String> {
}
