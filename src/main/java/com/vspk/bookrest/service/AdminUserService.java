package com.vspk.bookrest.service;

import com.vspk.bookrest.domain.User;

public interface AdminUserService {
    User verifyUserAccount(Long userId);
}
