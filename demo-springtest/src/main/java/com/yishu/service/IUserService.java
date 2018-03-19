package com.yishu.service;

import com.yishu.model.User;

import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */
public interface IUserService {
    Integer add(User user);
    Integer update(User user);
    Integer deleteById(Integer id);
    Integer deleteByUserName(String username);
    Integer batchDelete(List<Integer> ids);
    User load(Integer id);
    User loadByUserName(String username);
    List<User> listUser();
}
