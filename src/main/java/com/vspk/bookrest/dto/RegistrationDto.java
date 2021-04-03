package com.vspk.bookrest.dto;

import lombok.Data;

@Data
public class RegistrationDto {
    private String email;
    private String username;
    private String password;
    private String matchingPassword;
}
