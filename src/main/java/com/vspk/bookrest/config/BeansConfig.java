package com.vspk.bookrest.config;

import com.vspk.bookrest.repository.RoleRepository;
import com.vspk.bookrest.repository.UserRepository;
import com.vspk.bookrest.service.UserService;
import com.vspk.bookrest.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeansConfig {
    @Bean
    public UserService userService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        return new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }
}
