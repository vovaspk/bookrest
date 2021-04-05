package com.vspk.bookrest.service.impl;

import com.vspk.bookrest.AbstractContainerITest;
import com.vspk.bookrest.repository.RoleRepository;
import com.vspk.bookrest.repository.UserRepository;
import com.vspk.bookrest.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplITest extends AbstractContainerITest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void register() {
    }

    @Test
    void getAll() {
    }

    @Test
    void findByUsername() {
    }

    @Test
    void findById() {
    }

    @Test
    void delete() {
    }
}