package com.example.order_api.payloads.request;

public class UpdateProduct {
    private String productId;
    private String merchantId;
    private String usp;
    private Long stock;
    private Double price;
    private Double productRating;

    public UpdateProduct() {

    }

    public UpdateProduct(String productId, String merchantId, String usp, Long stock, Double price, Double productRating) {
        this.productId = productId;
        this.merchantId = merchantId;
        this.usp = usp;
        this.stock = stock;
        this.price = price;
        this.productRating = productRating;
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
}
