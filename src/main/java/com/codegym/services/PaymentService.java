package com.codegym.services;

import com.codegym.models.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Iterable<Payment> findAll();
    Iterable<Payment> findAllByStatus (String status);
    Optional<Payment> findById(Long id);
    List<Payment> findAllByUserId(Long id);
    void save(Payment payment);
    void remove(Long id);
}
