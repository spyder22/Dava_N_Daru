package com.example.product_api.payloads.response;

import java.util.List;

public class MerchantProducts {
    private Long merchantId;

    private List<Products> products;

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
