package com.codegym.services;

import com.codegym.models.Commenter;

import java.util.Optional;

public interface CommenterService {
    Optional<Commenter> findById(Long id);
    Iterable<Commenter> findAll();
    Commenter save(Commenter commenter);
    void delete(Long id);
    Iterable<Commenter> findCommentersByProductId(Long productId);
}
