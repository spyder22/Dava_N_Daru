package com.example.authentication_api.repository;

import com.example.authentication_api.entity.UserOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderRepository extends CrudRepository<UserOrder,String> {
    List<UserOrder> findAllByEmail(String id);
}
