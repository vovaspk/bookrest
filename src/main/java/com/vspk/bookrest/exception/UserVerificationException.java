package com.vspk.bookrest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserVerificationException extends RuntimeException {
    private final String message;

    public UserVerificationException (String message){
        this.message = message;
    }
}
