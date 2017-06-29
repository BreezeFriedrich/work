package shiro.service;

import shiro.model.Resource;
import shiro.model.Role;
import shiro.model.RoleResource;
import shiro.model.UserRole;

import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */
public interface IRoleService {
    /**
     * 添加单个角色对象
     * @param role
     */
    Integer add(Role role);

    /**
     * 根据角色 id 删除单个角色对象
     * @param id
     */
    Integer delete(int id);

    Integer batchDelete(List<Integer> ids);

    /**
     *
     * @param ids
     * @return
     */
    void deleteRoleAndResource(List<Integer> ids);

    /**
     * 根据 id 加载角色对象
     * @param id
     * @return
     */
    Role load(int id);

    /**
     *
     * @return
     */
    List<Role> list();

    /**
     * 更新单个角色对象
     * @param role
     */
    Integer update(Role role);

    /**
     *
     * @return
     */
    List<Role> listRole();

    /**
     *
     * @param uid
     * @param roleId
     * @return
     */
    UserRole loadUserRole(int uid, int roleId);

    /**
     *
     * @param uid
     * @param roleId
     */
    Integer addUserRole(int uid, int roleId);

    Integer addUserRoles(int uid, List<Integer> ids);

    /**
     *
     * @param uid
     * @param roleId
     */
    Integer deleteUserRole(int uid, int roleId);

    /**
     *
     * @param uid
     */
    Integer deleteUserRoles(int uid);
    /**
     *
     * @param roleId
     * @return
     */
    List<Resource> listRoleResource(int roleId);

    /**
     *
     * @param roleId
     * @param resId
     */
    Integer addRoleResource(int roleId, int resId);

    /**
     *
     * @param roleId
     * @param resId
     */
    Integer deleteRoleResource(int roleId, int resId);

    Integer batchDeleteRoleResource(List<Integer> ids);

    /**
     *
     * @param roleId
     * @param resId
     * @return
     */
    RoleResource loadResourceRole(int roleId, int resId);

    Integer deleteRoleAndUser(List<Integer> ids);
}
