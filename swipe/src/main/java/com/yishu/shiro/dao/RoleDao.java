package com.yishu.shiro.dao;

import com.yishu.shiro.model.Resource;
import com.yishu.shiro.model.Role;
import com.yishu.shiro.model.RoleResource;
import com.yishu.shiro.model.UserRole;

import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */
public interface RoleDao {

    Integer add(Role role);
    Integer delete(Integer id);
    Integer batchDelete(List<Integer> ids);
    Integer update(Role role);
    List<Role> listRole();
    Role load(Integer id);
    UserRole loadUserRole(int userId,int roleId);

    /**
     * 为单个用户设置单个角色
     * @param userId
     * @param roleId
     * @return
     */
    Integer addUserRole(int userId,int roleId);

    /**
     * 为单个用户设置多个角色
     * @param userId
     * @param roleIds
     * @return
     */
    Integer addUserRoles(int userId,List<Integer> roleIds);

    Integer deleteUserRole(int userId,int roleId);

    /**
     * 删除某个用户的所有角色
     * @param uid
     */
    Integer deleteUserRoles(int uid);

    Integer batchDeleteRoleResource(List<Integer> roleIds);

    /**
     * 根据角色id获取可以访问的所有资源
     * @param roleId
     * @return
     */
    List<Resource> listRoleResource(int roleId);

    Integer addRoleResource(int roleId,int resourceId);

    Integer deleteRoleResource(int roleId,int resorceId);

    RoleResource loadResourceRole(int roleId,int resorceId);

    Integer deleteRoleAndUser(List<Integer> ids);


}
