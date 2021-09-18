package com.vspk.bookrest.repository

import com.vspk.bookrest.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
    fun findUserByEmail(email: String): Optional<User>

    @Modifying
    @Query(
        value = "UPDATE users set verification_asked_times = verification_asked_times + 1 where id=?",
        nativeQuery = true
    )
    fun incrementVerificationTimesAsked(userId: Long)
}