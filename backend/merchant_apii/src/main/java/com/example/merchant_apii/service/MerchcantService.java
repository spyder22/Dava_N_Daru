package com.example.merchant_apii.service;

import com.example.merchant_apii.dto.MerchantSignup;
import com.example.merchant_apii.dto.ProductMerchantId;
import com.example.merchant_apii.payloads.request.AddProduct;
import com.example.merchant_apii.payloads.response.MerchantProducts;
import com.example.merchant_apii.payloads.response.Response;

public interface MerchcantService {

     Response signUp(MerchantSignup merchantDto);
     void addProduct(AddProduct product);
     MerchantProducts getMerchantProductsById(String merchantId);
     void addProductIdToMerchant(ProductMerchantId productMerchantId);
     String getMerchantName(String merchantId);




}
