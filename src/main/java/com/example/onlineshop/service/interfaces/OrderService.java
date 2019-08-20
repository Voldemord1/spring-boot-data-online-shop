package com.example.onlineshop.service.interfaces;

import com.example.onlineshop.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    void addOrder(Order order);

    Optional<Order> getOrderById(Long orderId);

    Optional<Order> getOrderByUserId(Long userId);

    void deleteOrder(Long orderId);

    List<Order> getOrders();
}
