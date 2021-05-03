package com.vspk.bookrest.service;

import org.springframework.http.ResponseEntity;

public interface UserVerificationService {
    String confirmAccount(String code);
    ResponseEntity<?> reSendVerificationToken(String email);
}
