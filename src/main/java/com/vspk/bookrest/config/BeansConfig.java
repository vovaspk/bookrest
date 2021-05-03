package com.vspk.bookrest.config;

import com.vspk.bookrest.email.EmailService;
import com.vspk.bookrest.email.EmailServiceImpl;
import com.vspk.bookrest.event.UserRegisteredEventListener;
import com.vspk.bookrest.repository.RoleRepository;
import com.vspk.bookrest.repository.UserRepository;
import com.vspk.bookrest.repository.VerificationRepository;
import com.vspk.bookrest.security.JwtTokenProvider;
import com.vspk.bookrest.service.UserAuthService;
import com.vspk.bookrest.service.UserService;
import com.vspk.bookrest.service.UserVerificationService;
import com.vspk.bookrest.service.impl.UserAuthServiceImpl;
import com.vspk.bookrest.service.impl.UserServiceImpl;
import com.vspk.bookrest.service.impl.UserVerificationServiceImpl;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Properties;

@Configuration
public class BeansConfig {
    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public UserAuthService authenticationService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService,
                                                 RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, ApplicationEventPublisher applicationEventPublisher){
        return new UserAuthServiceImpl(authenticationManager, jwtTokenProvider, userService, roleRepository, passwordEncoder, applicationEventPublisher);
    }

    @Bean
    public UserVerificationService userVerificationService(VerificationRepository verificationRepository,
                                                               UserService userService,
                                                               ApplicationEventPublisher applicationEventPublisher){
        return new UserVerificationServiceImpl(verificationRepository, userService, applicationEventPublisher);
    }

    @Bean
    public EmailService emailService(JavaMailSender javaMailSender){
        return new EmailServiceImpl(javaMailSender);
    }

    @Bean
    public UserRegisteredEventListener userRegisteredEventListener(EmailService emailService,
            VerificationRepository verificationRepository,
            UserService userService){
        return new UserRegisteredEventListener(emailService, verificationRepository, userService);
    }

}
