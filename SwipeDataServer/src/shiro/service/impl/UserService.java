package shiro.service.impl;

import shiro.dao.RoleDao;
import shiro.dao.UserDao;
import shiro.model.Resource;
import shiro.model.Role;
import shiro.model.User;
import shiro.service.IUserService;
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
    @Autowired
    private RoleDao roleDao;


    @Override
    public Integer add(User user) {
        return userDao.add(user);
    }

    @Override
    public Integer update(User user) {
        return userDao.update(user);
    }

    @Override
    public Integer delete(Integer id) {
        return userDao.delete(id);
    }

    @Override
    public Integer batchDelete(List<Integer> ids) {
        return userDao.batchDelete(ids);
    }

    @Override
    public User load(Integer id) {
        return userDao.load(id);
    }

    @Override
    public User loadByUserName(String username) {
        return userDao.loadByUserName(username);
    }

    @Override
    public List<User> listUser() {
        return userDao.listUser();
    }

    @Override
    public List<User> listByRole(Integer rid) {
        return userDao.listByRole(rid);
    }

    @Override
    public List<Role> listUserRole(Integer uid) {
        return userDao.listUserRole(uid);
    }

    @Override
    public List<Resource> listAllResources(Integer uid) {
        return userDao.listAllResources(uid);
    }

    @Override
    public List<String> listRoleSnByUser(Integer uid) {
        return userDao.listRoleSnByUser(uid);
    }
}
