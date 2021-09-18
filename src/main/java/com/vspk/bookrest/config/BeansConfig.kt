package com.vspk.bookrest.config

import com.vspk.bookrest.email.EmailService
import com.vspk.bookrest.email.EmailServiceImpl
import com.vspk.bookrest.event.UserRegisteredEventListener
import com.vspk.bookrest.repository.RoleRepository
import com.vspk.bookrest.repository.UserRepository
import com.vspk.bookrest.repository.VerificationRepository
import com.vspk.bookrest.security.JwtTokenProvider
import com.vspk.bookrest.service.AdminUserService
import com.vspk.bookrest.service.UserAuthService
import com.vspk.bookrest.service.UserService
import com.vspk.bookrest.service.UserVerificationService
import com.vspk.bookrest.service.impl.AdminUserServiceImpl
import com.vspk.bookrest.service.impl.UserAuthServiceImpl
import com.vspk.bookrest.service.impl.UserServiceImpl
import com.vspk.bookrest.service.impl.UserVerificationServiceImpl
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
open class BeansConfig {
    @Bean
    open fun userService(userRepository: UserRepository): UserService {
        return UserServiceImpl(userRepository)
    }

    @Bean
    open fun authenticationService(
        authenticationManager: AuthenticationManager, jwtTokenProvider: JwtTokenProvider, userService: UserService,
        roleRepository: RoleRepository, passwordEncoder: BCryptPasswordEncoder, applicationEventPublisher: ApplicationEventPublisher
    ): UserAuthService {
        return UserAuthServiceImpl(
            authenticationManager, jwtTokenProvider, userService, roleRepository, passwordEncoder,applicationEventPublisher
        )
    }

    @Bean
    open fun userVerificationService(
        verificationRepository: VerificationRepository, userService: UserService, applicationEventPublisher: ApplicationEventPublisher
    ): UserVerificationService {
        return UserVerificationServiceImpl(verificationRepository, userService, applicationEventPublisher)
    }

    @Bean
    open fun emailService(javaMailSender: JavaMailSender): EmailService {
        return EmailServiceImpl(javaMailSender)
    }

    @Bean
    open fun userRegisteredEventListener(
        emailService: EmailService,
        verificationRepository: VerificationRepository,
        userService: UserService
    ): UserRegisteredEventListener {
        return UserRegisteredEventListener(emailService, verificationRepository, userService)
    }

    @Bean
    open fun adminUserService(verificationRepository: VerificationRepository, userService: UserService): AdminUserService {
        return AdminUserServiceImpl(verificationRepository, userService)
    }
}