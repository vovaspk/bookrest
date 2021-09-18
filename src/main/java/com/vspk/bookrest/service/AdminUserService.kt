package com.vspk.bookrest.service

import com.vspk.bookrest.domain.User

interface AdminUserService {
    fun manageUserVerification(userId: Long): User
    fun verifyUser(user: User)
}