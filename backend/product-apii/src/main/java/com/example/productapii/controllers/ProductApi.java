package com.example.productapii.controllers;

import com.example.productapii.dtos.ProductDto;
import com.example.productapii.dtos.ProductMerchantDto;
import com.example.productapii.entity.Product;
import com.example.productapii.payloads.request.AddProduct;
import com.example.productapii.payloads.request.ProductMerchantId;
import com.example.productapii.payloads.request.UpdateProduct;
import com.example.productapii.services.ProductServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/product")
public class ProductApi {

    @Autowired
    ProductServiceImpl productServiceImpl;

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
        System.out.println("cat-----"+name);
        List<ProductDto> productDtoList=new ArrayList<>();
        List<Product> productList= productServiceImpl.getProductListByCategory(name);
        for(Product product:productList)
        {
            productDtoList.add(copyFromEntityToProductDto(product));
        }
        return  productDtoList;
    }

    @GetMapping("/{id}")
    public ProductDto getProductDetailsById(@PathVariable String id)
    {
        System.out.println("*************"+id);
        Optional<Product> product= productServiceImpl.findById(id);
        if (product.isPresent()) {
            System.out.println(product.get().getProductName());
            return  copyFromEntityToProductDto(product.get());
        }
        return null;
    }

    @GetMapping
    public List<ProductDto> findAll()
    {
        List<ProductDto> productDtoList=new ArrayList<>();
        List<Product> productList= productServiceImpl.findAll();
        for(Product product:productList)
        {
            productDtoList.add(copyFromEntityToProductDto(product));
        }
        return productDtoList;
    }


    @PostMapping
    public void addProduct(@RequestBody ProductDto productDto){

        System.out.println(productDto.getProductName()+"**************");
        productServiceImpl.addProduct(copyFromProductDtoToEntity(productDto));
    }

    @PostMapping("/addnew")//can use rabbitmq
    public String addNewProduct(@RequestBody AddProduct addProduct){
        return productServiceImpl.addNewProduct(addProduct);
    }

    @PostMapping("/update")
    public void updateProduct(@RequestBody UpdateProduct updateProduct){
        System.out.println(updateProduct.getProductId() + "update product************");
        productServiceImpl.updateProduct(updateProduct);
    }

    @PostMapping("/pmid")
    public ProductMerchantDto findByProductMerchantId(@RequestBody ProductMerchantId productMerchantId)
    {
        System.out.println("yooo");
        System.out.println(productMerchantId.getProductId());
        ProductMerchantDto productMerchantDto= productServiceImpl.findByProductMerchantId(productMerchantId);
        System.out.println(productMerchantDto+"************/////");
        return productMerchantDto;
    }


    @DeleteMapping("/delete")
    public void deleteAll( ){
        productServiceImpl.deleteAll();
    }

    @GetMapping("/exits/{id}")
    public Boolean productIdExits(@PathVariable  String id)
    {
        return productServiceImpl.exitsById(id);
    }


    @GetMapping("/recommends")
    public List<ProductMerchantDto>  getRecommendedProduct()
    {
        System.out.println("recommended");
        return productServiceImpl.getRecommendedProduct();
    }



    @GetMapping("/maxrating")
    public List<ProductMerchantDto> getHighestRatingProducts()
    {
        return  productServiceImpl.getHighestRatingProducts();

    }


}
