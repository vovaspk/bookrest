package com.vspk.bookrest.rest;

import com.vspk.bookrest.domain.User;
import com.vspk.bookrest.service.AdminUserService;
import com.vspk.bookrest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/admin/")
@RolesAllowed("ADMIN")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminRestController {

    private final UserService userService;
    private final AdminUserService adminUserService;


    @GetMapping(value = "users")
    public List<User> getUsers(){
        return userService.getAll();
    }

    @DeleteMapping(value = "users/{id}")
    public void getUsers(@PathVariable Long id) {
        userService.delete(id);
    }

    @PostMapping("verify/users/{id}")
    public User verify(@PathVariable Long id){
        return adminUserService.verifyUserAccount(id);
    }
}
