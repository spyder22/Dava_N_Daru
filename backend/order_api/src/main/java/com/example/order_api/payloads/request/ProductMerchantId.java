package com.example.order_api.payloads.request;

public class ProductMerchantId {
    private String productId;
    private String merchantId;

    public ProductMerchantId() {
    }

    public ProductMerchantId(String productId, String merchantId) {
        this.productId = productId;
        this.merchantId = merchantId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
