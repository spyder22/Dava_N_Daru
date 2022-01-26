package com.example.merchant_api.controller;

import com.example.merchant_api.dto.MerchantDto;
import com.example.merchant_api.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    @PostMapping("/register")
    public void registerMerchant(@RequestParam MerchantDto merchantDto)
    {
        merchantService.addMerchant(merchantDto);
        System.out.println("done");
    }


}
