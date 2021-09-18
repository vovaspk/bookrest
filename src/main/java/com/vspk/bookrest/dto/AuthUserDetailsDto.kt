package com.vspk.bookrest.dto

import javax.validation.constraints.NotBlank
import lombok.Data


data class AuthUserDetailsDto(
    val username: @NotBlank(message = "username should not be empty") String,
    val password: @NotBlank(message = "password should not be empty") String
)
