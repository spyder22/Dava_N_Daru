package com.example.cart_apii.payloads.request;

public class ProductMerchantId {
    private String productId;
    private String merchantId;

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
