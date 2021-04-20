package com.vspk.bookrest.service;

import com.vspk.bookrest.dto.AuthenticationRequestDto;
import com.vspk.bookrest.dto.RegistrationDto;
import com.vspk.bookrest.payload.LoginResponse;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserAuthService {
    ResponseEntity<?> authenticate(AuthenticationRequestDto authenticationRequestDto);
    ResponseEntity register(RegistrationDto registrationDto);
}
