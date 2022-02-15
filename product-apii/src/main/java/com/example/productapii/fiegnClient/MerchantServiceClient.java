package com.example.productapii.fiegnClient;

import com.example.productapii.payloads.request.ProductMerchantId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "merchant-apii")
public interface MerchantServiceClient {

    @PostMapping("/merchant/addpid")
    public void addProductIdToMerchant(@RequestBody ProductMerchantId productMerchantId);

    @GetMapping("/merchant/name/{merchantId}")
    public String getMerchantNameById(@PathVariable(value = "merchantId") String merchantId);

}
