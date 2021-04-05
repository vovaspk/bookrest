package com.vspk.bookrest.service.impl;

import com.vspk.bookrest.domain.Role;
import com.vspk.bookrest.domain.User;
import com.vspk.bookrest.repository.RoleRepository;
import com.vspk.bookrest.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll() {
        when(userRepository.findAll()).thenReturn(List.of(getUser(), getUser(), getUser()));

        List<User> all = userService.getAll();

        assertNotNull(all);
        assertFalse(all.isEmpty());
        assertEquals(3, all.size());

    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername("testUsername")).thenReturn(of(getUser()));

        Optional<User> foundUser = userService.findByUsername("testUsername");

        assertTrue(foundUser.isPresent());
    }

    @Test
    void findByUsernameNotFound() {
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findByUsername("testUsername");

        assertTrue(foundUser.isEmpty());
    }

    @Test
    void findById() {
        when(userRepository.findById(132L)).thenReturn(of(getUser()));

        Optional<User> foundUser = userService.findById(132L);

        assertTrue(foundUser.isPresent());
    }

    @Test
    void findByIdNotFound() {
        when(userRepository.findById(132L)).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findById(132L);

        assertTrue(foundUser.isEmpty());
    }

    @Test
    void delete() {
        Long testId = 142L;
        userService.delete(testId);
        verify(userRepository, times(1)).deleteById(testId);
    }

    @Test
    void save() {
        when(userRepository.save(getUser())).thenReturn(getUser());

        User savedUser = userService.save(getUser());

        assertNotNull(savedUser);
        assertEquals("testUsername", savedUser.getUsername());
    }

    @Test
    void findByEmail() {
        when(userRepository.findUserByEmail("testemail@gmail.com")).thenReturn(of(getUser()));

        Optional<User> foundUser = userService.findByEmail("testemail@gmail.com");

        assertTrue(foundUser.isPresent());
    }

    @Test
    void findByEmailNotFound() {
        when(userRepository.findUserByEmail("testemail@gmail.com")).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findByEmail("testemail@gmail.com");

        assertTrue(foundUser.isEmpty());
    }

    private User getUser() {
        User user = new User();
        user.setEmail("testemail@gmail.com");
        user.setUsername("testUsername");
        user.setFirstName("testname");
        user.setLastName("testname2");
        user.setPassword("1234");
        user.setRoles(List.of(new Role()));
        return user;
    }
}