package com.vspk.bookrest.exception.auth;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {
    private final String username;
    private final String message;

    public UserAlreadyExistsException(String username, String message) {
        this.username = username;
        this.message = message;
    }

}
