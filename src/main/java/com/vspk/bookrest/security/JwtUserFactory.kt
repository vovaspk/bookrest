package com.vspk.bookrest.security

import com.vspk.bookrest.domain.Role
import com.vspk.bookrest.domain.Status
import com.vspk.bookrest.domain.User
import java.util.stream.Collectors
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

object JwtUserFactory {

    private val enabledStatuses = listOf(Status.ACTIVE, Status.VERIFIED)

    fun create(user: User): JwtUser {
        return JwtUser(
            user.id!!,
            user.username,
            user.firstName,
            user.lastName,
            user.email,
            user.password,
            mapToGrantedAuthorities(ArrayList(user.roles)),
            enabledStatuses.contains(user.status),
            user.updated!!,
            user.verificationTimesAsked
        )
    }

    private fun mapToGrantedAuthorities(userRoles: List<Role>): List<GrantedAuthority> {
        return userRoles.stream()
            .map { role: Role -> SimpleGrantedAuthority(role.name) }
            .collect(Collectors.toList())
    }
}