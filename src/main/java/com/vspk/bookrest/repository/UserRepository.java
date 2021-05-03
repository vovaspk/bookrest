package com.vspk.bookrest.repository;

import com.vspk.bookrest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findUserByEmail(String email);


    @Modifying
    @Query(
            value = "UPDATE users set verification_asked_times = verification_asked_times + 1 where id=?",
            nativeQuery = true)
    void incrementVerificationTimesAsked(Long userId);
}
