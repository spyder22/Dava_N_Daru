package com.example.merchant_api.payloads.response;

import com.example.merchant_api.dto.Products;

import java.util.List;

public class MerchantProducts {
    private Long merchantId;

    private List<Products> products;

    public MerchantProducts() {
    }

    public MerchantProducts(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
