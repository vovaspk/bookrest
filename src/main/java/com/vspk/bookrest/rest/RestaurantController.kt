package com.vspk.bookrest.rest

import com.vspk.bookrest.domain.Restaurant
import com.vspk.bookrest.dto.AuthUserDetailsDto
import com.vspk.bookrest.dto.RestaurantDto
import com.vspk.bookrest.payload.LoginResponse
import java.time.Instant
import java.util.Date
import javax.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/restaurants")
class RestaurantController() {

//    @PostMapping()
//    fun createRestaurant(restaurantDto: RestaurantDto) {
//
//    }
//
//    @GetMapping
//    fun getRestaurants(): ResponseEntity<List<Restaurant>> {
//
//    }

    //get all restaurants(screen info)
    //get all restaurants(admin web view)
    //get get one restaurant (inside restaurant screen info)
    //post update restaurant info
}