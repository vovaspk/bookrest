package com.vspk.bookrest.security;

import com.vspk.bookrest.domain.Role;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface JwtTokenProvider {
    String createToken(String username, List<Role> roles);

    Authentication getAuthentication(String token);

    String getUsername(String token);

    String resolveToken(HttpServletRequest req);

    boolean isTokenValid(String token);
}
