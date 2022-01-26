package com.example.cart_api.services;

import com.example.cart_api.dto.*;
import com.example.cart_api.entity.Cart;
import com.example.cart_api.payloads.request.CartItem;
import com.example.cart_api.repository.CartRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CartService {



    @Autowired
    CartRepository cartRepository;

    @Autowired
    private RestTemplate restTemplate;


    public ProductDto getProductById(String productId)
    {
        ProductDto productDto= restTemplate.getForObject("http://product-api/product/"+productId,ProductDto.class);
        return productDto;

    }


    private ProductMerchantDto getProductMerchanFromProduct(Long merchantId,ProductDto productDto)
    {
        ProductMerchantDto productMerchantDto=new ProductMerchantDto();

        List<MerchantDto> merchantDtoList=productDto.getMerchantList();
        if(merchantDtoList!=null)
        {
            for(MerchantDto dto:merchantDtoList)
            {
                if(dto.getMerchantId() == merchantId)
                {
                    productMerchantDto.setMerchant(dto);
                    return productMerchantDto;
                }
            }
        }

        return null;

    }


    public  CartDto getCartProductsByMail(String mail)
    {
        double total=0;
        Optional<Cart> cart=cartRepository.findById(mail);
        List<Cart> cartList=new ArrayList<>();
        CartDto cartDto=new CartDto();

        Cart cartItem;

        if(cart.isPresent())
        {
            List<ProductMerchantDto> productMerchantDtoList=new ArrayList<>();
            cartItem=cart.get();
            HashMap<Long,ProductQuantity> items=cartItem.getItems();
            if(items!=null)
            {
                for (Map.Entry mapElement : items.entrySet())
                {
                    Long merchantId = (Long) mapElement.getKey();
                    ProductQuantity productQuantity = (ProductQuantity) mapElement.getValue() ;
                    if(productQuantity!=null)
                    {
                        for (Map.Entry key : productQuantity.getProductQuantity().entrySet())
                        {
                            String productId = (String) key.getKey();
                            Long quantity = (Long) key.getValue();

                            System.out.println(productId+"*********"+quantity);
                            ProductDto productDto= restTemplate.getForObject("http://product-api/product/"+productId,ProductDto.class);
                            // TODO: get product from productservice using productId

                            ProductMerchantDto productMerchantDto= getProductMerchanFromProduct(merchantId,productDto);
                            BeanUtils.copyProperties(productDto,productMerchantDto);

                            productMerchantDtoList.add(productMerchantDto);
                            total += productMerchantDto.getMerchant().getPrice() * quantity;
                        }
                    }
                    else
                    {
                        return null;
                    }
                }
                cartDto.setTotal(total);
                cartDto.setUserEmail(mail);
                cartDto.setProductMerchantDtoList(productMerchantDtoList);
                return cartDto;
            }
        }
        return null;
    }





    public void removeProduct(CartItem cartItem)
    {

        System.out.println("yooooo1");

        Optional<Cart> response= cartRepository.findById(cartItem.getUserMail());
        if (response.isPresent())
        {
            System.out.println("yooooo2");
            Cart cart=response.get();

            HashMap<Long,ProductQuantity> merchantProduct=cart.getItems();

            if(merchantProduct !=null)
            {
                System.out.println("yooooo3");
                for (Map.Entry mapElement : merchantProduct.entrySet())
                {
                    System.out.println("yooooo4");
                    Long merchantIdGuess = (Long) mapElement.getKey();
                    if(merchantIdGuess==cartItem.getMerchantId()) {
                        System.out.println("yooooo5");
                        ProductQuantity productQuantity = (ProductQuantity) mapElement.getValue();
                        if (productQuantity != null) {
                            System.out.println("yooooo6");
                            HashMap<String,Long> pd=productQuantity.getProductQuantity();
                            for (Map.Entry key : pd.entrySet()) {
                                String productIdCheck = (String) key.getKey();

                                System.out.println("yooooo7");
                                if (productIdCheck == cartItem.getProductId())
                                {
                                    System.out.println("yooooo8");
                                    long stockPresent=0;
                                    ProductDto productDto= restTemplate.getForObject("http://product-api/product/"+cartItem.getProductId(),ProductDto.class);
                                    System.out.println(productDto+"*********************");
                                    List<MerchantDto> merchantDtoList=productDto.getMerchantList();

                                    for(MerchantDto merchantDto:merchantDtoList)
                                    {
                                        if(merchantDto.getMerchantId()==cartItem.getMerchantId())
                                        {
                                            stockPresent=merchantDto.getStock();
                                            merchantDto.setStock(merchantDto.getStock()+cartItem.getQuantity());
                                            merchantDtoList.remove(merchantDto);
                                            merchantDtoList.add(merchantDto);
                                            productDto.setMerchantList(merchantDtoList);
//                                            productDto.getMerchantList().set(merchantDto.getMerchantId(),merchantDto);
//                                            productDto.s
                                            //set your headers
                                            HttpHeaders headers = new HttpHeaders();
                                            headers.setContentType(MediaType.APPLICATION_JSON);

                                            //set your entity to send
                                            HttpEntity entity = new HttpEntity(productDto,headers);
                                            ResponseEntity<String> out = restTemplate.exchange("http://product-api/product/update/"+cartItem.getProductId(), HttpMethod.POST, entity
                                                    , String.class);

                                            System.out.println(out);


                                            break;
                                        }
                                    }



                                    productQuantity.getProductQuantity().remove(cartItem.getProductId());
                                    // TODO: 23/01/22 update in cartdatabse
                                    merchantProduct.put(merchantIdGuess, productQuantity);
                                    cart.setItems(merchantProduct);
                                    cartRepository.save(cart);
                                    return;
                                }
                            }
                        }
                    }
                }
            }

        }
        else {

        }

    }


    private void addToCart(ProductMerchantDto productMerchantDtoList,CartItem cartItem)
    {
        Optional<Cart> response=cartRepository.findById(cartItem.getUserMail());
        Cart cart;

        HashMap<Long,ProductQuantity> merProQuan;
        HashMap<String ,Long> prodQuan=new HashMap<>();
        ProductQuantity productQuantity=new ProductQuantity();

        if(response.isPresent())
        {
            cart=response.get();
            merProQuan=cart.getItems();

            if(merProQuan !=null)
            {
                for (Map.Entry mapElement : merProQuan.entrySet())
                {
                    Long merchantIdGuess = (Long) mapElement.getKey();
                    if(merchantIdGuess==cartItem.getMerchantId())
                    {

                        productQuantity=(ProductQuantity)mapElement.getValue();
                        prodQuan=productQuantity.getProductQuantity();
                    }
                }
            }

        }
        else
        {
            cart=new Cart();
            merProQuan=new HashMap<>();
        }

        cart.setUserEmail(cartItem.getUserMail());


        prodQuan.put(cartItem.getProductId(),cartItem.getQuantity());
        productQuantity.setProductQuantity(prodQuan);
        merProQuan.put(cartItem.getMerchantId(),productQuantity);

        cart.setItems(merProQuan);

        cartRepository.save(cart);
        System.out.println("***********Sucessfully added************");





    }


    public void addProducts(CartItem cartItem)
    {
        ProductDto productDto= restTemplate.getForObject("http://product-api/product/"+cartItem.getProductId(),ProductDto.class);
        System.out.println(productDto+"**********");
        ProductMerchantDto productMerchantDto=new ProductMerchantDto();
        if(productDto!=null)
        {
            BeanUtils.copyProperties(productDto,productMerchantDto);
            List<MerchantDto> merchantDtoList=productDto.getMerchantList();
            if(merchantDtoList!=null)
            {
                MerchantDto merchantDto=null;
                for (MerchantDto dto:merchantDtoList)
                {
                    if(dto.getMerchantId() == cartItem.getMerchantId())
                    {
                        merchantDto=dto;
                        break;
                    }
                }
                productMerchantDto.setMerchant(merchantDto);
                addToCart(productMerchantDto,cartItem);

            }


        }
        else
        {
            System.out.println("addition in cart failure");
        }




    }


}
