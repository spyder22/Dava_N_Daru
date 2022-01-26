package com.example.merchant_api.repository;

import com.example.merchant_api.entity.Merchant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends MongoRepository<Merchant,Long> {

    Merchant findByMerchantEmail(String merchantEmail);
}
