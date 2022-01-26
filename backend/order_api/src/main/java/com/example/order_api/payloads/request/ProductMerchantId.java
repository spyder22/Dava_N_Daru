package com.example.order_api.payloads.request;

public class ProductMerchantId {
    private String productId;
    private Long merchantId;

    public ProductMerchantId() {
    }

    public ProductMerchantId(String productId, Long merchantId) {
        this.productId = productId;
        this.merchantId = merchantId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
}
