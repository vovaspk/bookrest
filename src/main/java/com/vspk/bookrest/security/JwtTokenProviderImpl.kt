package com.vspk.bookrest.security

import com.vspk.bookrest.domain.Role
import com.vspk.bookrest.exception.auth.JwtAuthenticationException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Base64
import java.util.Date
import java.util.function.Consumer
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class JwtTokenProviderImpl : JwtTokenProvider {
    @Value("\${jwt.token.secret}")
    private var secret: String? = null

    @Value("\${jwt.token.expired}")
    private val validityInMilliseconds: Long = 0

    private var userDetailsService: UserDetailsService? = null

    @Autowired
    fun setUserDetailsService(@Qualifier("jwtUserDetailsService") userDetailsService: UserDetailsService) {
        this.userDetailsService = userDetailsService
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @PostConstruct
    protected fun init() {
        secret = Base64.getEncoder().encodeToString(secret!!.toByteArray())
    }

    override fun createToken(username: String, roles: List<Role>): String {
        val claims = Jwts.claims().setSubject(username)
        claims["roles"] = getRoleNames(roles)
        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    override fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService!!.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    override fun getUsername(token: String): String {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject
    }

    override fun resolveToken(req: HttpServletRequest): String {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            bearerToken.substring(7)
        } else ""
    }

    override fun isTokenValid(token: String): Boolean {
        return try {
            val claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (e: JwtException) {
            throw JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED)
        } catch (e: IllegalArgumentException) {
            throw JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED)
        }
    }

    private fun getRoleNames(userRoles: List<Role>): List<String?> {
        val result: MutableList<String?> = ArrayList()
        userRoles.forEach(Consumer { role: Role -> result.add(role.name) })
        return result
    }
}