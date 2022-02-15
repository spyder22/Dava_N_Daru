package com.example.cart_apii.dto;

public class MerchantDto {
    private String merchantId;
    private String merchantName;

    private String usp;
    private Long stock;
    private Double price;
    private Double productRating;


    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUsp() {
        return usp;
    }

    public void setUsp(String usp) {
        this.usp = usp;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getProductRating() {
        return productRating;
    }

    public void setProductRating(Double productRating) {
        this.productRating = productRating;
    }

    public MerchantDto() {
    }

    public MerchantDto(String merchantId, String usp, Long stock, Double price, Double productRating) {
        this.merchantId = merchantId;
        this.usp = usp;
        this.stock = stock;
        this.price = price;
        this.productRating = productRating;
    }
}
