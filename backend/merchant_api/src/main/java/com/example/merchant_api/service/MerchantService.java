package com.example.merchant_api.service;

import com.example.merchant_api.dto.MerchantDto;
import com.example.merchant_api.dto.ProductMerchantId;
import com.example.merchant_api.dto.Products;
import com.example.merchant_api.entity.Merchant;
import com.example.merchant_api.payloads.request.AddProduct;
import com.example.merchant_api.payloads.response.MerchantProducts;
import com.example.merchant_api.payloads.response.ProductMerchantDto;
import com.example.merchant_api.repository.MerchantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MerchantService {

    @Autowired
    MerchantRepository merchantRepository;


    public Merchant findByMerchantEmail(String merchantEmail)
    {
       return merchantRepository.findByMerchantEmail(merchantEmail);
    }

    public void addMerchant(MerchantDto merchantDto)
    {
        Merchant merchant=new Merchant();
        BeanUtils.copyProperties(merchantDto,merchant);
        merchant.setMerchantRating(0);

        try{
            merchantRepository.save(merchant);
        }
        catch (Exception ex)
        {
            System.out.println("adding of merchant "+merchantDto.getMerchantName()+" failed");
            ex.printStackTrace();
        }

    }


    public void addProduct(AddProduct product)
    {
        //send data to addNewProduct(product) in product-api
        String productid=null;

        // TODO: 25/01/22 get productId and add to merchant table

        Optional<Merchant> response=merchantRepository.findById(product.getMerchantId());
        if(response.isPresent())
        {
            Merchant merchant=response.get();
            List<String> ids=merchant.getProductIds();
            if(ids == null)
            {
                ids=new ArrayList<>();
            }
            ids.add(productid);
            merchant.setProductIds(ids);
            try
            {
                merchantRepository.save(merchant);
            }
            catch (Exception ex)
            {
                System.out.println("Error saving new product id in merchant table");
                ex.printStackTrace();
            }
        }


    }


    public MerchantProducts getMerchantProductsById(Long merchantId)
    {
        MerchantProducts merchantProducts=new MerchantProducts(merchantId);
        Optional<Merchant> response=merchantRepository.findById(merchantId);

        if(response.isPresent())
        {
            Merchant merchant=response.get();

            List<String> productIds=merchant.getProductIds();
            if(productIds!=null)
            {
                List<Products> productsList=new ArrayList<>();
                for(String id:productIds)
                {
                    ProductMerchantId productMerchantId=new ProductMerchantId(id,merchantId);
                    ProductMerchantDto productMerchantDto=null;
                    //product_api -->findByProductMerchantId(productMerchantId)

                    if(productMerchantDto!=null)
                    {
                          productsList.add(convertFromProductMerchantDtoToMerchantProducts(productMerchantDto));
                    }
                }
                merchantProducts.setProducts(productsList);
                return  merchantProducts;
            }

        }
        return null;

    }

    private Products convertFromProductMerchantDtoToMerchantProducts(ProductMerchantDto productMerchantDto)
    {

        Products product=new Products();

        try {
            product.setImage(productMerchantDto.getImage());
            product.setPrice(productMerchantDto.getMerchant().getPrice());
            product.setProductId(productMerchantDto.getProductId());
            product.setProductName(productMerchantDto.getProductName());
            product.setStock(productMerchantDto.getMerchant().getStock());
            product.setProductRating(productMerchantDto.getMerchant().getProductRating());
            product.setUsp(productMerchantDto.getMerchant().getUsp());
            return product;
        }
        catch (Exception ex)
        {
            System.out.println("Exception while converting");
            ex.printStackTrace();
        }

        return null;

    }


}
