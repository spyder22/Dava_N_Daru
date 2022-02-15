package com.example.merchant_apii.controller;

import com.example.merchant_apii.dto.MerchantSignup;
import com.example.merchant_apii.dto.ProductMerchantId;
import com.example.merchant_apii.payloads.request.AddProduct;
import com.example.merchant_apii.payloads.request.SignInDto;
import com.example.merchant_apii.payloads.response.MerchantProducts;
import com.example.merchant_apii.payloads.response.Response;
import com.example.merchant_apii.service.MerchantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//todo : need to improve on api naming conventions
// addpid -- does not give any details.. pid in OS is called process id ..
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    MerchantServiceImpl merchantServiceImpl;

    @PostMapping("/register")
    public Response registerMerchant(@RequestBody MerchantSignup merchantSignup)
    {
        System.out.println("mail"+  merchantSignup.getMerchantEmail());

        return merchantServiceImpl.signUp(merchantSignup);
    }

    @PostMapping("/signin")
    public Response signIn(@RequestBody SignInDto signInDto)
    {
        return merchantServiceImpl.signIn(signInDto);
    }

    @GetMapping("/find/{merchantId}") //working
    public MerchantProducts getMerchantProductsById(@PathVariable  String merchantId)
    {
        System.out.println(merchantId);
        return merchantServiceImpl.getMerchantProductsById(merchantId);
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody AddProduct product)
    {
        System.out.println(product.getMerchantId()+"------merchnat id " +product.getProductName());
        merchantServiceImpl.addProduct(product);
    }


    @PostMapping("/addpid")
    public void addProductIdToMerchant(@RequestBody  ProductMerchantId productMerchantId)
    {
        System.out.println(productMerchantId.getMerchantId() +"-----"+productMerchantId.getProductId());
        merchantServiceImpl.addProductIdToMerchant(productMerchantId);
    }

    @GetMapping("/listids")
    public List<String> getAllMerchantIds()
    {
        return merchantServiceImpl.getAllMerchantIds();
    }

    @GetMapping("/name/{merchantId}")
    public String getMerchantNameById(@PathVariable String merchantId)
    {
        return merchantServiceImpl.getMerchantName(merchantId);
    }

}
