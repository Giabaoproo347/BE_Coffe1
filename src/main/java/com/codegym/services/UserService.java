package com.codegym.services;

import com.codegym.models.user.User;

import java.util.Optional;

public interface UserService {
    Iterable<User> findAll();
    Optional<User> findById (Long id);
    void save (User user);
    void remove (Long id);
}
