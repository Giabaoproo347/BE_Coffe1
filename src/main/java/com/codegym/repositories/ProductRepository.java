package com.codegym.repositories;

import com.codegym.models.Category;
import com.codegym.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining (String name);
    List<Product> findAllByCategory (Category category);
}
