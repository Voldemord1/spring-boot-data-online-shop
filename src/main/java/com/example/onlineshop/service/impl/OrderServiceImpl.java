package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.Order;
import com.example.onlineshop.repository.OrderJpaRepository;
import com.example.onlineshop.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderJpaRepository orderJpaRepository;

    @Autowired
    public OrderServiceImpl(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    @Transactional
    public void addOrder(Order order) {
        orderJpaRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> getOrderById(Long orderId) {
        return orderJpaRepository.findById(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> getOrderByUserId(Long userId) {
        return orderJpaRepository.getOrderByBasketUserId(userId);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        orderJpaRepository.deleteById(orderId);
    }

    @Override
    @Transactional
    public List<Order> getOrders() {
        return orderJpaRepository.findAll();
    }
}
