package com.example.onlineshop.service.interfaces;

import com.example.onlineshop.model.Basket;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.model.User;

import java.util.List;
import java.util.Optional;

public interface BasketService {

    void addBasket(Basket basket);

    List<Basket> getAll();

    Optional<Basket> getBasketById(Long basketId);

    Optional<Basket> getBasketByUserId(Long userId);

    void addProduct(Basket basket, Product product);

    void removeProduct(Basket basket, Product product);

    void clearBasketById(Long basketId);
}
