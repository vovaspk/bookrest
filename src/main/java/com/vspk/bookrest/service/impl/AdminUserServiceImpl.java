package com.vspk.bookrest.service.impl;

import com.vspk.bookrest.domain.Status;
import com.vspk.bookrest.domain.User;
import com.vspk.bookrest.domain.Verification;
import com.vspk.bookrest.exception.UserAlreadyVerifiedException;
import com.vspk.bookrest.exception.auth.UserNotFoundException;
import com.vspk.bookrest.repository.VerificationRepository;
import com.vspk.bookrest.service.AdminUserService;
import com.vspk.bookrest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final VerificationRepository verificationRepository;
    private final UserService userService;

    @Transactional
    @Override
    public User verifyUserAccount(Long userId) {
        log.info("trying to verify user with id={}", userId);
        Optional<User> foundUser = userService.findById(userId);
        foundUser.ifPresentOrElse(this::verifyUser, () -> {
            log.warn("Cannot verify user with id={}", userId);
            throw new UserNotFoundException(userId);
        });
        return foundUser.get();
    }

    private void verifyUser(User user) {
        if(user.isVerified()){
            throw new UserAlreadyVerifiedException(user.getId(), "User with id " + user.getId() + " is already verified");
        }
        var verification = new Verification(user, "ADMIN_VERIFIED", new Date(), new Date());
        verificationRepository.save(verification);
        user.setStatus(Status.VERIFIED);
        userService.save(user);
        log.info("user with id={} has been verified by admin", user.getId());
    }
}
