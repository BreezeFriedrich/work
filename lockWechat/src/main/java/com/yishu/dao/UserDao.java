package com.yishu.dao;

import com.yishu.pojo.User;

public interface UserDao {
    User findUserByName(String username);
}
