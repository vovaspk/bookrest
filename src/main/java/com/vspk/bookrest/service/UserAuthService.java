package com.vspk.bookrest.service;

import com.vspk.bookrest.dto.AuthUserDetailsDto;
import com.vspk.bookrest.dto.RegisterUserDetailsDto;
import org.springframework.http.ResponseEntity;

public interface UserAuthService {
    ResponseEntity<?> authenticate(AuthUserDetailsDto authUserDetailsDto);
    ResponseEntity<?> register(RegisterUserDetailsDto registerUserDetailsDto);
}
