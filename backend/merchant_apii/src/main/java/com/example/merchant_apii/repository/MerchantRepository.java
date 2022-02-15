package com.example.merchant_apii.repository;

import com.example.merchant_apii.entity.Merchant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends MongoRepository<Merchant,String> {

    Merchant findByMerchantEmail(String merchantEmail);


}
