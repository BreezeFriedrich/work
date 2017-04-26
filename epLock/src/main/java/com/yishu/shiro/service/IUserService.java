package com.yishu.shiro.service;

import com.yishu.shiro.model.Resource;
import com.yishu.shiro.model.Role;
import com.yishu.shiro.model.User;

import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */
public interface IUserService {
    /**
     * 添加单个用户
     * @param user
     */
    User add(User user);

    /**
     *add user-role relation data
     * @param user
     * @param rids
     */
    User add(User user,List<Integer> rids);

    /**
     * 根据 user_id 删除用户数据
     * @param id
     */
    void delete(int id);

    /**
     * 删除用户和用户绑定的角色信息
     * @param ids
     */
    void deleteUserAndRole(List<Integer> ids);

    /**
     *made a transaction
     * 更新用户数据
     * 1、update user base data
     * 2、update user's role
     *    （1）delete all roles
     *    （2）add binded roles
     * @param user
     * @param rids
     */
    User update(User user,List<Integer> rids);

    /**
     * 更新单个用户信息
     * @param user
     * @return
     */
    User update(User user);

    /**
     * 根据主键 id 加载用户对象
     * @param id
     * @return
     */
    User load(int id);

    /**
     * load user obj by username while login
     * @param username
     * @return
     */
    User loadByUsername(String username);

    /**
     * 登录逻辑
     * 1、query user obj by username
     * 2、如果有用户对象，则继续匹配密码
     * 如果没有用户对象，则抛出异常
     * @param username
     * @param password
     * @return
     */
    User login(String username,String password);

    /**
     * query user objs tables
     * @return
     */
    List<User> list();

    /**
     *query the role's all users by the role's id
     * @param id
     * @return
     */
    List<User> listByRole(int id);

    /**
     *query authorization by the role's id
     * @param uid
     * @return
     */
    List<Resource> listAllResource(int uid);

    /**
     *query roles's string tables relate to the user
     * @param uid
     * @return
     */
    List<String> listRoleSnByUser(int uid);

    /**
     * query role table binded with the user
     * @param uid
     * @return
     */
    List<Role> listUserRole(int uid);
}
