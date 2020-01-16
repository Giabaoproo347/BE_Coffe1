package com.codegym.services.impl;

import com.codegym.models.ProductPicture;
import com.codegym.repositories.ProductPictureRepository;
import com.codegym.services.ProductPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProductPictureServiceImpl implements ProductPictureService {
    @Autowired
    private ProductPictureRepository productPictureRepository;

    @Override
    public Iterable<ProductPicture> findAll() {
        return productPictureRepository.findAll();
    }

    @Override
    public Optional<ProductPicture> findById(Long id) {
        return productPictureRepository.findById(id);
    }

    @Override
    public void save(ProductPicture productPicture) {
        productPictureRepository.save(productPicture);
    }

    @Override
    public void remove(Long id) {
        productPictureRepository.deleteById(id);
    }
}
