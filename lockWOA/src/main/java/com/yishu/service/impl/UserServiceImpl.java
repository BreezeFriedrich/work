package com.yishu.service.impl;

import com.yishu.jwt.User;
import com.yishu.service.IUserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Override
    public User findUserByName(String username) {
        return null;
    }
}
