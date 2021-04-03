package com.vspk.bookrest.rest;

import com.vspk.bookrest.domain.User;
import com.vspk.bookrest.dto.AuthenticationRequestDto;
import com.vspk.bookrest.dto.RegistrationDto;
import com.vspk.bookrest.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.ResponseMessage;

import javax.annotation.security.RolesAllowed;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
@RolesAllowed("USER")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final UserAuthService userAuthService;

    @CrossOrigin(origins = "*")
    @PostMapping("login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
        Map<Object, Object> response = userAuthService.authenticate(requestDto);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegistrationDto requestDto) {
//        if (userRepository.existsByUsername(requestDto.getUsername())) {
//            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
//                    HttpStatus.BAD_REQUEST);
//        }
//
//        if (userRepository.existsByEmail(requestDto.getEmail())) {
//            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
//                    HttpStatus.BAD_REQUEST);
//        }

        return userAuthService.register(requestDto);
        //return new ResponseEntity<>(new ResponseMessage("User with username: " + registeredUser.getUsername() + " registered successfully"), HttpStatus.CREATED);
    }

}
