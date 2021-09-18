package com.vspk.bookrest.exception.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.vspk.bookrest.payload.AuthApiError
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class UserAuthenticationEntrypoint : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest, response: HttpServletResponse, ex: AuthenticationException) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val mapper = ObjectMapper()
        val authApiError = AuthApiError(ex.message)
        mapper.writeValue(response.outputStream, authApiError)
    }
}