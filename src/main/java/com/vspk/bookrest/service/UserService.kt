package com.vspk.bookrest.service

import com.vspk.bookrest.domain.User
import java.util.*

interface UserService {
    fun save(user: User): User
    fun getAll(): List<User>
    fun findByUsername(username: String): Optional<User>
    fun findByEmail(email: String): Optional<User>
    fun findById(id: Long): Optional<User>
    fun incrementVerificationTimesAsked(userId: Long)
    fun delete(id: Long)

    //for tests purposes only
    fun deleteAll()
}