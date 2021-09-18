package com.vspk.bookrest.payload


data class AuthApiError(
    val errorMessage: String? = null,
    val validationErrors: Map<String, String>? = null
)