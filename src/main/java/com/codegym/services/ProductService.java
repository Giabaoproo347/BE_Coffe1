package com.codegym.services;

import com.codegym.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Iterable<Product> findAll();
    Optional<Product> findById(Long id);
    void save(Product product);
    void remove(Long id);

    List<Product> findByNameContaining (String name);
    List<Product> findByCategoryId (Long  id);
    List<Product> findByPromotionId (Long id);
}
