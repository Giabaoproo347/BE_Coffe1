package com.codegym.controllers;


import com.codegym.models.Order;
import com.codegym.models.Payment;
import com.codegym.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @GetMapping("")
    public ResponseEntity<Iterable<Payment>> showListPayment() {
        Iterable<Payment> payments = paymentService.findAll();
        return new ResponseEntity<Iterable<Payment>>(payments, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addNewPayment(@Valid @RequestBody Payment payment) {
        try {
            paymentService.save(payment);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.findById(id);
        if (payment.isPresent()) {
            return new ResponseEntity<>(payment.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        Optional<Payment> currentPayment = paymentService.findById(id);
        if (currentPayment.isPresent()) {
            currentPayment.get().setId(id);
            currentPayment.get().setName(payment.getName());
            currentPayment.get().setDate(payment.getDate());
            currentPayment.get().setOrders(payment.getOrders());

            paymentService.save(payment);
            return new ResponseEntity<Payment>(currentPayment.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Payment>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Payment> deletePayment(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.findById(id);
        if (payment.isPresent()) {
            paymentService.remove(id);
            return new ResponseEntity<Payment>(HttpStatus.OK);
        }
        return new ResponseEntity<Payment>(HttpStatus.NOT_FOUND);
    }
}
