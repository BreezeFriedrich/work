package com.yishu.dao;

import com.yishu.domain.User;

public interface UserDao {
    User findUserByName(String username);
}
