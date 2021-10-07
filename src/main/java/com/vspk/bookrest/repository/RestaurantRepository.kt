package com.vspk.bookrest.repository

import com.vspk.bookrest.domain.Restaurant
import org.springframework.data.jpa.repository.JpaRepository

interface RestaurantRepository : JpaRepository<Restaurant, Long> {
}