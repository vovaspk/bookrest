package com.vspk.bookrest.service.impl

import com.vspk.bookrest.domain.Status
import com.vspk.bookrest.domain.User
import com.vspk.bookrest.domain.Verification
import com.vspk.bookrest.event.SendingEmailConfirmationEvent
import com.vspk.bookrest.exception.UserVerificationException
import com.vspk.bookrest.repository.VerificationRepository
import com.vspk.bookrest.service.UserService
import com.vspk.bookrest.service.UserVerificationService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Slf4j
@RequiredArgsConstructor
open class UserVerificationServiceImpl(
    private val verificationRepository: VerificationRepository,
    private val userService: UserService,
    private val applicationEventPublisher: ApplicationEventPublisher
) : UserVerificationService {

    companion object {
        private val log = LoggerFactory.getLogger(UserVerificationServiceImpl::class.java)
    }

    @Transactional
    override fun confirmAccount(code: String): String {
        val userVerification = verificationRepository.findVerificationByCode(code)
            .orElseThrow { UserVerificationException("Invalid verification link") }

        val user = userVerification.user

        validateUserAccount(userVerification, user)

        userVerification.confirmed_at = Date()

        user.status = Status.VERIFIED
        verificationRepository.save(userVerification)

        log.info("User with Id ${userVerification.user?.id} has been successfully verified")
        return "Thank you! Your account has been verified"
    }

    private fun validateUserAccount(userVerification: Verification, user: User) {
        if (accountAlreadyVerified(userVerification, user)) {
            throw UserVerificationException("Account is already Verified!")
        }
        if (verificationLinkIsExpired(userVerification)) {
            throw UserVerificationException("Confirmation Link is expired!")
        }
    }

    private fun verificationLinkIsExpired(userVerification: Verification): Boolean {
        return userVerification.expires_at.before(Date())
    }

    private fun accountAlreadyVerified(userVerification: Verification, user: User): Boolean {
        return userVerification.confirmed_at != null || user.isVerified()
    }

    override fun reSendVerificationToken(email: String): ResponseEntity<*> {
        val registeredUser = userService.findByEmail(email)
            .orElseThrow { UsernameNotFoundException("User with email: $email not found") }

        if (registeredUser.verificationTimesAsked > 3) {
            throw UserVerificationException("Email verification limit is exceeded")
        }
        applicationEventPublisher.publishEvent(SendingEmailConfirmationEvent(registeredUser))
        return ResponseEntity.status(HttpStatus.OK).build<Any>()
    }
}