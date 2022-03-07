package com.vspk.bookrest.service

import com.vspk.bookrest.domain.Restaurant
import java.util.Optional

interface RestaurantService {
    fun save(restaurant: Restaurant): Restaurant
    fun getAll(): List<Restaurant>
    fun findById(id: Long): Optional<Restaurant>
    fun delete(id: Long)
}