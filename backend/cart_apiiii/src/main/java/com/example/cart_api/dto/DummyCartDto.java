package com.example.cart_api.dto;

import java.util.HashMap;

public class DummyCartDto {
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
