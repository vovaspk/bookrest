package com.vspk.bookrest.service.impl;

import com.vspk.bookrest.domain.Role;
import com.vspk.bookrest.domain.Status;
import com.vspk.bookrest.domain.User;
import com.vspk.bookrest.domain.Verification;
import com.vspk.bookrest.exception.UserVerificationException;
import com.vspk.bookrest.repository.VerificationRepository;
import com.vspk.bookrest.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserVerificationServiceImplUTest {

    @Mock
    private VerificationRepository verificationRepository;
    @Mock
    private UserService userService;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private UserVerificationServiceImpl userVerificationService;
    private static final String CODE = "random11docr1dwcod13e";

    @BeforeEach
    void setUp() {
        userVerificationService = new UserVerificationServiceImpl(verificationRepository, userService, applicationEventPublisher);
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
//    void confirmAccount() {
//        Optional<Verification> verification = Optional.of(goodVerification(Status.ACTIVE));
//        when(verificationRepository.findVerificationByCode(CODE)).thenReturn(verification);
//        when(verificationRepository.save(verification.get())).thenReturn(verification.get());
//        userVerificationService.confirmAccount(CODE);
//        assertEquals(Status.VERIFIED, verification.get().getUser().getStatus());
//    }
//
//    @Test
//    void accountAlreadyConfirmed() {
//        when(verificationRepository.findVerificationByCode(CODE)).thenReturn(Optional.of(goodVerification(Status.VERIFIED)));
//        assertThrows(UserVerificationException.class, () -> userVerificationService.confirmAccount(CODE));
//    }
//
//    @Test
//    void verificationLinkExpired() {
//        when(verificationRepository.findVerificationByCode(CODE)).thenReturn(Optional.of(expiredVerification(Status.ACTIVE)));
//        assertThrows(UserVerificationException.class, () -> userVerificationService.confirmAccount(CODE));
//    }
//
//    @Test
//    void reSendVerificationToken() {
//    }
//
//    @Test
//    void resendVerificationLinkLimitExceeded() {
//        when(userService.findByEmail("testemail@gmail.com")).thenReturn(Optional.of(user(Status.ACTIVE, 4)));
//        assertThrows(UserVerificationException.class, () -> userVerificationService.reSendVerificationToken("testemail@gmail.com"));
//    }
//
//    private Verification expiredVerification(Status userStatus){
//        return new Verification(user(userStatus,1), CODE, yesterday(), null);
//    }
//
//    private Verification goodVerification(Status userStatus){
//        return new Verification(user(userStatus,1), CODE, tomorrow(), null);
//    }
//
//    private User user(Status userStatus, int verificationTimesAsked) {
//        var role = new Role();
//        role.setId(1L);
//        role.setName("ROLE_USER");
//        role.setStatus(Status.ACTIVE);
//
//        User user = new User();
//        user.setEmail("testemail@gmail.com");
//        user.setUsername("testusername");
//        user.setFirstName("testusername");
//        user.setLastName("testname2");
//        user.setPassword("1234");
//        user.setRoles(List.of(role));
//        user.setStatus(userStatus);
//        user.setVerificationTimesAsked(verificationTimesAsked);
//        return user;
//    }
//
//    private Date tomorrow(){
//        var localDateTime = LocalDateTime.now().plusDays(1);
//        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
//    }
//
//    private Date yesterday(){
//        var localDateTime = LocalDateTime.now().minusDays(1);
//        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
//    }
}