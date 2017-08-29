package com.yishu.dao;

import com.yishu.jwt.User;

public interface UserDao {
    User findUserByName(String username);
}
