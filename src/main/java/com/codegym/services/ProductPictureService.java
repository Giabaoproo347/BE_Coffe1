package com.codegym.services;

import com.codegym.models.ProductPicture;

import java.util.Optional;

public interface ProductPictureService {
    Iterable<ProductPicture> findAll();
    Optional<ProductPicture> findById(Long id);
    void save(ProductPicture productPicture);
    void remove(Long id);
}
