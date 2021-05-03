package com.vspk.bookrest.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vspk.bookrest.payload.AuthApiError;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationEntrypoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex)
            throws IOException, ServletException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final var mapper = new ObjectMapper();
        var authApiError = new AuthApiError(ex.getMessage());

        mapper.writeValue(response.getOutputStream(), authApiError);
    }
}
