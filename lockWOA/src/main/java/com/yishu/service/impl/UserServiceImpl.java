package com.yishu.service.impl;

import com.yishu.dao.UserDao;
import com.yishu.jwt.User;
import com.yishu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    UserDao userDao;

    @Override
    public User findUserByName(String username) {
        return userDao.findUserByName(username);
    }
}
