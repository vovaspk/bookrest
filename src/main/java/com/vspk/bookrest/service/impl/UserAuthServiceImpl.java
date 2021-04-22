package com.vspk.bookrest.service.impl;

import com.vspk.bookrest.domain.Role;
import com.vspk.bookrest.domain.Status;
import com.vspk.bookrest.domain.User;
import com.vspk.bookrest.dto.AuthenticationRequestDto;
import com.vspk.bookrest.dto.RegistrationDto;
import com.vspk.bookrest.exception.auth.JwtAuthenticationException;
import com.vspk.bookrest.exception.auth.UserAlreadyExistsException;
import com.vspk.bookrest.payload.LoginResponse;
import com.vspk.bookrest.payload.RegistrationResponse;
import com.vspk.bookrest.repository.RoleRepository;
import com.vspk.bookrest.security.JwtTokenProvider;
import com.vspk.bookrest.service.UserAuthService;
import com.vspk.bookrest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> authenticate(AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            var user = userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            return ResponseEntity.ok().body(new LoginResponse(username, token, user.getRoles()));
        } catch (AuthenticationException e) {
            throw new JwtAuthenticationException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<?> register(RegistrationDto dto) {
        validate(dto);

        Role userRole = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);

        var newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setRoles(userRoles);
        newUser.setStatus(Status.ACTIVE);

        var registeredUser = userService.save(newUser);

        log.info("user successfully registered: {} ", registeredUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegistrationResponse(registeredUser));

    }

    private void validate(RegistrationDto dto) {
        String username = dto.getUsername();
        String email = dto.getEmail();

        if (existsByUsername(username)) {
            throw new UserAlreadyExistsException(username, "Failed to register user, username [" + username + "] is already taken!");
        }

        if (existsByEmail(email)) {
            throw new UserAlreadyExistsException(email, "Failed to register user, email [" + email + "] is already in use!");
        }
    }

    private boolean existsByUsername(String username) {
        return userService.findByUsername(username).isPresent();
    }

    private boolean existsByEmail(String email) {
        return userService.findByEmail(email).isPresent();
    }
}
