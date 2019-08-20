package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.Basket;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.BasketJpaRepository;
import com.example.onlineshop.service.interfaces.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BasketServiceImpl implements BasketService {

    private final BasketJpaRepository basketJpaRepository;

    @Autowired
    public BasketServiceImpl(BasketJpaRepository basketJpaRepository) {
        this.basketJpaRepository = basketJpaRepository;
    }

    @Override
    @Transactional
    public void addBasket(Basket basket) {
        basketJpaRepository.save(basket);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Basket> getAll() {
        return basketJpaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Basket> getBasketById(Long basketId) {
        return basketJpaRepository.findById(basketId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Basket> getBasketByUserId(Long userId) {
        return basketJpaRepository.getBasketByUserId(userId);
    }

    @Override
    @Transactional
    public void addProduct(Basket basket, Product product) {
        basket.getProductList().add(product);
        basketJpaRepository.save(basket);
    }

    @Override
    @Transactional
    public void removeProduct(Basket basket, Product product) {
        basket.getProductList().remove(product);
        basketJpaRepository.save(basket);
    }

    @Override
    @Transactional
    public void clearBasketById(Long basketId) {
        if (getBasketById(basketId).isPresent()) {
            Basket basket = getBasketById(basketId).get();
            basket.getProductList().clear();
            basketJpaRepository.save(basket);
        }
    }
}
