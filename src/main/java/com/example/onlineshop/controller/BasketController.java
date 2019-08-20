package com.example.onlineshop.controller;

import com.example.onlineshop.model.Basket;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.User;
import com.example.onlineshop.service.interfaces.BasketService;
import com.example.onlineshop.service.interfaces.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class BasketController {

    private final Logger logger = Logger.getLogger(BasketController.class);
    private final BasketService basketService;
    private final ProductService productService;

    @Autowired
    public BasketController(BasketService basketService, ProductService productService) {
        this.basketService = basketService;
        this.productService = productService;
    }

    @GetMapping("/product/buy")
    public String buyProduct(
            @AuthenticationPrincipal User user,
            @RequestParam("productId") Long productId) {
        if (!basketService.getBasketByUserId(user.getId()).isPresent()) {
            basketService.addBasket(new Basket(user));
            logger.info("Basket created for user " + user.getId());
        }
        Optional<Basket> basketByUserId = basketService.getBasketByUserId(user.getId());
        if (basketByUserId.isPresent()) {
            Optional<Product> productById = productService.getProductById(productId);
            if (productById.isPresent()) {
                basketService.addProduct(basketByUserId.get(), productById.get());
            }
        }
        return "redirect:/user/products";
    }

    @GetMapping("/checkout")
    public ModelAndView showProductsInBasket(@AuthenticationPrincipal User user, ModelMap model) {
        Optional<Basket> basketByUserId = basketService.getBasketByUserId(user.getId());
        if (basketByUserId.isPresent()) {
            model.addAttribute("products", basketByUserId.get().getProductList());
        }
        return new ModelAndView("checkout", model);
    }

    @GetMapping(path = "/basket/product/delete")
    public String deleteProductFromBasket(
            @AuthenticationPrincipal User user,
            @RequestParam("id") Long productId) {
        Optional<Basket> basketByUserId = basketService.getBasketByUserId(user.getId());
        Optional<Product> productById = productService.getProductById(productId);
        if (basketByUserId.isPresent() && productById.isPresent()) {
            basketService.removeProduct(basketByUserId.get(), productById.get());
            return "redirect:/user/checkout?userId=" + user.getId();
        } else {
            logger.warn("The basket with user id: " + user.getId() + " isn't exist");
        }
        return "redirect:/user/checkout";
    }
}
