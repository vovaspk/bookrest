package com.vspk.bookrest.rest;

import com.vspk.bookrest.dto.AuthenticationRequestDto;
import com.vspk.bookrest.dto.RegistrationDto;
import com.vspk.bookrest.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@RolesAllowed("USER")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final UserAuthService userAuthService;

    @CrossOrigin(origins = "*")
    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequestDto requestDto) {
        Map<Object, Object> response = userAuthService.authenticate(requestDto);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid RegistrationDto requestDto) {
        return userAuthService.register(requestDto);
    }

}
