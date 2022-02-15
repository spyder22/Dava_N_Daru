package com.example.merchant_apii.payloads.response;


import com.example.merchant_apii.dto.Products;

import java.util.List;

public class MerchantProducts {
    private String merchantId;

    private List<Products> products;

    public MerchantProducts() {
    }

    public MerchantProducts(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
