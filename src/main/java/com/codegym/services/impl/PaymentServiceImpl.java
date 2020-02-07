package com.codegym.services.impl;

import com.codegym.models.Payment;
import com.codegym.repositories.PaymentRepository;
import com.codegym.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Iterable<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Iterable<Payment> findAllByStatus(String status) {
        return paymentRepository.findAllByStatus(status);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public List<Payment> findAllByUserId(Long id) {
        return paymentRepository.findAllByUserId(id);
    }

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public void remove(Long id) {
        paymentRepository.deleteById(id);
    }
}
