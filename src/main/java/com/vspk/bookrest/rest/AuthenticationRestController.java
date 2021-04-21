package com.vspk.bookrest.rest;

import com.vspk.bookrest.dto.AuthenticationRequestDto;
import com.vspk.bookrest.dto.RegistrationDto;
import com.vspk.bookrest.payload.AuthFailedResponse;
import com.vspk.bookrest.payload.LoginResponse;
import com.vspk.bookrest.payload.RegistrationResponse;
import com.vspk.bookrest.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully logged in system", response = LoginResponse.class),
            @ApiResponse(code = 401, message = "username or password is wrong, authorization failed", response = AuthFailedResponse.class)
    })
    @CrossOrigin(origins = "*")
    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequestDto requestDto) {
        return userAuthService.authenticate(requestDto);
    }

    @ApiOperation(value = "registration",response = RegistrationResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully registered user", response = RegistrationResponse.class),
            @ApiResponse(code = 401, message = "failed to register user, check username and email on uniqueness", response = AuthFailedResponse.class)
    })
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
