package com.vspk.bookrest.repository;

import com.vspk.bookrest.domain.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Optional<Verification> findVerificationByCode(String code);
}
