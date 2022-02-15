package com.example.order_api.feignClients;


import com.example.order_api.dto.ProductMerchantDto;
import com.example.order_api.payloads.request.ProductMerchantId;
import com.example.order_api.payloads.request.UpdateProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-apii")
public interface ProductServiceClient {

    @PostMapping("/product/pmid")
    public ProductMerchantDto findByProductMerchantId(@RequestBody ProductMerchantId productMerchantId);

    @PostMapping("/product/update")
    public void updateProduct(@RequestBody UpdateProduct updateProduct);

}
