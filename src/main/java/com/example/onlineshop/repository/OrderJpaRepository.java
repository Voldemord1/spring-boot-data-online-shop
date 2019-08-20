package com.example.onlineshop.repository;

import com.example.onlineshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    Optional<Order> getOrderByBasketUserId(Long userId);
}
