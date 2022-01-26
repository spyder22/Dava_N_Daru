package com.example.cart_api.dto;

import java.util.HashMap;
import java.util.List;

public class CartDto {
    private String userEmail;
    private List<ProductMerchantDto> productMerchantDtoList;
    private Double total;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<ProductMerchantDto> getProductMerchantDtoList() {
        return productMerchantDtoList;
    }

    public void setProductMerchantDtoList(List<ProductMerchantDto> productMerchantDtoList) {
        this.productMerchantDtoList = productMerchantDtoList;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
