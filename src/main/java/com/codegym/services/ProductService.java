package com.codegym.services;

import com.codegym.models.Product;

import java.util.Optional;

public interface ProductService {
    Iterable<Product> findAllBook();
    Optional<Product> findById(Long id);
    void save(Product product);
    void remove(Long id);
}
