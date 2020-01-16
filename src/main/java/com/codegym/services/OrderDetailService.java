package com.codegym.services;

import com.codegym.models.OrderDetail;

import java.util.Optional;

public interface OrderDetailService {
    Iterable<OrderDetail> findAll();

    Optional<OrderDetail> findById(Long id);

    void save(OrderDetail orderDetail);

    void remove(Long id);
}

