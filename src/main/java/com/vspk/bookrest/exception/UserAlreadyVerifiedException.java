package com.vspk.bookrest.exception;

import lombok.Getter;

@Getter
public class UserAlreadyVerifiedException extends RuntimeException{
    private final String username;
    private final Long id;
    private final String message;

    public UserAlreadyVerifiedException(String username, String message) {
        this.username = username;
        this.message = message;
        this.id = null;
    }

    public UserAlreadyVerifiedException(Long id, String message) {
        this.id = id;
        this.message = message;
        this.username = null;
    }
}
