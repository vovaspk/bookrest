package com.vspk.bookrest.repository

import com.vspk.bookrest.domain.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: String): Role
}