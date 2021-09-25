package com.vspk.bookrest.rest

import com.vspk.bookrest.dto.AuthUserDetailsDto
import com.vspk.bookrest.dto.RegisterUserDetailsDto
import com.vspk.bookrest.payload.LoginResponse
import com.vspk.bookrest.payload.RegistrationResponse
import com.vspk.bookrest.service.UserAuthService
import com.vspk.bookrest.service.UserVerificationService
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*
import javax.annotation.security.RolesAllowed
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/auth/")
@RolesAllowed("USER")
@Api(value = "authorization entrypoint")
@CrossOrigin(origins = ["*"])
class AuthenticationRestController (
    private val userAuthService: UserAuthService,
    private val userVerificationService: UserVerificationService
){

    //TODO make functional tests
    @PostMapping("login")
    fun login(@RequestBody requestDto: @Valid AuthUserDetailsDto): ResponseEntity<LoginResponse> {
        return userAuthService.authenticate(requestDto)
    }

    @PostMapping("register")
    fun register(@RequestBody requestDto: @Valid RegisterUserDetailsDto): ResponseEntity<RegistrationResponse> {
        return userAuthService.register(requestDto)
    }

    @GetMapping("test")
    fun test(): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.OK).body(TestDto("test response", Date.from(Instant.now())))
    }

    @GetMapping("email-verification/{token}/verify")
    fun verifyAccount(@PathVariable token: String): String {
        return userVerificationService.confirmAccount(token)
    }

    @PostMapping("email-verification/resend")
    fun reSendVerificationToken(@RequestParam email: String): ResponseEntity<*> {
        // add user a choice to confirm with phone or email
        // also need to add password reset function
        return userVerificationService.reSendVerificationToken(email)
    }

    internal data class TestDto (val response: String?, val date: Date?)
}