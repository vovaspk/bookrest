package com.vspk.bookrest.service.impl;

import com.vspk.bookrest.domain.Role;
import com.vspk.bookrest.domain.Status;
import com.vspk.bookrest.domain.User;
import com.vspk.bookrest.dto.AuthenticationRequestDto;
import com.vspk.bookrest.dto.RegistrationDto;
import com.vspk.bookrest.repository.RoleRepository;
import com.vspk.bookrest.security.JwtTokenProvider;
import com.vspk.bookrest.service.UserAuthService;
import com.vspk.bookrest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public Map<Object, Object> authenticate(AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            var user = userService.findByUsername(username);

            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.get().getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            response.put("roles", user.get().getRoles());

            return response;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public ResponseEntity register(RegistrationDto dto){

        if (existsByUsername(dto.getUsername())) {
            return new ResponseEntity<>("Failed to register user, username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (existsByEmail(dto.getEmail())) {
            return new ResponseEntity<>("Failed to register user, email is already in use!", HttpStatus.BAD_REQUEST);
        }

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

        var response = new HashMap<>();
        response.put("user", registeredUser);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    private boolean existsByUsername(String username){
        return userService.findByUsername(username).isPresent();
    }

    private boolean existsByEmail(String email){
        return userService.findByEmail(email).isPresent();
    }
}
