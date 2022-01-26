package com.example.cart_api.services;

import com.example.cart_api.repository.DummyCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DummyCartService {
    @Autowired
    DummyCartRepository dummyCartRepository;
}
