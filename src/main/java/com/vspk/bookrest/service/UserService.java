package com.vspk.bookrest.service;

import com.vspk.bookrest.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    List<User> getAll();

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    void delete(Long id);
}
