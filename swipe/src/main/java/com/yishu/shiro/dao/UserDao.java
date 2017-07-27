package com.yishu.shiro.dao;

import com.yishu.shiro.model.Resource;
import com.yishu.shiro.model.Role;
import com.yishu.shiro.model.User;

import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */
public interface UserDao {

    Integer add(User user);
    Integer update(User user);
    Integer delete(Integer id);
    Integer batchDelete(List<Integer> ids);
    User load(Integer id);
    User loadByUserName(String username);
    List<User> listUser();
    List<User> listByRole(Integer rid);
    List<Role> listUserRole(Integer uid);
    List<Resource> listAllResources(Integer uid);
    List<String> listRoleSnByUser(Integer uid);
}
