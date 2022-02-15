package com.example.merchant_apii.dto;

public class ProductMerchantId {
    private String productId;
    private String merchantId;
    //todo : reduce the usage of constructors
    public ProductMerchantId() {
    }

    public ProductMerchantId(String productId, String merchantId) {
        this.productId = productId;
        this.merchantId = merchantId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
