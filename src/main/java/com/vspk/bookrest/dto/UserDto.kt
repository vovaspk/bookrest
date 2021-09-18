package com.vspk.bookrest.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.vspk.bookrest.domain.User
import lombok.Data

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserDto(
    private val username: String,
    private val firstName: String,
    private val lastName: String,
    private val email: String
)