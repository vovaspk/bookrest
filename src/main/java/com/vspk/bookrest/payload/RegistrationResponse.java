package com.vspk.bookrest.payload;

import com.vspk.bookrest.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class RegistrationResponse {
    private final User registeredUser;
}
