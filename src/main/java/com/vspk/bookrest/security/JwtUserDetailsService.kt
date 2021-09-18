package com.vspk.bookrest.security

import com.vspk.bookrest.service.UserService
import com.vspk.bookrest.service.impl.AdminUserServiceImpl
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@Slf4j
class JwtUserDetailsService @Autowired constructor(private val userService: UserService) : UserDetailsService {

    companion object {
        private val log = LoggerFactory.getLogger(JwtUserDetailsService::class.java)
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.findByUsername(username)
        if (user.isEmpty) {
            throw UsernameNotFoundException("User with username: $username not found")
        }
        val jwtUser = JwtUserFactory.create(user.get())
        log.info("IN loadUserByUserName - user with username: {} successfully loaded", username)
        return jwtUser
    }
}