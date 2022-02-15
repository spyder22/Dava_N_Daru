package com.example.authentication_api.controller;


import com.example.authentication_api.dto.ResponseDto;
import com.example.authentication_api.dto.UserOrderDto;
import com.example.authentication_api.payloads.user.AccountDto;
import com.example.authentication_api.payloads.user.SignInDto;
import com.example.authentication_api.payloads.user.SignInResponseDto;
import com.example.authentication_api.payloads.user.SignUpDto;
import com.example.authentication_api.service.UserOrderServiceImpl;
import com.example.authentication_api.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins="*",maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserOrderServiceImpl userOrderServiceImpl;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignUpDto signupDto) throws Exception{
        System.out.println("signup comming");
        return userServiceImpl.signUp(signupDto);
    }

    @PostMapping("/googlesignin")
    public void googleSignIn(@RequestBody SignUpDto signUpDto)
    {
        System.out.println("signin google----"+signUpDto.getEmail()+signUpDto.getName());
        userServiceImpl.googleSignIn(signUpDto);
    }

    @GetMapping("/account/{email}")
    public AccountDto getAccountDetailById(@PathVariable String email)
    {
        System.out.println(email);
        AccountDto accountDto= userServiceImpl.getAccountDetailById(email);
        System.out.println(accountDto.getName()+"------"+accountDto.getAddress());
        return accountDto;
    }


    // signin

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto)throws Exception {
        System.out.println("signin");
        return userServiceImpl.signIn(signInDto);
    }

    @GetMapping("/orderIds/{email}")
    public List<String > getOrderIdsByUserEmail(@PathVariable String email)
    {
        return  userServiceImpl.getOrderIdsOfUserByMail(email);

    }

    @GetMapping("/usermail/{id}")
    public String getUserEmailById(@PathVariable Integer id)
    {
        return  userServiceImpl.getUserEmailById(id);
    }

    @PostMapping("/addOrder")
    public void addOrderIdToUser(@RequestBody UserOrderDto userOrderDto)
    {
        userOrderServiceImpl.add(userOrderDto);
    }


}
