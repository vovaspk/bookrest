package com.vspk.bookrest.rest;

import com.vspk.bookrest.dto.AuthUserDetailsDto;
import com.vspk.bookrest.dto.RegisterUserDetailsDto;
import com.vspk.bookrest.payload.AuthApiError;
import com.vspk.bookrest.payload.LoginResponse;
import com.vspk.bookrest.payload.RegistrationResponse;
import com.vspk.bookrest.service.UserAuthService;
import com.vspk.bookrest.service.UserVerificationService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@RolesAllowed("USER")
@RequiredArgsConstructor
@Api(value="authorization entrypoint")
@CrossOrigin(origins = "*")
public class AuthenticationRestController {

    private final UserAuthService userAuthService;
    private final UserVerificationService userVerificationService;

//TODO make functional tests
    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthUserDetailsDto requestDto) {
        return userAuthService.authenticate(requestDto);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserDetailsDto requestDto) {
        return userAuthService.register(requestDto);
    }

    @GetMapping("test")
    public ResponseEntity<?> test() {
        return ResponseEntity.status(HttpStatus.OK).body(new TestDto("test response", Date.from(Instant.now())));
    }

    @GetMapping("email-verification/{token}/verify")
    public String verifyAccount(@PathVariable String token){
        return userVerificationService.confirmAccount(token);
    }

    @PostMapping("email-verification/resend")
    public ResponseEntity<?> reSendVerificationToken(@RequestParam String email){
        // add user a choice to confirm with phone or email
        // also need to add password reset function
        return userVerificationService.reSendVerificationToken(email);
    }


    @Data
    @AllArgsConstructor
    static class TestDto{
        private String response;
        private Date date;
    }
}
