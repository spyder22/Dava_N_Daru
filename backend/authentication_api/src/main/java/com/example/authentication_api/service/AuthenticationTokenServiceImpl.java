package com.example.authentication_api.service;

import com.example.authentication_api.entity.AuthenticationToken;
import com.example.authentication_api.entity.Customer;
import com.example.authentication_api.repository.AuthenticationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// todo : service interface is missing .. this is not a right way of coding
@Service
public class AuthenticationTokenServiceImpl {

    @Autowired
    AuthenticationTokenRepository authenticationTokenRepository;


    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        authenticationTokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(Customer user) {
        return authenticationTokenRepository.findByUser(user);
    }
}
