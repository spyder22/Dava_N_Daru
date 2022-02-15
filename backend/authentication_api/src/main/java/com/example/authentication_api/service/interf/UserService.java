package com.example.authentication_api.service.interf;

import com.example.authentication_api.dto.ResponseDto;
import com.example.authentication_api.exceptions.CustomException;
import com.example.authentication_api.payloads.user.AccountDto;
import com.example.authentication_api.payloads.user.SignInDto;
import com.example.authentication_api.payloads.user.SignInResponseDto;
import com.example.authentication_api.payloads.user.SignUpDto;

public interface UserService {

    public ResponseDto signUp(SignUpDto signupDto)throws CustomException;
    public SignInResponseDto signIn(SignInDto signInDto)throws Exception ;
    public String getUserEmailById(Integer id);
    public void googleSignIn(SignUpDto signUpDto);
    public AccountDto getAccountDetailById(String email);






}
