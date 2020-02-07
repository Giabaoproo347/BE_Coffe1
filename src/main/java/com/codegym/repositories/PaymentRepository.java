package com.codegym.repositories;

import com.codegym.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByStatus (String status);
    List<Payment> findAllByUserId(Long id);
}
