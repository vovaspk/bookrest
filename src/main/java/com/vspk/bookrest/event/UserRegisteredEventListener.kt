package com.vspk.bookrest.event

import com.vspk.bookrest.domain.Verification
import com.vspk.bookrest.email.EmailService
import com.vspk.bookrest.repository.VerificationRepository
import com.vspk.bookrest.service.UserService
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.UUID
import org.springframework.context.ApplicationListener
import org.springframework.scheduling.annotation.Async

open class UserRegisteredEventListener(
    var emailService: EmailService,
    var verificationRepository: VerificationRepository,
    var userService: UserService
) : ApplicationListener<SendingEmailConfirmationEvent> {

    @Async
    @Override
    override fun onApplicationEvent(event: SendingEmailConfirmationEvent) {
        val registeredUser = event.registeredUser
        val secretToken = UUID.randomUUID().toString()
        val verificationLink =
            "https://bookrest1.herokuapp.com/api/v1/auth/email-verification/$secretToken/verify"
        val verification = Verification(registeredUser, secretToken, tomorrow(), null)

        val message =
            "<html><body><p>We've linked this email address to your bookrest account, as you asked. Your email will be used if you forgot your password and for important account message. Your email address must be verified.</p>${verificationLink}</body></html>"

        verificationRepository.save(verification)
        userService.incrementVerificationTimesAsked(registeredUser.id!!)
        //emailService.send(registeredUser.email, "BookRest Account Verification", message)

    }


    private fun tomorrow(): Date {
        //better to use LocalDateTime from java.time package, need to migrate later
        val localDateTime = LocalDateTime.now().plusDays(1)
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }
}
