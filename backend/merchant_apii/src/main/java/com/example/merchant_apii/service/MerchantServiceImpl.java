package com.example.merchant_apii.service;


import com.example.merchant_apii.dto.MerchantSignup;
import com.example.merchant_apii.payloads.request.SignInDto;
import com.example.merchant_apii.payloads.response.MerchantDto;
import com.example.merchant_apii.dto.ProductMerchantId;
import com.example.merchant_apii.dto.Products;
import com.example.merchant_apii.entity.Merchant;
import com.example.merchant_apii.feignClients.ProductServiceClient;
import com.example.merchant_apii.payloads.request.AddProduct;
import com.example.merchant_apii.payloads.request.ProductDto;
import com.example.merchant_apii.payloads.response.MerchantProducts;
import com.example.merchant_apii.payloads.response.ProductMerchantDto;
import com.example.merchant_apii.payloads.response.Response;
import com.example.merchant_apii.repository.MerchantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MerchantServiceImpl implements MerchcantService{

    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    ProductServiceClient productServiceClient;

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    @Override
    public Response signUp(MerchantSignup merchantDto)
    {
        if (Objects.nonNull(merchantRepository.findByMerchantEmail(merchantDto.getMerchantEmail()))) {
            return new Response("failure",null);
        }
        String encryptedPassword = null;

        try {
            encryptedPassword = hashPassword(merchantDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Merchant merchant=new Merchant();
        BeanUtils.copyProperties(merchantDto,merchant);
        merchant.setProductIds(new ArrayList<>());
        merchant.setPassword(encryptedPassword);
        try
        {
            merchantRepository.save(merchant);
            return new Response("success", merchant.getMerchantId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new Response("failure", null);
        }
    }


    // todo : change this logic based on changes done in authentication_api service
    public Response signIn(SignInDto signInDto)  {
        // find user by email
        Merchant merchant = merchantRepository.findByMerchantEmail(signInDto.getEmail());

        if (Objects.isNull(merchant)) {
            return new Response("failure", null);
        }

        try {
            if (!merchant.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                return new Response("failure", null);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new Response("success", merchant.getMerchantId());

    }



    @Override
    public void addProduct(AddProduct product)
    {
        // todo : merchant exists check should be the first step in this method
        //send data to addNewProduct(product) in product-api
        String productid=productServiceClient.addNewProduct(product);
        System.out.println(productid);
        if(productid==null)
        {
            System.out.println("error adding new product");
            return;
        }
        // todo : do you need the exists check?
        boolean exits=merchantRepository.existsById(product.getMerchantId());
        if(exits)
        {
            addProductIdToMerchant(new ProductMerchantId(productid, product.getMerchantId()));
        }
    }

    // todo : change this to use one api call on product services to load all products by merchant
    @Override
    public MerchantProducts getMerchantProductsById(String merchantId)
    {
        MerchantProducts merchantProducts=new MerchantProducts(merchantId);
        List<Products> products=new ArrayList<>();
        Optional<Merchant> response=merchantRepository.findById(merchantId);

        System.out.println("outside ");
        if(response.isPresent())
        {
            Merchant merchant=response.get();
            System.out.println("merchant name"+merchant.getMerchantName());

            List<String> productIds=merchant.getProductIds();
            System.out.println("product ids "+productIds);
            if(productIds!=null)
            {
                List<Products> productsList=new ArrayList<>();
                for(String id:productIds)
                {
                    ProductDto productDto = productServiceClient.getProductDetailsById(id);
                    if (productDto != null) {
                        ProductMerchantDto productMerchantDto = getProductMerchantDtoFromProductDto(productDto, merchantId);
                        products.add(convertFromProductMerchantDtoToMerchantProducts(productMerchantDto));
                    }
                }
                merchantProducts.setProducts(products);
                System.out.println("success getMerchantProductsById");
                return merchantProducts;
            }
        }
        System.out.println("failure getMerchantProductsById");
        return null;

    }



    @Override
    public void addProductIdToMerchant(ProductMerchantId productMerchantId)
    {
        System.out.println("addProductIdToMerchant---> pid: "+productMerchantId.getProductId());

        System.out.println("addProductIdToMerchant---> pid: "+productMerchantId.getMerchantId());

        Optional<Merchant> response=merchantRepository.findById(productMerchantId.getMerchantId());
        if (response.isPresent())
        {
            Merchant merchant=response.get();
            // todo : change this from List to Set to reduce the logic here to achieve the unique list of PIDs for merchant collection
            List<String> productIds=merchant.getProductIds();

            System.out.println(merchant.getProductIds()+"pids of m");

            List<String> newProductIds=new ArrayList<>();
            if(productIds!=null)
            {
                if(productIds.size()==0)
                {
                    productIds=new ArrayList<>();
                    productIds.add(productMerchantId.getProductId());
                    merchant.setProductIds(productIds);
                    merchantRepository.save(merchant);
                    return;
                }

                for(String id:productIds)
                {
                    System.out.println("id-----"+id);
                    if(!id.equals(productMerchantId.getProductId()))
                    {
                        newProductIds.add(id);
                    }
                }
                newProductIds.add(productMerchantId.getProductId());
                merchant.setProductIds(newProductIds);
                merchantRepository.save(merchant);
                System.out.println("Product id added successfully");
                return;
            }
            else {
                productIds = new ArrayList<>();
                productIds.add(productMerchantId.getProductId());
                merchant.setProductIds(productIds);
                merchantRepository.save(merchant);
                System.out.println("Product id added successfully");
            }

            return;
        }
        System.out.println("No merchant present");
    }




    @Override
    public String getMerchantName(String merchantId)
    {
        Optional<Merchant> merchant= merchantRepository.findById(merchantId);
        if(merchant.isPresent())
        {
            return merchant.get().getMerchantName();
        }
        return null;
    }




    public List<String> getAllMerchantIds() {
        List<Merchant> merchant= merchantRepository.findAll();
        List<String> ids=new ArrayList<>();
        for(Merchant merchant1:merchant)
        {
            ids.add(merchant1.getMerchantId());
        }
        return ids;
    }

    private Products convertFromProductMerchantDtoToMerchantProducts(ProductMerchantDto productMerchantDto)
    {
        Products product=new Products();

        BeanUtils.copyProperties(productMerchantDto,product);

        try {
            product.setImage(productMerchantDto.getImage());
            product.setPrice(productMerchantDto.getMerchant().getPrice());
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


    private ProductMerchantDto getProductMerchantDtoFromProductDto(ProductDto productDto,String merchantId)
    {
        System.out.println("getProductMerchantDtoFromProductDto--->pid: "+productDto.getProductId());
        ProductMerchantDto productMerchantDto=new ProductMerchantDto();
        BeanUtils.copyProperties(productDto,productMerchantDto);
        List<MerchantDto> merchantDtos=productDto.getMerchantList();
        for(MerchantDto merchantDto:merchantDtos)
        {
            if(merchantDto.getMerchantId().equals(merchantId))
            {
                System.out.println("Yooooooooo");
                productMerchantDto.setMerchant(merchantDto);
                return productMerchantDto;
            }

        }
        return null;
    }

}
