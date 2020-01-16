package com.codegym.services;

import com.codegym.models.Promotion;

import java.util.Optional;

public interface PromotionService {
    Iterable<Promotion> findAll();

    Optional<Promotion> findById(Long id);

    void save(Promotion promotion);

    void remove(Long id);
}
