package com.vspk.bookrest.security

import com.vspk.bookrest.domain.Role
import javax.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication

interface JwtTokenProvider {
    fun createToken(username: String, roles: List<Role>): String
    fun getAuthentication(token: String): Authentication
    fun getUsername(token: String): String
    fun resolveToken(req: HttpServletRequest): String
    fun isTokenValid(token: String): Boolean
}