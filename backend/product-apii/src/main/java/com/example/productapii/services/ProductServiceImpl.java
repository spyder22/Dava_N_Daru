package com.example.productapii.services;


import com.example.productapii.dtos.MerchantDto;
import com.example.productapii.dtos.ProductMerchantDto;
import com.example.productapii.entity.Product;
import com.example.productapii.fiegnClient.MerchantServiceClient;
import com.example.productapii.payloads.request.AddProduct;
import com.example.productapii.payloads.request.ProductMerchantId;
import com.example.productapii.payloads.request.UpdateProduct;
import com.example.productapii.payloads.response.MerchantProducts;
import com.example.productapii.payloads.response.Products;
import com.example.productapii.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantServiceClient merchantServiceClient;









    @Override
    public List<Product> getProductListByCategory(String categoryName) {
        return productRepository.findByCategoryNameContainingIgnoreCase(categoryName);
    }


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // todo : missing the logic for the merchant name in the addNew .. but you have in update
    @Override
    public String addNewProduct(AddProduct addProduct)
    {
        Product product=new Product();
        BeanUtils.copyProperties(addProduct,product);

        List<MerchantDto> merchantList=new ArrayList<>();
        MerchantDto merchantDto=new MerchantDto();
        merchantDto.setUsp(addProduct.getUsp());
        merchantDto.setStock(addProduct.getStock());
        merchantDto.setProductRating((double)0);
        merchantDto.setPrice(addProduct.getPrice());
        merchantDto.setMerchantId(addProduct.getMerchantId());
        merchantList.add(merchantDto);
        product.setMerchantList(merchantList);

        try {
            productRepository.save(product);
            Product response=productRepository.findByProductName(product.getProductName());
            if(response !=null)
            {
                return response.getProductId();
            }
        }
        catch (Exception ex)
        {
            System.out.println("error adding new product");
            ex.printStackTrace();
        }
        return null;

    }

    @Override
    public Optional<Product> findById(String id)
    {
        return productRepository.findById(id);
    }

    // todo : see if you can achieve this with a mongo query
    @Override
    public ProductMerchantDto findByProductMerchantId(ProductMerchantId productMerchantId)
    {
        Optional<Product> productList=productRepository.findById(productMerchantId.getProductId());
        if(productList.isPresent())
        {
            ProductMerchantDto productMerchantDto=new ProductMerchantDto();
            Product product=productList.get();
            List<MerchantDto> merchantDtoList=product.getMerchantList();
            if(merchantDtoList!=null)
            {
                for(MerchantDto dto:merchantDtoList)
                {
                    if(dto.getMerchantId().equals(productMerchantId.getMerchantId()))
                    {
                        BeanUtils.copyProperties(product,productMerchantDto);
                        productMerchantDto.setMerchant(dto);
                        System.out.println("success findByProductMerchantDto --added"+productMerchantDto.getProductName());
                        return productMerchantDto;
                    }
                }
            }
        }
        System.out.println("failure findByProductMerchantDto");
        return null;
    }

    @Override
    public void addProduct(Product product)
    {
// TODO: 28/01/22 add in merchant product list
        try
        {
            productRepository.insert(product);
            String id=product.getProductId();
            System.out.println(id+"*********");
            List<MerchantDto> merchantDtos=product.getMerchantList();

            // can you convert this to one call .. which will send all merchantids ..
            for (MerchantDto merchantDto:merchantDtos)
            {
                merchantServiceClient.addProductIdToMerchant(new ProductMerchantId(id,merchantDto.getMerchantId()));
            }
        }
        catch (Exception e)
        {
            System.out.println("updating in merchant table failure");
            e.printStackTrace();
        }
    }

    public void deleteAll()
    {
        productRepository.deleteAll();
    }

    @Override
    public void updateProduct(UpdateProduct updateProduct)
    {

        Optional<Product> productOptional=findById(updateProduct.getProductId());
        long orderCount=0;

        if(productOptional.isPresent())
        {
            System.out.println(productOptional.get().getProductName());
            System.out.println(productOptional.get().getMerchantList());
            Product product=productOptional.get();
            Product newProduct=new Product();

            BeanUtils.copyProperties(product,newProduct);

            List<MerchantDto> merchantDtoList=product.getMerchantList();
            List<MerchantDto> newMerchantDtos=new ArrayList<>();
            if(merchantDtoList!=null)
            {
                System.out.println(updateProduct.getMerchantId());
                for(MerchantDto dto:merchantDtoList)
                {
                    System.out.println(dto.getMerchantId()+"*****");
                    try
                    {
                        if(dto.getMerchantId().equals(updateProduct.getMerchantId()))
                        {
                            System.out.println(updateProduct.getUsp()+"  "+updateProduct.getStock() );

                            MerchantDto merchantDto=new MerchantDto();

                            String merchantName=merchantServiceClient.getMerchantNameById(dto.getMerchantId());

                            merchantDto.setMerchantId(updateProduct.getMerchantId());
                            merchantDto.setMerchantName(merchantName);
                            merchantDto.setPrice(updateProduct.getPrice());
                            merchantDto.setProductRating(updateProduct.getProductRating());
                            merchantDto.setStock(updateProduct.getStock());
                            merchantDto.setUsp(updateProduct.getUsp());
                            newMerchantDtos.add(merchantDto);
                        }
                        else
                        {
                            newMerchantDtos.add(dto);
                        }
                    }catch (NullPointerException ex)
                    {
                        ex.printStackTrace();
                        continue;
                    }

                }
            }
            newProduct.setMerchantList(newMerchantDtos);

            productRepository.save(newProduct);
        }

    }





    @Override
    public List<ProductMerchantDto> getHighestRatingProducts()
    {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "merchantList.productRating"));

        List<Product> responseList=products.stream().limit(7).collect(Collectors.toList());

        return getProductMerchantDtoFromProduct(responseList);

    }



    @Override
    public List<ProductMerchantDto> getHighestSoldProducts()
    {

        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "orderCount"));

        List<Product> responseList=products.stream().limit(7).collect(Collectors.toList());

        return getProductMerchantDtoFromProduct(responseList);
    }


    @Override
    public List<ProductMerchantDto>  getRecommendedProduct()
    {


        List<ProductMerchantDto> productMerchantDtoList=new ArrayList<>();

        productMerchantDtoList.addAll(getHighestSoldProducts());
        productMerchantDtoList.addAll(getHighestRatingProducts());

        List<ProductMerchantDto> newList = productMerchantDtoList.stream()
                .distinct()
                .collect(Collectors.toList());

        for(ProductMerchantDto productMerchantDto:newList)
        {
            System.out.println("name    " +productMerchantDto.getProductName());
        }


        List<ProductMerchantDto> output=new ArrayList<>();

        int numberOfElements = 8;
        Random rand = new Random();

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(newList.size());
            ProductMerchantDto random = newList.get(randomIndex);
            output.add(random);
            newList.remove(randomIndex);
        }
        return output;
    }

    public Boolean exitsById(String id)
    {
        return productRepository.existsById(id);
    }

    private List<ProductMerchantDto> getProductMerchantDtoFromProduct(List<Product> products)
    {

        List<ProductMerchantDto> productMerchantDtoList=new ArrayList<ProductMerchantDto>();



        for(Product product:products)
        {
            ProductMerchantDto productMerchantDto=new ProductMerchantDto();
            BeanUtils.copyProperties(product,productMerchantDto);
            List<MerchantDto> merchantList=product.getMerchantList();

            System.out.println(merchantList+"-------merhcnat list");

            List<MerchantDto> sortedMerchants=null;
            try
            {
                sortedMerchants = merchantList.stream()
                        .sorted(Comparator.comparing(MerchantDto::getProductRating).reversed())
                        .collect(Collectors.toList());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }




            MerchantDto merchantDto=sortedMerchants.get(0);
            productMerchantDto.setMerchant(merchantDto);

            productMerchantDtoList.add(productMerchantDto);
        }

        return  productMerchantDtoList;
    }



}