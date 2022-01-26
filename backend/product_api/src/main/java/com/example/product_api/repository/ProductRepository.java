package com.example.product_api.repository;

import com.example.product_api.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

//    @Query("{'MerchantList.MerchantId':?0}")
//    void updateBy(String productId, MerchantDto merchantDto);

    List<Product> findByCategoryNameContainingIgnoreCase(String categoryName);


    List<Product> findByMerchantList_MerchantId(Long merchantId);

    Product findByProductName(String productName);


}
