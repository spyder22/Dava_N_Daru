package com.example.cart_apii.feignClients;


import com.example.cart_apii.payloads.request.ProductMerchantId;
import com.example.cart_apii.payloads.request.UpdateProduct;
import com.example.cart_apii.payloads.response.ProductMerchantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-apii")
public interface ProductServiceClient {

    @PostMapping("/product/pmid")
     ProductMerchantDto findByProductMerchantId(@RequestBody ProductMerchantId productMerchantId);

    @PostMapping("/product/update")
     void updateProduct(@RequestBody UpdateProduct updateProduct);

}
