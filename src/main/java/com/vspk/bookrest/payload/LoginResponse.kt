package com.vspk.bookrest.payload

import com.vspk.bookrest.domain.Role


data class LoginResponse(
    val username: String,
    val token: String,
    val roles: List<Role>,
    val verificationStatus: String
)