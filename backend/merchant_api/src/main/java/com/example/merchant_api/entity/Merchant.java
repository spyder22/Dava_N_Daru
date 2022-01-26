package com.example.merchant_api.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Merchant {




    @Id
    private Long merchantId;
    private String merchantName;
    private String merchantEmail;
    private String password;
    private double merchantRating;
    private List<String> productIds;

    public Merchant() {
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantEmail() {
        return merchantEmail;
    }

    public void setMerchantEmail(String merchantEmail) {
        this.merchantEmail = merchantEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMerchantRating() {
        return merchantRating;
    }

    public void setMerchantRating(double merchantRating) {
        this.merchantRating = merchantRating;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    //    private List<MerchantProducts> merchantProductsList;
//
//    public Merchant() {
//    }
//
//    public Merchant(Long merchantId, String merchantName, double merchantRating, List<MerchantProducts> merchantProductsList) {
//        this.merchantId = merchantId;
//        this.merchantName = merchantName;
//        this.merchantRating = merchantRating;
//        this.merchantProductsList = merchantProductsList;
//    }
//
//    public Long getMerchantId() {
//        return merchantId;
//    }
//
//    public void setMerchantId(Long merchantId) {
//        this.merchantId = merchantId;
//    }
//
//    public String getMerchantName() {
//        return merchantName;
//    }
//
//    public void setMerchantName(String merchantName) {
//        this.merchantName = merchantName;
//    }
//
//    public double getMerchantRating() {
//        return merchantRating;
//    }
//
//    public void setMerchantRating(double merchantRating) {
//        this.merchantRating = merchantRating;
//    }
//
//    public List<MerchantProducts> getMerchantProductsList() {
//        return merchantProductsList;
//    }
//
//    public void setMerchantProductsList(List<MerchantProducts> merchantProductsList) {
//        this.merchantProductsList = merchantProductsList;
//    }
}
