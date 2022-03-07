package com.vspk.bookrest.dto

data class RestaurantDto(
    val name: String,
    val phone: String,
    val thumbnailPath: String,//or id //retrieve saved thumbnail from db
    val address: String,//normalize later
    val cuisineType: String,
    val scheduleId: List<String>//each string is day of week

)
