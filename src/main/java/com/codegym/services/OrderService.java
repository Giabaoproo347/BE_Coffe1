package com.codegym.services;

import com.codegym.models.Order;

import java.util.Optional;

public interface OrderService {
    Iterable<Order> findAll();
    Optional<Order> findById(Long id);
    void save(Order order);
    void remove(Long id);
}
