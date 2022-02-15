package com.example.authentication_api.repository;

import com.example.authentication_api.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer> {

    Customer findByEmail(String email);
}
