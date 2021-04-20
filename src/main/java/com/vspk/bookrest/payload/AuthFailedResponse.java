package com.vspk.bookrest.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class AuthFailedResponse {
    private final String errorMessage;
}
