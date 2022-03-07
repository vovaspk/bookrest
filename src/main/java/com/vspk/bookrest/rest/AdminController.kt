package com.vspk.bookrest.rest

import com.vspk.bookrest.domain.User
import com.vspk.bookrest.service.AdminUserService
import com.vspk.bookrest.service.UserService
import org.springframework.web.bind.annotation.*
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping(value = ["/api/v1/admin/"])
@RolesAllowed("ADMIN")
@CrossOrigin(origins = ["*"])
class AdminController(private val userService: UserService,
                      private val adminUserService: AdminUserService) {

    @GetMapping(value = ["users"])
    fun getUsers(): List<User> = userService.getAll()

    @DeleteMapping(value = ["users/{id}"])
    fun getUsers(@PathVariable id: Long) = userService.delete(id)

    @PostMapping("verify/users/{id}")
    fun verify(@PathVariable id: Long): User = adminUserService.manageUserVerification(id)
}