package com.vspk.bookrest.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import lombok.Data

@Data
class RegisterUserDetailsDto(
    val email: @NotBlank(message = "email is mandatory") @Email(message = "invalid email") String,
    val username: @NotBlank(message = "username is mandatory") String,
    val password: @NotBlank(message = "password is mandatory") String,
    val matchingPassword: @NotBlank(message = "matching password is mandatory") String
)