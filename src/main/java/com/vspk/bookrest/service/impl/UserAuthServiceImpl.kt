package com.vspk.bookrest.service.impl

import com.vspk.bookrest.domain.Role
import com.vspk.bookrest.domain.Status
import com.vspk.bookrest.domain.User
import com.vspk.bookrest.dto.AuthUserDetailsDto
import com.vspk.bookrest.dto.RegisterUserDetailsDto
import com.vspk.bookrest.event.SendingEmailConfirmationEvent
import com.vspk.bookrest.exception.auth.JwtAuthenticationException
import com.vspk.bookrest.exception.auth.UserAlreadyExistsException
import com.vspk.bookrest.payload.LoginResponse
import com.vspk.bookrest.payload.RegistrationResponse
import com.vspk.bookrest.repository.RoleRepository
import com.vspk.bookrest.security.JwtTokenProvider
import com.vspk.bookrest.service.UserAuthService
import com.vspk.bookrest.service.UserService
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Slf4j
class UserAuthServiceImpl(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userService: UserService,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val applicationEventPublisher: ApplicationEventPublisher
) : UserAuthService {

    companion object{
        private val log = LoggerFactory.getLogger(UserAuthServiceImpl::class.java)
    }


    override fun authenticate(requestDto: AuthUserDetailsDto): ResponseEntity<LoginResponse> {
        try {
            val username = requestDto.username
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, requestDto.password))
            val user = userService.findByUsername(username)
                .orElseThrow { UsernameNotFoundException("User with username: $username not found") }

            val token = jwtTokenProvider.createToken(username, user.roles)
            //TODO return object and them map to response object in controller/ each object for each layer
            return ResponseEntity.ok().body(LoginResponse(username=username, token=token, roles=user.roles, verificationStatus=user.status.toString()))

        } catch (e: AuthenticationException) {
            throw JwtAuthenticationException("Invalid username or password", HttpStatus.UNAUTHORIZED)
        }
    }

    override fun register(dto: RegisterUserDetailsDto): ResponseEntity<RegistrationResponse> {
        validate(dto)
        val userRole = roleRepository.findByName("ROLE_USER")
        val userRoles: MutableList<Role> = ArrayList()
        userRoles.add(userRole)
        val newUser = User(
            username = dto.username,
            email = dto.email,
            password = passwordEncoder.encode(dto.password),
            roles = userRoles,
            firstName = null,
            lastName = null,
            verificationTimesAsked = 1,
        likedRestaurants = emptyList())
        newUser.status = Status.ACTIVE
        val registeredUser = userService.save(newUser)
        log.info("user successfully registered: {} ", registeredUser)
        applicationEventPublisher.publishEvent(SendingEmailConfirmationEvent(registeredUser))
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(RegistrationResponse(registeredUser))//TODO check that response returns correct json
    }

    //BasicValidator<T> , then create validator with class that's need to be validated with custom validation, if validation grows up ..
    private fun validate(dto: RegisterUserDetailsDto) {
        val username = dto.username
        val email = dto.email
        if (existsByUsername(username)) {
            throw UserAlreadyExistsException(
                username,
                "Failed to register user, username [$username] is already taken!"
            )
        }
        if(dto.password != dto.matchingPassword){
            throw IllegalArgumentException("password must match second password")
        }
        if (existsByEmail(email)) {
            throw UserAlreadyExistsException(email, "Failed to register user, email [$email] is already in use!")
        }
    }

    private fun existsByUsername(username: String): Boolean {
        return userService.findByUsername(username).isPresent
    }

    private fun existsByEmail(email: String): Boolean {
        return userService.findByEmail(email).isPresent
    }
}