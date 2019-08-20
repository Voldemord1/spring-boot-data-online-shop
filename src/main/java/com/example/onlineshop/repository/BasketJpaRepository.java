package com.example.onlineshop.repository;

import com.example.onlineshop.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketJpaRepository extends JpaRepository<Basket, Long> {

    Optional<Basket> getBasketByUserId(Long userId);
}
