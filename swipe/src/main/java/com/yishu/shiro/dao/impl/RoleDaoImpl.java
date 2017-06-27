package com.yishu.shiro.dao.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.shiro.dao.RoleDao;
import com.yishu.shiro.model.Resource;
import com.yishu.shiro.model.Role;
import com.yishu.shiro.model.RoleResource;
import com.yishu.shiro.model.UserRole;
import com.yishu.util.HttpUtil;
import com.yishu.util.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/6/27.
 */
public class RoleDaoImpl implements RoleDao {

    JsonUtil jsonUtil=new JsonUtil();
    HashMap map=new HashMap();
    String postdata="";
    String getdata="";
    ObjectMapper objectMapper=new ObjectMapper();
    int result=0;
    int num=0;

    public int getDataIntFromJson(String param){
        try {
            JsonNode node=objectMapper.readTree(param);
            result=node.get("result").asInt();
            if(0==result){
                num=node.get("data").asInt();
            }
            return num;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -100;
    }

    @Override
    public Integer add(Role role) {
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        return null;
    }

    @Override
    public Integer batchDelete(List<Integer> ids) {
        return null;
    }

    @Override
    public Integer update(Role role) {
        return null;
    }

    @Override
    public List<Role> listRole() {
        return null;
    }

    @Override
    public Role load(Integer id) {
        return null;
    }

    @Override
    public UserRole loadUserRole(int userId, int roleId) {
        return null;
    }

    @Override
    public Integer addUserRole(int userId, int roleId) {
        return null;
    }

    @Override
    public Integer addUserRoles(int userId, List<Integer> roleIds) {
        map.put("sign",51);
        map.put("userid",userId);
        map.put("roleIds",roleIds);
        jsonUtil.writeTreeToString(map,postdata);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        if(null!=getdata&&""!=getdata){
            num=getDataIntFromJson(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer deleteUserRole(int userId, int roleId) {
        return null;
    }

    @Override
    public Integer deleteUserRoles(int uid) {
        map.put("sign",50);
        map.put("userIds",uid);
        jsonUtil.writeTreeToString(map,postdata);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        if(null!=getdata&&""!=getdata){
            num=getDataIntFromJson(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer batchDeleteRoleResource(List<Integer> roleIds) {
        return null;
    }

    @Override
    public List<Resource> listRoleResource(int roleId) {
        return null;
    }

    @Override
    public Integer addRoleResource(int roleId, int resourceId) {
        return null;
    }

    @Override
    public Integer deleteRoleResource(int roleId, int resorceId) {
        return null;
    }

    @Override
    public RoleResource loadResourceRole(int roleId, int resorceId) {
        return null;
    }

    @Override
    public Integer deleteRoleAndUser(List<Integer> ids) {
        return null;
    }
}
