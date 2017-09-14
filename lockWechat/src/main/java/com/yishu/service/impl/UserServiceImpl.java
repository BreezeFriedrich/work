package com.yishu.service.impl;

import com.yishu.dao.UserDao;
import com.yishu.pojo.User;
import com.yishu.service.IUserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("UserServiceImpl");
    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByName(String username) {
        return userDao.findUserByName(username);
    }
}
