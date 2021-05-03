package com.vspk.bookrest.payload;

import com.vspk.bookrest.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class LoginResponse {
    private final String username;
    private final String token;
    private final List<Role> roles;
    private final String verificationStatus;
}
