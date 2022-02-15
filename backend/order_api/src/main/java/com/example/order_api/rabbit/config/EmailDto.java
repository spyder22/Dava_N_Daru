package com.example.order_api.rabbit.config;

import java.io.Serializable;

public class EmailDto implements Serializable {
    private String subject;
    private String message;
    private String userEmail;

    public EmailDto() {
    }

    public EmailDto(String subject, String message, String userEmail) {
        this.subject = subject;
        this.message = message;
        this.userEmail = userEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
