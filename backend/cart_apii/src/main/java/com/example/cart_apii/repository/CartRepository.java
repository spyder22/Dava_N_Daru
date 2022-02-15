package com.example.cart_apii.repository;

import com.example.cart_apii.entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository  extends MongoRepository<Cart,String> {
    Cart findByUserEmail(String userEmail);

}
