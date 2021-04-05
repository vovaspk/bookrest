package com.vspk.bookrest.service.impl;

import com.vspk.bookrest.domain.User;
import com.vspk.bookrest.repository.RoleRepository;
import com.vspk.bookrest.repository.UserRepository;
import com.vspk.bookrest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User save(User user){
        User savedUser = userRepository.save(user);
        log.info("In save - user saved with userId: {}", savedUser.getId());
        return savedUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        var foundUser = userRepository.findByUsername(username);
        if(foundUser.isEmpty()){
            log.warn("user not found with username: {}", username);
            return Optional.empty();
        }
        log.info("IN findByUsername - user: {} found by username: {}", foundUser, username);
        return foundUser;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var foundUser = userRepository.findUserByEmail(email);
        if(foundUser.isEmpty()){
            log.warn("user not found with email: {}", email);
            return Optional.empty();
        }
        log.info("IN findByEmail - user: {} found with email: {}", foundUser, email);
        return foundUser;
    }

    @Override
    public Optional<User> findById(Long id) {
        var result = userRepository.findById(id);

        if (result.isEmpty()) {
            log.warn("IN findById - user not found by id: {}", id);
            return Optional.empty();
        }

        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
