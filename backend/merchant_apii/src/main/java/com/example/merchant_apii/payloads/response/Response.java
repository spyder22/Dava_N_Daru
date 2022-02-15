package com.example.merchant_apii.payloads.response;

public class Response {
    private String status;
    private String merchantId;

    public Response() {
    }

    public Response(String status, String merchantId) {
        this.status = status;
        this.merchantId = merchantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
}
