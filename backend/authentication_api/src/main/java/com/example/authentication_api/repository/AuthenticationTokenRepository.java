package com.example.authentication_api.repository;

import com.example.authentication_api.entity.AuthenticationToken;
import com.example.authentication_api.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationTokenRepository extends CrudRepository<AuthenticationToken,Integer> {
    AuthenticationToken findByUser(Customer user);
}
