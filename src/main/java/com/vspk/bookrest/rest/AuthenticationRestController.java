package com.vspk.bookrest.rest;

import com.vspk.bookrest.dto.AuthenticationRequestDto;
import com.vspk.bookrest.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@RolesAllowed("USER")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final AuthenticationService authenticationService;

    @CrossOrigin(origins = "*")
    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        Map<Object, Object> response = authenticationService.login(requestDto);
        return ResponseEntity.ok(response);
    }

}
