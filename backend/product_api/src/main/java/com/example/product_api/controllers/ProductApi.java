package com.example.product_api.controllers;


import com.example.product_api.dtos.MerchantDto;
import com.example.product_api.dtos.ProductDto;
import com.example.product_api.dtos.ProductMerchantDto;
import com.example.product_api.entity.Product;
import com.example.product_api.payloads.request.AddProduct;
import com.example.product_api.payloads.request.ProductMerchantId;
import com.example.product_api.services.ProductService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductApi {
    @Autowired
    ProductService productService;


    RabbitTemplate rabbitTemplate;

    //getstock


    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;



    private Product copyFromProductDtoToEntity(ProductDto productDto)
    {
        Product product=new Product();
        BeanUtils.copyProperties(productDto,product);

        return product;

    }

    private ProductDto copyFromEntityToProductDto(Product product)
    {
        ProductDto productDto=new ProductDto();
        BeanUtils.copyProperties(product,productDto);
        return productDto;
    }



    @GetMapping("/cat/{name}")
    public List<ProductDto> findByCategory(@PathVariable String name)
    {
        List<ProductDto> productDtoList=new ArrayList<>();
        List<Product> productList=productService.getProductListByCategory(name);
        for(Product product:productList)
        {
            productDtoList.add(copyFromEntityToProductDto(product));
        }

        return  productDtoList;
    }





//    @GetMapping("/{id}")
//    public ProductDto findById(@PathVariable String id)
//    {
//        Product product=productService.findById(id);
//
//        System.out.println(product + "************");
//
//        ProductDto productDto=new ProductDto();
//        BeanUtils.copyProperties(product,productDto);
//        return productDto;
//
//    }






    @GetMapping("/{id}")
    public ProductDto getProductDetailsById(@PathVariable String id)
    {
        Optional<Product> product=productService.findById(id);

        if (product.isPresent()) {
            return  copyFromEntityToProductDto(product.get());
        }

        return null;

    }



    @GetMapping
    public List<ProductDto> findAll()
    {
        List<ProductDto> productDtoList=new ArrayList<>();
        List<Product> productList=productService.findAll();
        for(Product product:productList)
        {
            productDtoList.add(copyFromEntityToProductDto(product));
        }

        return productDtoList;

    }


    @PostMapping
    public void addProduct(@RequestBody ProductDto productDto){

        System.out.println(productDto.getProductName()+"**************");
        productService.addProduct(copyFromProductDtoToEntity(productDto));
    }

    @PostMapping("/addnew")//rabbitmq
    public String addNewProduct(@RequestBody AddProduct addProduct){

        return productService.addNewProduct(addProduct);

    }


    @PutMapping("/update/{productId}")
    public void updateProduct(@PathVariable String productId, @RequestBody MerchantDto merchantDto){
        System.out.println(merchantDto + "************");
        productService.updateProduct(productId,merchantDto);

    }


    @GetMapping("/merchantId/{id}")
    public List<ProductMerchantDto> findByMerchantId(@PathVariable Long id)
    {
        return productService.findByMerchantId(id);
    }

    @PostMapping("/pmid")
    public ProductMerchantDto findByProductMerchantId(@RequestBody ProductMerchantId productMerchantId)
    {
        System.out.println("yooo");
        System.out.println(productMerchantId.getProductId());
        return productService.findByProductMerchantId(productMerchantId);
    }

//    @GetMapping("/mpid")
//    public ProductMerchantDto findByProductMerchantId(@RequestBody ProductMerchantId productMerchantId)
//    {
//        return productService.findByProductMerchantId(productMerchantId);
//    }


    @DeleteMapping("/delete")
    public void deleteAll( ){
        productService.deleteAll();
    }

    @GetMapping("/exits/{id}")
    public Boolean productIdExits(@PathVariable  String id)
    {
        return productService.exitsById(id);
    }


//    @GetMapping("/recommends")
//    public List<ProductMerchantDto>  getRecommendedProduct()
//    {
//        return productService.getRecommendedProduct();
//    }
//
//
//
//    @GetMapping("/maxrating")
//    public List<ProductMerchantDto> getHighestRatingProducts()
//    {
//        return  productService.getHighestRatingProducts();
//
//    }


    @GetMapping("/maxsold")
    public List<ProductMerchantDto> getHighestSoldProduct()
    {
        return productService.getHighestSoldProducts();

    }


}
