package com.vspk.bookrest.service;

import com.vspk.bookrest.dto.AuthenticationRequestDto;

import java.util.Map;

public interface AuthenticationService {
    Map<Object, Object> login(AuthenticationRequestDto authenticationRequestDto);
}
