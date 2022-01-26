package com.example.product_api.services;

import com.example.product_api.dtos.MerchantDto;
import com.example.product_api.dtos.ProductDto;
import com.example.product_api.dtos.ProductMerchantDto;
import com.example.product_api.dtos.ProductsByMerchantDto;
import com.example.product_api.entity.Product;
import com.example.product_api.payloads.request.AddProduct;
import com.example.product_api.payloads.request.ProductMerchantId;
import com.example.product_api.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;




    public List<Product> getProductListByCategory(String categoryName) {

        return productRepository.findByCategoryNameContainingIgnoreCase(categoryName);
    }



    public List<Product> findAll() {
        return productRepository.findAll();
    }



    public Optional<Product> findById(String id)
    {
        return productRepository.findById(id);
    }

    public List<ProductMerchantDto> findByMerchantId(Long id)
    {
        List<Product> products=productRepository.findByMerchantList_MerchantId(id);


        List<ProductMerchantDto> productMerchantDtoList=new ArrayList<ProductMerchantDto>();

        for(Product product:products)
        {
            ProductMerchantDto productMerchantDto=new ProductMerchantDto();
            BeanUtils.copyProperties(product,productMerchantDto);
            List<MerchantDto> merchantList=product.getMerchantList();
            for(MerchantDto merchantDto:merchantList)
            {
                if(id == merchantDto.getMerchantId())
                {
                    productMerchantDto.setMerchant(merchantDto);
                    productMerchantDtoList.add(productMerchantDto);
                    break;
                }
            }
        }


        return productMerchantDtoList;
    }

    public ProductMerchantDto findByProductMerchantId(ProductMerchantId productMerchantId)
    {
        List<ProductMerchantDto> productMerchantDtoList=findByMerchantId(productMerchantId.getMerchantId());
        System.out.println(productMerchantDtoList);

        for(ProductMerchantDto productMerchantDto:productMerchantDtoList)
        {
            System.out.println(productMerchantDto.getProductName()+"------>"+productMerchantDto.getProductId());
            if(productMerchantDto.getProductId().equals(productMerchantId.getProductId()))
            {
                return productMerchantDto;
            }
        }
        return null;
    }




    public Boolean exitsById(String id)
    {
        return productRepository.existsById(id);
    }




    public void addProduct(Product product)
    {
        productRepository.save(product);
    }


    public void deleteAll()
    {
        productRepository.deleteAll();
    }

    public void updateProduct(String productId, MerchantDto merchantDto)
    {
        Optional<Product> productOptional=findById(productId);

        if(productOptional.isPresent())
        {
            Product product=productOptional.get();
            List<MerchantDto> merchantDtoList=product.getMerchantList();
            if(merchantDtoList!=null)
            {
                for(MerchantDto dto:merchantDtoList)
                {
                    if(dto.getMerchantId() == merchantDto.getMerchantId())
                    {
                        dto.setPrice(merchantDto.getPrice());
                        dto.setProductRating(merchantDto.getProductRating());
                        dto.setStock(merchantDto.getStock());
                        dto.setUsp(merchantDto.getUsp());
                    }
                }
            }
            productRepository.save(product);
        }

//        Query query = new Query(Criteria.where("productaId").is(productId));
//        Update update = new Update().set("product.merchantList", merchantDto);
//        System.out.println(merchantDto.getUsp()+"******************");
//        mongoTemplate.findAndModify(query, update, Product.class);

    }




//    private Product sortProducts(Product f)
//    {
//        return  new Product(f.getProductId(),f.getProductName(),f.getCategoryName(),f.getDescription(),f.getOrderCount(),f.getAttribute1(),f.getAttribute2(),f.getAttribute3(),f.getAttribute4(),f.getAttribute5(),f.getMerchantList().stream().sorted(Comparator.comparing(MerchantDto::getMerchantId)).collect(Collectors.toList()));
//    }


    private List<ProductMerchantDto> getProductMerchantDtoFromProduct(List<Product> products)
    {

        List<ProductMerchantDto> productMerchantDtoList=new ArrayList<ProductMerchantDto>();



        for(Product product:products)
        {
            ProductMerchantDto productMerchantDto=new ProductMerchantDto();
            BeanUtils.copyProperties(product,productMerchantDto);
            List<MerchantDto> merchantList=product.getMerchantList();


            List<MerchantDto> sortedMerchants = merchantList.stream()
                    .sorted(Comparator.comparing(MerchantDto::getProductRating).reversed())
                    .collect(Collectors.toList());


            MerchantDto merchantDto=sortedMerchants.get(0);
            productMerchantDto.setMerchant(merchantDto);

            productMerchantDtoList.add(productMerchantDto);
        }

        return  productMerchantDtoList;
    }







    public List<ProductMerchantDto> getHighestRatingProducts()
    {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "merchantList.productRating"));

        return getProductMerchantDtoFromProduct(products);

    }




    public List<ProductMerchantDto> getHighestSoldProducts()
    {

        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "orderCount"));


        return getProductMerchantDtoFromProduct(products);
    }

    public List<ProductMerchantDto>  getRecommendedProduct()
    {


        List<ProductMerchantDto> productMerchantDtoList=new ArrayList<>();

        productMerchantDtoList.addAll(getHighestSoldProducts());
        productMerchantDtoList.addAll(getHighestRatingProducts());


        List<ProductMerchantDto> output=new ArrayList<>();

        int numberOfElements = 4;
        Random rand = new Random();

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(productMerchantDtoList.size());
            ProductMerchantDto random = productMerchantDtoList.get(randomIndex);
            output.add(random);
            productMerchantDtoList.remove(randomIndex);
        }
        return output;
    }



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

}