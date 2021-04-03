package com.vspk.bookrest.service;

import com.vspk.bookrest.dto.AuthenticationRequestDto;
import com.vspk.bookrest.dto.RegistrationDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserAuthService {
    Map<Object, Object> authenticate(AuthenticationRequestDto authenticationRequestDto);
    ResponseEntity register(RegistrationDto registrationDto);
}
