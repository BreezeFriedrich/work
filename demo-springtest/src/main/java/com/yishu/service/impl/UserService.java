package com.yishu.service.impl;

import com.yishu.dao.UserDao;
import com.yishu.model.User;
import com.yishu.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */
@Service
public class UserService implements IUserService {
    private static final Logger logger= LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDao userDao;

    public Integer add(User user) {
        return userDao.add(user);
    }

    public Integer update(User user) {
        return userDao.update(user);
    }

    public Integer deleteById(Integer id) {
        return userDao.deleteById(id);
    }

    @Override
    public Integer deleteByUserName(String username) {
        return userDao.deleteByUserName(username);
    }

    public Integer batchDelete(List<Integer> ids) {
        return userDao.batchDelete(ids);
    }

    public User load(Integer id) {
        return userDao.load(id);
    }

    public User loadByUserName(String username) {
        return userDao.loadByUserName(username);
    }

    public List<User> listUser() {
        return userDao.listUser();
    }
}
