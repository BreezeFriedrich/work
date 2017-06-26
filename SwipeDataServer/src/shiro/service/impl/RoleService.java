package shiro.service.impl;

import shiro.dao.RoleDao;
import shiro.model.Resource;
import shiro.model.Role;
import shiro.model.RoleResource;
import shiro.model.UserRole;
import shiro.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */
@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public Integer add(Role role) {
        return roleDao.add(role);
    }

    @Override
    public Integer delete(int id) {
        return roleDao.delete(id);
    }

    @Transactional
    @Override
    public void deleteRoleAndResource(List<Integer> ids) {
        roleDao.batchDelete(ids);
        roleDao.batchDeleteRoleResource(ids);
    }

    @Override
    public Role load(int id) {
        return roleDao.load(id);
    }

    @Override
    public List<Role> list() {
        return roleDao.listRole();
    }

    @Override
    public Integer update(Role role) {
        return roleDao.update(role);
    }

    @Override
    public List<Role> listRole() {
        return roleDao.listRole();
    }

    @Override
    public UserRole loadUserRole(int uid, int roleId) {
        return roleDao.loadUserRole(uid, roleId);
    }

    @Override
    public void addUserRole(int uid, int roleId) {
        roleDao.addUserRole(uid, roleId);
    }

    @Override
    public void deleteUserRole(int uid, int roleId) {
        roleDao.deleteUserRole(uid, roleId);
    }

    @Override
    public void deleteUserRoles(int uid) {
        roleDao.deleteUserRoles(uid);
    }

    @Override
    public List<Resource> listRoleResource(int roleId) {
        return roleDao.listRoleResource(roleId);
    }

    @Override
    public void addRoleResource(int roleId, int resId) {
        roleDao.addRoleResource(roleId, resId);
    }

    @Override
    public void deleteRoleResource(int roleId, int resId) {
        roleDao.deleteRoleResource(roleId, resId);
    }

    @Override
    public RoleResource loadResourceRole(int roleId, int resId) {
        return roleDao.loadResourceRole(roleId, resId);
    }

    @Override
    public Integer deleteRoleAndUser(List<Integer> ids) {
        return roleDao.deleteRoleAndUser(ids);
    }
}
