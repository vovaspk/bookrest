package com.vspk.bookrest.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;

@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class AuthApiError {
    private final String errorMessage;
    private Map<String, String> validationErrors;
}
