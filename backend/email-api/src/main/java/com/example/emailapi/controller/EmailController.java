package com.example.emailapi.controller;

import com.example.emailapi.dto.EmailDto;
import com.example.emailapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*",maxAge = 3600)
@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;


    @PostMapping
    void sendMail(@RequestBody EmailDto emailDto)
    {
        emailService.sendSimpleMessage(emailDto);
    }
}
