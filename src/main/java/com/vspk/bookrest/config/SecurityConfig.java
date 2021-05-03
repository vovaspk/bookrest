package com.vspk.bookrest.config;

import com.vspk.bookrest.exception.handler.UserAuthenticationEntrypoint;
import com.vspk.bookrest.security.JwtConfigurer;
import com.vspk.bookrest.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    private static final String REGISTER_ENDPOINT = "/api/v1/auth/register";
    private JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
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
                .antMatchers(LOGIN_ENDPOINT, REGISTER_ENDPOINT, "/v2/api-docs", "/swagger-ui.html", "/v2/swagger-ui.html").permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                    .and()
                .exceptionHandling()
                .authenticationEntryPoint(userAuthenticationEntrypoint())
                    .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Bean
    public UserAuthenticationEntrypoint userAuthenticationEntrypoint(){
        return new UserAuthenticationEntrypoint();
    }
}
