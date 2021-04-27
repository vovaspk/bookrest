package com.vspk.bookrest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthUserDetailsDto {
    @NotBlank(message = "username should not be empty")
    private String username;
    @NotBlank(message = "password should not be empty")
    private String password;
}
