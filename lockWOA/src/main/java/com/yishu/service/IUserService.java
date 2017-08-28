package com.yishu.service;

import com.yishu.jwt.User;

public interface IUserService {
    public User findUserByName(String username);
}
