package com.vspk.bookrest.config

import com.vspk.bookrest.exception.handler.UserAuthenticationEntrypoint
import com.vspk.bookrest.security.JwtConfigurer
import com.vspk.bookrest.security.JwtTokenProvider
import java.util.Arrays
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
open class SecurityConfig(private val jwtTokenProvider: JwtTokenProvider) : WebSecurityConfigurerAdapter() {

    companion object {
        private const val ADMIN_ENDPOINT = "/api/v1/admin/**"
        private const val LOGIN_ENDPOINT = "/api/v1/auth/login"
        private const val REGISTER_ENDPOINT = "/api/v1/auth/register"
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .httpBasic().disable()
            .csrf().disable()
            .cors()
                .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeRequests()
            .antMatchers("/v2/api-docs").permitAll()
            .antMatchers("/configuration/ui").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/configuration/security").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/swagger-ui/*").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/v2/**").permitAll()
            .antMatchers("/api/v1/auth/email-verification/**").permitAll()
            .antMatchers(
                LOGIN_ENDPOINT,
                REGISTER_ENDPOINT,
                "/v2/api-docs",
                "/swagger-ui.html",
                "/v2/swagger-ui.html")
                .permitAll()
            .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
            .anyRequest().authenticated()
                .and()
            .exceptionHandling()
            .authenticationEntryPoint(userAuthenticationEntrypoint())
                .and()
            .apply(JwtConfigurer(jwtTokenProvider))
    }

    @Bean
    open fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "OPTIONS", "DELETE")
        configuration.allowedHeaders =
            listOf("Access-Control-Allow-Origin", "Content-Type", "Authorization")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    open fun userAuthenticationEntrypoint(): UserAuthenticationEntrypoint {
        return UserAuthenticationEntrypoint()
    }
}