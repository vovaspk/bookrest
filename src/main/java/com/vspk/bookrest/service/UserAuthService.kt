package com.vspk.bookrest.service

import com.vspk.bookrest.dto.AuthUserDetailsDto
import com.vspk.bookrest.dto.RegisterUserDetailsDto
import com.vspk.bookrest.payload.LoginResponse
import com.vspk.bookrest.payload.RegistrationResponse
import org.springframework.http.ResponseEntity

interface UserAuthService {
    fun authenticate(authUserDetailsDto: AuthUserDetailsDto): ResponseEntity<LoginResponse>
    fun register(registerUserDetailsDto: RegisterUserDetailsDto): ResponseEntity<RegistrationResponse>
}