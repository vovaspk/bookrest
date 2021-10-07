package com.vspk.bookrest.repository

import com.vspk.bookrest.domain.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository: JpaRepository<Review, Long> {

}