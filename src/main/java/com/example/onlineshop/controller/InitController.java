package com.example.onlineshop.controller;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.service.interfaces.ProductService;
import com.example.onlineshop.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class InitController {

    private UserService userService;
    private ProductService productService;

    @Autowired
    public InitController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @PostConstruct
    private void init() {
        userService.addUser("admin@gmail.com", "123", "ROLE_ADMIN");
        userService.addUser("user@gmail.com", "123", "ROLE_USER");
        userService.addUser("test@gmail.com", "123", "ROLE_USER");
        Product product1 = new Product("Phone", "iPhone X 128G", 699.0);
        Product product2 = new Product("Watch", "wrist watch", 399.0);
        Product product3 = new Product("MacBook", "laptop", 2699.0);
        Product product4 = new Product("iPad", "tablet", 1100.0);
        productService.addProduct(product1);
        productService.addProduct(product2);
        productService.addProduct(product3);
        productService.addProduct(product4);
    }
}
