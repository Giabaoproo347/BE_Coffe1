package com.codegym.services;

import com.codegym.models.Product;
import com.codegym.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Iterable<Product> findAllBook();
    Optional<Product> findById(Long id);
    void save(Product product);
    void remove(Long id);

    Iterable<Product> findByName(String name);
    Iterable<Product> findProductsByNameContaining(String name);

    List<Product> findByNameContaining (String name);
    List<Product> findByCategoryId (Long  id);
    List<Product> findByPromotionId (Long id);
    Page<Product> findAll(Pageable pageable);

}
