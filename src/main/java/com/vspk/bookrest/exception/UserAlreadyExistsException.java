package com.vspk.bookrest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class UserAlreadyExistsException extends RuntimeException{
    private HttpStatus status;
    public UserAlreadyExistsException(String username){
        super("User with username: " + username + " already exists");
    }

    public UserAlreadyExistsException(String username, HttpStatus status){
        super("User with username: " + username + " already exists");
        this.status = status;
    }
}
