package com.vspk.bookrest.service.impl

import com.vspk.bookrest.domain.Role
import com.vspk.bookrest.domain.Status
import com.vspk.bookrest.domain.User
import com.vspk.bookrest.dto.AuthUserDetailsDto
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.InjectMocks
import com.vspk.bookrest.service.impl.UserAuthServiceImpl
import org.mockito.Mock
import com.vspk.bookrest.security.JwtTokenProvider
import com.vspk.bookrest.service.UserService
import com.vspk.bookrest.repository.RoleRepository
import java.util.List
import java.util.Optional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.context.ApplicationEventPublisher
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.ArgumentMatchers
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication

@ExtendWith(MockitoExtension::class)
internal class UserAuthServiceImplUTest {
    @InjectMocks
    private var authService: UserAuthServiceImpl? = null

    @Mock
    private val authenticationManager: AuthenticationManager? = null

    @Mock
    private val jwtTokenProvider: JwtTokenProvider? = null

    @Mock
    private val userService: UserService? = null

    @Mock
    private val roleRepository: RoleRepository? = null

    @Mock
    private val passwordEncoder: BCryptPasswordEncoder? = null

    @Mock
    private val applicationEventPublisher: ApplicationEventPublisher? = null
    @BeforeEach
    fun setUp() {
        authService = UserAuthServiceImpl(
            authenticationManager!!,
            jwtTokenProvider!!,
            userService!!,
            roleRepository!!,
            passwordEncoder!!,
            applicationEventPublisher!!
        )
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun authenticate() {
        val auth = Mockito.mock(
            Authentication::class.java
        )
        auth.isAuthenticated = true
        val testUser = getTestUser()
        Mockito.`when`(authenticationManager!!.authenticate(ArgumentMatchers.any())).thenReturn(auth)
        Mockito.`when`(userService!!.findByUsername("testusername")).thenReturn(Optional.of(testUser))
        Mockito.`when`(jwtTokenProvider!!.createToken(testUser.username, testUser.roles)).thenReturn("successToken")
        val response: ResponseEntity<*> = authService!!.authenticate(authRequestDto())
        Assertions.assertTrue(response.body.toString().contains("testusername"))
        Assertions.assertTrue(response.body.toString().contains("successToken"))
        Assertions.assertTrue(response.body.toString().contains("roles"))
    }

    //
    //    @Test
    //    void authenticateUserNotFound() {
    //        Authentication auth = mock(Authentication.class);
    //        auth.setAuthenticated(false);
    //
    //        //var response = authService.authenticate(authRequestDto());
    //        assertThrows(JwtAuthenticationException.class, () -> authService.authenticate(authRequestDto()));
    ////        assertEquals(401, response.getStatusCode().value());
    ////        assertEquals("AuthFailedResponse(errorMessage=Invalid username or password)", response.getBody().toString());
    //    }
    //
    //    @Test
    //    void register() {
    //        RegisterUserDetailsDto registerDto = registerDto();
    //        Role userRole = getUserRole();
    //
    //        when(userService.findByUsername(registerDto.getUsername())).thenReturn(Optional.empty());
    //        when(userService.findByEmail(registerDto.getEmail())).thenReturn(Optional.empty());
    //        when(roleRepository.findByName("ROLE_USER")).thenReturn(userRole);
    //        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
    //        when(userService.save(any())).thenReturn(getUser());
    //
    //        var registerResponse = authService.register(registerDto);
    //
    //        assertNotNull(registerResponse.getBody());
    //        assertEquals(201, registerResponse.getStatusCode().value());
    //
    //    }
    //    private Role getUserRole() {
    //        Role userRole = new Role();
    //        userRole.setName("ROLE_USER");
    //        userRole.setId(1L);
    //        userRole.setStatus(Status.ACTIVE);
    //        userRole.setCreated(new Date());
    //        userRole.setUpdated(new Date());
    //        userRole.setUsers(List.of(getUser()));
    //        return userRole;
    //    }
    //
    //    @Test
    //    void registerUsernameIsAlreadyTaken() {
    //        RegisterUserDetailsDto registerDto = registerDto();
    //
    //        when(userService.findByUsername(registerDto.getUsername())).thenReturn(Optional.of(getUser()));
    //
    //        //var registerResponse = authService.register(registerDto);
    //        assertThrows(UserAlreadyExistsException.class, () -> authService.register(registerDto));
    //        //assertEquals(400, registerResponse.getStatusCode().value());
    //
    //    }
    //
    //    @Test
    //    void registerEmailIsAlreadyInUse() {
    //        RegisterUserDetailsDto registerDto = registerDto();
    //
    //        when(userService.findByEmail(registerDto.getEmail())).thenReturn(Optional.of(getUser()));
    //
    //
    //        //var registerResponse = authService.register(registerDto);
    //        assertThrows(UserAlreadyExistsException.class, () -> authService.register(registerDto));
    //        //assertEquals(400, registerResponse.getStatusCode().value());
    //    }
    //
    //    private RegisterUserDetailsDto registerDto() {
    //        RegisterUserDetailsDto dto = new RegisterUserDetailsDto();
    //        dto.setEmail("testemail@gmail.com");
    //        dto.setUsername("testusername");
    //        dto.setPassword("1234");
    //        dto.setMatchingPassword("1234");
    //        return dto;
    //    }
    //
        private fun authRequestDto(): AuthUserDetailsDto {
            return AuthUserDetailsDto("testusername", "1234");
    }



    private fun getTestUser() : User{
        val role = Role("ROLE_USER")
        role.status = Status.ACTIVE
        val user = User(
            "testusername",
            "testusername",
            "testname2",
            "testemail@gmail.com",
            "1234",
            listOf(role),
            0
        )
        user.status = Status.ACTIVE
        return user
    }
}