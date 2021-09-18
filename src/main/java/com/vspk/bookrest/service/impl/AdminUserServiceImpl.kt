package com.vspk.bookrest.service.impl

import com.vspk.bookrest.domain.Status
import com.vspk.bookrest.domain.User
import com.vspk.bookrest.domain.Verification
import com.vspk.bookrest.exception.UserAlreadyVerifiedException
import com.vspk.bookrest.exception.auth.UserNotFoundException
import com.vspk.bookrest.repository.VerificationRepository
import com.vspk.bookrest.service.AdminUserService
import com.vspk.bookrest.service.UserService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import java.util.*
import javax.transaction.Transactional

@Slf4j
@RequiredArgsConstructor
open class AdminUserServiceImpl(
    private val verificationRepository: VerificationRepository,
    private val userService: UserService
) : AdminUserService {

    companion object {
        private val log = LoggerFactory.getLogger(AdminUserServiceImpl::class.java)
    }

    @Transactional
    override fun manageUserVerification(userId: Long): User {
        log.info("trying to verify user with id={}", userId)
        val foundUser = userService.findById(userId)
        foundUser.ifPresentOrElse({ user: User -> verifyUser(user) }) {
            log.warn("Cannot verify user with id={}", userId)
            throw UserNotFoundException(userId)
        }
        return foundUser.get()
    }

    @Transactional
    override fun verifyUser(user: User) {
        if (user.isVerified()) {
            throw UserAlreadyVerifiedException(user.id, "User with id " + user.id + " is already verified")
        }
        val verification = Verification(user, "ADMIN_VERIFIED", Date(), Date())
        verificationRepository.save(verification)
        user.status = Status.VERIFIED
        userService.save(user)
        log.info("user with id=${user.id} has been verified by admin")
    }
}