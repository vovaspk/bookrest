package com.vspk.bookrest.service.impl

import com.vspk.bookrest.domain.User
import com.vspk.bookrest.repository.UserRepository
import com.vspk.bookrest.service.UserService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import java.util.*
import javax.transaction.Transactional

@Slf4j
open class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    companion object{
        private val log = LoggerFactory.getLogger(UserServiceImpl::class.java)
    }

    override fun save(user: User): User {
        val savedUser = userRepository.save(user)
        log.info("In repository - user saved with userId: ${savedUser.id}")
        return savedUser
    }

    override fun getAll(): List<User> {
        val result = userRepository.findAll()
        log.info("IN getAll - ${result.size} users found")
        return result
    }

    override fun findByUsername(username: String): Optional<User> {
        val foundUser = userRepository.findByUsername(username)
        if (foundUser.isEmpty) {
            log.warn("user not found with username: $username")
            return Optional.empty()
        }
        log.info("IN findByUsername - user: $foundUser found by username: $username")
        return foundUser
    }

    override fun findByEmail(email: String): Optional<User> {
        val foundUser = userRepository.findUserByEmail(email)
        if (foundUser.isEmpty) {
            log.warn("user not found with email: $email")
            return Optional.empty()
        }
        log.info("IN findByEmail - user: $foundUser found with email: $email")
        return foundUser
    }

    override fun findById(id: Long): Optional<User> {
        val result = userRepository.findById(id)
        if (result.isEmpty) {
            log.warn("IN findById - user not found by id: $id")
            return Optional.empty()
        }
        log.info("IN findById - user: $result found by id: $id")
        return result
    }

    @Transactional
    override fun incrementVerificationTimesAsked(userId: Long) {
        userRepository.incrementVerificationTimesAsked(userId)
    }

    override fun delete(id: Long) {
        userRepository.deleteById(id)
        log.info("IN delete - user with id: $id successfully deleted")
    }

    override fun deleteAll() {
        userRepository.deleteAll()
        log.info("table users cleared")
    }
}