package com.vspk.bookrest.service.impl;

import com.google.common.primitives.Ints;
import com.vspk.bookrest.domain.Status;
import com.vspk.bookrest.domain.Verification;
import com.vspk.bookrest.event.SendingEmailConfirmationEvent;
import com.vspk.bookrest.exception.UserVerificationException;
import com.vspk.bookrest.repository.VerificationRepository;
import com.vspk.bookrest.service.UserService;
import com.vspk.bookrest.service.UserVerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class UserVerificationServiceImpl implements UserVerificationService {
    private final VerificationRepository verificationRepository;
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @Override
    public String confirmAccount(String code) {
        var userVerification = verificationRepository.findVerificationByCode(code).orElseThrow(() -> new UserVerificationException("Invalid verification link"));
        var user = userVerification.getUser();
        validateUserAccount(userVerification, user);
        userVerification.setConfirmed_at(new Date());

        user.setStatus(Status.VERIFIED);
        verificationRepository.save(userVerification);

        log.info("User with Id " + userVerification.getUser().getId() + " has been successfully verified");
        return "Thank you! Your account has been verified";
    }

    private void validateUserAccount(Verification userVerification, com.vspk.bookrest.domain.User user) {
        if(accountAlreadyVerified(userVerification, user)){
            throw new UserVerificationException("Account is already Verified!");
        }

        if(verificationLinkIsExpired(userVerification)){
            throw new UserVerificationException("Confirmation Link is expired!");
        }
    }

    private boolean verificationLinkIsExpired(Verification userVerification) {
        return userVerification.getExpires_at().before(new Date());
    }

    private boolean accountAlreadyVerified(Verification userVerification, com.vspk.bookrest.domain.User user) {
        return userVerification.getConfirmed_at() != null || user.isVerified();
    }

    @Override
    public ResponseEntity<?> reSendVerificationToken(String email) {
        var registeredUser = userService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));
        if(registeredUser.getVerificationTimesAsked()>3){
            throw new UserVerificationException("Email verification limit is exceeded");
        }
        applicationEventPublisher.publishEvent(new SendingEmailConfirmationEvent(registeredUser));

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
