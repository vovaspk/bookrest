package com.vspk.bookrest.security

import com.vspk.bookrest.exception.auth.JwtAuthenticationException
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider) : GenericFilterBean() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, res: ServletResponse, filterChain: FilterChain) {

        val token = jwtTokenProvider.resolveToken((req as HttpServletRequest))

        try {
            if (token.isNotEmpty() && jwtTokenProvider.isTokenValid(token)) {
                val authentication = jwtTokenProvider.getAuthentication(token)
                if (authentication != null) {
                    SecurityContextHolder.getContext().authentication = authentication
                    (res as HttpServletResponse).setHeader("Access-Control-Allow-Origin", "*")
                    res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, HEAD, OPTIONS, DELETE")
                    res.setHeader("Access-Control-Max-Age", "3600")
                }
            }
        } catch (e: JwtAuthenticationException) {
            SecurityContextHolder.clearContext()
            (res as HttpServletResponse).status = HttpServletResponse.SC_UNAUTHORIZED
            filterChain.doFilter(req, res)
            throw JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED)
        }
        filterChain.doFilter(req, res)
    }
}