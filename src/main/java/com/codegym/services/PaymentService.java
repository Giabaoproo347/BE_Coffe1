package com.codegym.services;

import com.codegym.models.Payment;

import java.util.Optional;

public interface PaymentService {
    Iterable<Payment> findAll();
    Optional<Payment> findById(Long id);
    void save(Payment payment);
    void remove(Long id);
}
