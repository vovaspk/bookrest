package com.vspk.bookrest.repository

import com.vspk.bookrest.domain.RestaurantThumbnail
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantThumbnailRepository : JpaRepository<RestaurantThumbnail, Long> {
}