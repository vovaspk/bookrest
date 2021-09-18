package com.vspk.bookrest.service.impl;

import com.vspk.bookrest.domain.Role;
import com.vspk.bookrest.domain.Status;
import com.vspk.bookrest.domain.User;
import com.vspk.bookrest.dto.AuthUserDetailsDto;
import com.vspk.bookrest.dto.RegisterUserDetailsDto;
import com.vspk.bookrest.exception.auth.JwtAuthenticationException;
import com.vspk.bookrest.exception.auth.UserAlreadyExistsException;
import com.vspk.bookrest.repository.RoleRepository;
import com.vspk.bookrest.security.JwtTokenProvider;
import com.vspk.bookrest.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceImplUTest {

    @InjectMocks
    private UserAuthServiceImpl authService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserService userService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @BeforeEach
    void setUp() {
        authService = new UserAuthServiceImpl(authenticationManager, jwtTokenProvider, userService, roleRepository, passwordEncoder, applicationEventPublisher);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void authenticate() {
        Authentication auth = mock(Authentication.class);
        auth.setAuthenticated(true);

        User testUser = getUser();

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(userService.findByUsername("testusername")).thenReturn(Optional.of(testUser));
        when(jwtTokenProvider.createToken(testUser.getUsername(), testUser.getRoles())).thenReturn("successToken");

        ResponseEntity<?> response = authService.authenticate(authRequestDto());

        assertTrue(response.getBody().toString().contains("testusername"));
        assertTrue(response.getBody().toString().contains("successToken"));
        assertTrue(response.getBody().toString().contains(("roles")));

    }

    @Test
    void authenticateUserNotFound() {
        Authentication auth = mock(Authentication.class);
        auth.setAuthenticated(false);

        //var response = authService.authenticate(authRequestDto());
        assertThrows(JwtAuthenticationException.class, () -> authService.authenticate(authRequestDto()));
//        assertEquals(401, response.getStatusCode().value());
//        assertEquals("AuthFailedResponse(errorMessage=Invalid username or password)", response.getBody().toString());
    }

    @Test
    void register() {
        RegisterUserDetailsDto registerDto = registerDto();
        Role userRole = getUserRole();

        when(userService.findByUsername(registerDto.getUsername())).thenReturn(Optional.empty());
        when(userService.findByEmail(registerDto.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        when(userService.save(any())).thenReturn(getUser());

        var registerResponse = authService.register(registerDto);

        assertNotNull(registerResponse.getBody());
        assertEquals(201, registerResponse.getStatusCode().value());

    }

    private Role getUserRole() {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        userRole.setId(1L);
        userRole.setStatus(Status.ACTIVE);
        userRole.setCreated(new Date());
        userRole.setUpdated(new Date());
        userRole.setUsers(List.of(getUser()));
        return userRole;
    }

    @Test
    void registerUsernameIsAlreadyTaken() {
        RegisterUserDetailsDto registerDto = registerDto();

        when(userService.findByUsername(registerDto.getUsername())).thenReturn(Optional.of(getUser()));

        //var registerResponse = authService.register(registerDto);
        assertThrows(UserAlreadyExistsException.class, () -> authService.register(registerDto));
        //assertEquals(400, registerResponse.getStatusCode().value());

    }

    @Test
    void registerEmailIsAlreadyInUse() {
        RegisterUserDetailsDto registerDto = registerDto();

        when(userService.findByEmail(registerDto.getEmail())).thenReturn(Optional.of(getUser()));


        //var registerResponse = authService.register(registerDto);
        assertThrows(UserAlreadyExistsException.class, () -> authService.register(registerDto));
        //assertEquals(400, registerResponse.getStatusCode().value());
    }

    private RegisterUserDetailsDto registerDto() {
        RegisterUserDetailsDto dto = new RegisterUserDetailsDto();
        dto.setEmail("testemail@gmail.com");
        dto.setUsername("testusername");
        dto.setPassword("1234");
        dto.setMatchingPassword("1234");
        return dto;
    }

    private AuthUserDetailsDto authRequestDto() {
        var authdto = new AuthUserDetailsDto();
        authdto.setUsername("testusername");
        authdto.setPassword("1234");

        return authdto;
    }

    private User getUser() {
        var role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        role.setStatus(Status.ACTIVE);

        User user = new User();
        user.setEmail("testemail@gmail.com");
        user.setUsername("testusername");
        user.setFirstName("testusername");
        user.setLastName("testname2");
        user.setPassword("1234");
        user.setRoles(List.of(role));
        user.setStatus(Status.ACTIVE);
        return user;
    }
}