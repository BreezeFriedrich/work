package com.yishu.service;

import com.yishu.domain.User;

public interface IUserService {
    public User findUserByName(String username);
}
