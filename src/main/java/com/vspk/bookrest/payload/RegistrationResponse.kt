package com.vspk.bookrest.payload

import com.vspk.bookrest.domain.User

data class RegistrationResponse(val registeredUser: User){
    override fun toString(): String {
        return "RegistrationResponse(registeredUser=$registeredUser)"
    }
}