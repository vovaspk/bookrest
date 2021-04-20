package com.vspk.bookrest.rest;

import com.vspk.bookrest.dto.AuthenticationRequestDto;
import com.vspk.bookrest.dto.RegistrationDto;
import com.vspk.bookrest.payload.LoginResponse;
import com.vspk.bookrest.payload.RegistrationResponse;
import com.vspk.bookrest.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@RolesAllowed("USER")
@RequiredArgsConstructor
@Api(value="authorization entrypoint")
public class AuthenticationRestController {

    private final UserAuthService userAuthService;

//TODO make functional tests
    @ApiOperation(value = "login",response = LoginResponse.class)
    @CrossOrigin(origins = "*")
    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequestDto requestDto) {
        return userAuthService.authenticate(requestDto);
    }

    @ApiOperation(value = "View a list of available products",response = RegistrationResponse.class)
    @CrossOrigin(origins = "*")
    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid RegistrationDto requestDto) {
        return userAuthService.register(requestDto);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("test")
    public ResponseEntity test() {
        return ResponseEntity.status(HttpStatus.OK).body(new TestDto("test response", Date.from(Instant.now())));
    }

    @Data
    @AllArgsConstructor
    static class TestDto{
        private String response;
        private Date date;
    }
}
