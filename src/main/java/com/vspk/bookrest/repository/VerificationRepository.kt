package com.vspk.bookrest.repository

import com.vspk.bookrest.domain.Verification
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VerificationRepository : JpaRepository<Verification, Long> {
    fun findVerificationByCode(code: String): Optional<Verification>
}