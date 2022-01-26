package com.example.cart_api.entity;

import com.example.cart_api.dto.ProductQuantity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.HashSet;

@Document
public class Cart {

    @Id
    private String userEmail;



    //  merchantid---->productId----->quantity
    private HashMap<Long,ProductQuantity> items;

    private Double total;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public HashMap<Long, ProductQuantity> getItems() {
        return items;
    }

    public void setItems(HashMap<Long, ProductQuantity> items) {
        this.items = items;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


}
