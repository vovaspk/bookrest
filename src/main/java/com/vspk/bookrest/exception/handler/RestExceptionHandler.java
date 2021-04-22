package com.vspk.bookrest.exception.handler;

import com.vspk.bookrest.exception.auth.JwtAuthenticationException;
import com.vspk.bookrest.exception.auth.UserAlreadyExistsException;
import com.vspk.bookrest.payload.AuthApiError;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Order(value = HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    protected ResponseEntity<Object> userAlreadyExists(UserAlreadyExistsException ex){
        var authApiError = new AuthApiError(ex.getMessage());
        return buildResponseEntity(authApiError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = JwtAuthenticationException.class)
    protected ResponseEntity<Object> loginFailed(JwtAuthenticationException ex){
        var authApiError = new AuthApiError(ex.getMessage());
        return buildResponseEntity(authApiError, ex.getHttpStatus());

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return buildResponseEntity(new AuthApiError("validation failed", errors), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseEntity(AuthApiError apiError, HttpStatus status) {
        return new ResponseEntity<>(apiError, status);
    }
}
