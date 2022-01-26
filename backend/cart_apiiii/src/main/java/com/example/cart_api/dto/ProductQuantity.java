package com.example.cart_api.dto;

import java.util.HashMap;

public class ProductQuantity {

    //prodcutId---> Quantity
    private HashMap<String,Long> productQuantity;




    public HashMap<String, Long> getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(HashMap<String, Long> productQuantity) {
        this.productQuantity = productQuantity;
    }
}
