package com.example.authentication_api.exceptions;

public class AuthenticationFailureException extends Exception {
    public AuthenticationFailureException(String msg){
        super(msg);
    }
}
