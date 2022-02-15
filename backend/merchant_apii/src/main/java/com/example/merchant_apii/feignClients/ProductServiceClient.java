package com.example.merchant_apii.feignClients;


import com.example.merchant_apii.dto.ProductMerchantId;
import com.example.merchant_apii.payloads.request.AddProduct;
import com.example.merchant_apii.payloads.request.ProductDto;
import com.example.merchant_apii.payloads.response.ProductMerchantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-apii")
public interface ProductServiceClient {


    @GetMapping("/product/{id}")
     ProductDto getProductDetailsById(@PathVariable(value = "id") String id);

    @PostMapping("/product/addnew")//rabbitmq
     String addNewProduct(@RequestBody AddProduct addProduct);

}
