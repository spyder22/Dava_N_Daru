package com.example.emailapi.service;

import com.example.emailapi.dto.EmailDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }


    @RabbitListener(queues = "email_queue")
    public void sendSimpleMessage(EmailDto emailDto)
    {
        SimpleMailMessage messageBox = new SimpleMailMessage();
        messageBox.setTo(emailDto.getUserEmail());
        messageBox.setSubject(emailDto.getSubject());
        messageBox.setText(emailDto.getMessage());
        emailSender.send(messageBox);
    }
}
