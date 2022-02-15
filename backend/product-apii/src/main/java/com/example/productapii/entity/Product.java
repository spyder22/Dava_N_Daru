package com.example.productapii.entity;

import com.example.productapii.dtos.MerchantDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Product {

    @Id
    private String productId;
    private String productName;
    private String categoryName;
    private String description;
    private String image;
    private Long orderCount;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private List<MerchantDto> merchantList;

    public Product(String productId, String productName, String categoryName, String description, String image) {
        this.productId = productId;
        this.productName = productName;
        this.categoryName = categoryName;
        this.description = description;
        this.image = image;
    }

    public Product() {
    }

    public Product(String productId, String productName, String categoryName, String description, String image, Long orderCount, String attribute1, String attribute2, String attribute3, String attribute4, String attribute5, List<MerchantDto> merchantList) {
        this.productId = productId;
        this.productName = productName;
        this.categoryName = categoryName;
        this.description = description;
        this.image = image;
        this.orderCount = orderCount;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
        this.attribute4 = attribute4;
        this.attribute5 = attribute5;
        this.merchantList = merchantList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public List<MerchantDto> getMerchantList() {
        return merchantList;
    }

    public void setMerchantList(List<MerchantDto> merchantList) {
        this.merchantList = merchantList;
    }
}
