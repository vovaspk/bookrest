package com.vspk.bookrest.service.impl;

import com.vspk.bookrest.AbstractContainerITest;
import com.vspk.bookrest.domain.Role;
import com.vspk.bookrest.domain.Status;
import com.vspk.bookrest.domain.User;
import com.vspk.bookrest.dto.AuthenticationRequestDto;
import com.vspk.bookrest.dto.RegistrationDto;
import com.vspk.bookrest.repository.RoleRepository;
import com.vspk.bookrest.security.JwtTokenProvider;
import com.vspk.bookrest.service.UserAuthService;
import com.vspk.bookrest.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserAuthServiceImplITest extends AbstractContainerITest {

    @Autowired
    private UserAuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {

    }

    @Test
    void authenticate() {
        userService.save(getUser());

        Map<Object, Object> response = authService.authenticate(authDto());

        assertEquals("testusername", response.get("username"));
        assertNotNull(response.get("token"));
        assertEquals(getUser().getRoles(), response.get("roles"));
    }

    @Test
    void register() {
        ResponseEntity response = authService.register(registerDto());

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertTrue(response.getBody().toString().contains("{user=User(username=testusername, firstName=null, lastName=null, email=testemail@gmail.com, roles=[Role{id: 1, name: ROLE_USER}])}"));
    }

    private AuthenticationRequestDto authDto(){
        AuthenticationRequestDto dto = new AuthenticationRequestDto();
        dto.setUsername("testusername");
        dto.setPassword("1234");
        return dto;
    }

    private RegistrationDto registerDto(){
        RegistrationDto dto = new RegistrationDto();
        dto.setEmail("testemail@gmail.com");
        dto.setUsername("testusername");
        dto.setPassword("1234");
        dto.setMatchingPassword("1234");
        return dto;
    }

    private User getUser() {
        Role role_user = roleRepository.findByName("ROLE_USER");

        User user = new User();
        user.setEmail("testemail@gmail.com");
        user.setUsername("testusername");
        user.setFirstName("testusername");
        user.setLastName("testname2");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setRoles(List.of(role_user));
        user.setStatus(Status.ACTIVE);
        return user;
    }

    @AfterEach
    void tearDown() {
        userService.deleteAll();
    }
}