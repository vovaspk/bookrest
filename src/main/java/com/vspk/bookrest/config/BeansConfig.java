package com.vspk.bookrest.config;

import com.vspk.bookrest.repository.RoleRepository;
import com.vspk.bookrest.repository.UserRepository;
import com.vspk.bookrest.security.JwtTokenProvider;
import com.vspk.bookrest.service.UserAuthService;
import com.vspk.bookrest.service.UserService;
import com.vspk.bookrest.service.impl.UserAuthServiceImpl;
import com.vspk.bookrest.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeansConfig {
    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public UserAuthService authenticationService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService,
                                                 RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder){
        return new UserAuthServiceImpl(authenticationManager, jwtTokenProvider, userService, roleRepository, passwordEncoder);
    }
}
