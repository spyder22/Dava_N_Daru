package com.example.productapii.repository;

import com.example.productapii.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {


    List<Product> findByCategoryNameContainingIgnoreCase(String categoryName);


    Product findByProductName(String productName);

    //todo : use https://docs.mongodb.com/manual/tutorial/query-array-of-documents/ to create one  more method to load products by merchant id


}
