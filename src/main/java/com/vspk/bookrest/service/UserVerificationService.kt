package com.vspk.bookrest.service

import org.springframework.http.ResponseEntity

interface UserVerificationService {
    fun confirmAccount(code: String): String
    fun reSendVerificationToken(email: String): ResponseEntity<*>
}