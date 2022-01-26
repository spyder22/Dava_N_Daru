package com.example.cart_api.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;


@Document
public class DummyCart {

    @Id
    private String userId;
    private HashMap<String,Long> items;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public HashMap<String, Long> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Long> items) {
        this.items = items;
    }
}
