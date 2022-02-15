//package com.example.cart_apii.test;
//
//import com.example.cart_apii.dto.CartDto;
//import com.example.cart_apii.dto.CartProducts;
//import com.example.cart_apii.service.CartServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Test {
//    static  CartServiceImpl cartService=new CartServiceImpl();
//
//    public static void main(String[] args) {
//        CartProducts cartProducts1 = new CartProducts("1", 2, 3);
//        CartProducts cartProducts2 = new CartProducts("2", 3, 1);
//        CartProducts cartProducts3 = new CartProducts("1", 2, 3);
//
//        CartDto cartDto1 = new CartDto("1", "1@gmail.com", 0, cartProducts1);
//        CartDto cartDto2 = new CartDto("1", "1@gmail.com", 0, cartProducts2);
//
////        CartDto cartDto3 = new CartDto("1", "1@gmail.com", 0, cartProducts3);
//
//
//        List<CartProducts> cartProductsList = new ArrayList<>();
//        cartProductsList.add(cartProducts1);
//        cartProductsList.add(cartProducts2);
//
//        System.out.println(cartProductsList.indexOf(cartProducts3));
//
//
//        if (cartProductsList.contains(cartProducts3)) {
//
//            System.out.println("contains");
//        } else {
//            System.out.println("doesn't contains");
//        }
//    }
//}
