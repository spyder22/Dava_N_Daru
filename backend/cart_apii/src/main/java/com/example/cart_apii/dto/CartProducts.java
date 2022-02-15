package com.example.cart_apii.dto;

public class CartProducts {
    private String productId;
    private int quantity;
    private  String merchantId;
    private  String merchantName;
    private String image;
    private  String productName;
    private double price;
    private double subtotal;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public CartProducts() {
    }

    public CartProducts(String productId, int quantity, String merchantId, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.merchantId = merchantId;
        this.price = price;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CartProducts))  {
            return false;
        }

        CartProducts other = (CartProducts) obj;
        return this.getProductId().equals(other.getProductId())
                && this.getMerchantId().equals(other.getMerchantId());
    }

    @Override
    public int hashCode() {
        return 31*productId.hashCode()+merchantId.hashCode();
    }
}
