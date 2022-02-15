package com.example.productapii.services;

import com.example.productapii.dtos.ProductMerchantDto;
import com.example.productapii.entity.Product;
import com.example.productapii.payloads.request.AddProduct;
import com.example.productapii.payloads.request.ProductMerchantId;
import com.example.productapii.payloads.request.UpdateProduct;

import java.util.List;
import java.util.Optional;

public interface ProductService {

     List<Product> findAll() ;


     List<Product> getProductListByCategory(String categoryName);

     ProductMerchantDto findByProductMerchantId(ProductMerchantId productMerchantId);

     void addProduct(Product product);
     void updateProduct(UpdateProduct updateProduct);
     List<ProductMerchantDto> getHighestRatingProducts();
     List<ProductMerchantDto> getHighestSoldProducts();
     List<ProductMerchantDto>  getRecommendedProduct();

     Optional<Product> findById(String id);
     String addNewProduct(AddProduct addProduct);


}
