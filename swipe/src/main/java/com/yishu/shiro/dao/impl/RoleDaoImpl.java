package com.yishu.shiro.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
    Role role=null;
    UserRole userRole=null;
    RoleResource roleResource=null;
    List<Role> roleList =null;
    List<Resource> resourceList =null;
    JsonNode dataNode=null;
    int num=0;

    @Override
    public Integer add(Role role) {
        map.put("sign",70);
        map.put("role",role);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        if(null!=getdata&&""!=getdata){
            num=jsonUtil.getIntFromDataNode(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer delete(Integer id) {
        map.put("sign",71);
        map.put("id",id);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        if(null!=getdata&&""!=getdata){
            num=jsonUtil.getIntFromDataNode(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer batchDelete(List<Integer> ids) {
        map.put("sign",72);
        map.put("ids",ids);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        if(null!=getdata&&""!=getdata){
            num=jsonUtil.getIntFromDataNode(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer update(Role role) {
        map.put("sign",73);
        map.put("role",role);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        if(null!=getdata&&""!=getdata){
            num=jsonUtil.getIntFromDataNode(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public List<Role> listRole() {
        map.put("sign",74);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        dataNode=jsonUtil.getDataNode(getdata);
        try {
            roleList =objectMapper.readValue(dataNode.traverse(), new TypeReference<List<Role>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return roleList;
    }

    @Override
    public Role load(Integer id) {
        map.put("sign",75);
        map.put("id",id);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        dataNode=jsonUtil.getDataNode(getdata);
        try {
            role=objectMapper.treeToValue(dataNode,Role.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public UserRole loadUserRole(int userId, int roleId) {
        map.put("sign",76);
        map.put("userId",userId);
        map.put("roleId",roleId);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        dataNode=jsonUtil.getDataNode(getdata);
        try {
            userRole=objectMapper.treeToValue(dataNode,UserRole.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userRole;
    }

    @Override
    public Integer addUserRole(int userId, int roleId) {
        map.put("sign",77);
        map.put("userId",userId);
        map.put("roleId",roleId);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        if(null!=getdata&&""!=getdata){
            num=jsonUtil.getIntFromDataNode(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer addUserRoles(int userId, List<Integer> roleIds) {
        map.put("sign",78);
        map.put("userid",userId);
        map.put("roleIds",roleIds);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        if(null!=getdata&&""!=getdata){
            num=jsonUtil.getIntFromDataNode(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer deleteUserRole(int userId, int roleId) {
        map.put("sign",79);
        map.put("userId",userId);
        map.put("roleId",roleId);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        if(null!=getdata&&""!=getdata){
            num=jsonUtil.getIntFromDataNode(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer deleteUserRoles(int uid) {
        map.put("sign",80);
        map.put("userId",uid);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        if(null!=getdata&&""!=getdata){
            num=jsonUtil.getIntFromDataNode(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer batchDeleteRoleResource(List<Integer> roleIds) {
        map.put("sign",81);
        map.put("roleIds",roleIds);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        if(null!=getdata&&""!=getdata){
            num=jsonUtil.getIntFromDataNode(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public List<Resource> listRoleResource(int roleId) {
        map.put("sign",82);
        map.put("roleId",roleId);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        dataNode=jsonUtil.getDataNode(getdata);
        try {
            resourceList =objectMapper.readValue(dataNode.traverse(), new TypeReference<List<Resource>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resourceList;
    }

    @Override
    public Integer addRoleResource(int roleId, int resourceId) {
        map.put("sign",83);
        map.put("roleId",roleId);
        map.put("resourceId",resourceId);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        if(null!=getdata&&""!=getdata){
            num=jsonUtil.getIntFromDataNode(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer deleteRoleResource(int roleId, int resourceId) {
        map.put("sign",84);
        map.put("roleId",roleId);
        map.put("resourceId",resourceId);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        if(null!=getdata&&""!=getdata){
            num=jsonUtil.getIntFromDataNode(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public RoleResource loadResourceRole(int roleId, int resourceId) {
        map.put("sign",85);
        map.put("roleId",roleId);
        map.put("resourceId",resourceId);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        dataNode=jsonUtil.getDataNode(getdata);
        try {
            roleResource=objectMapper.treeToValue(dataNode,RoleResource.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return roleResource;
    }

    @Override
    public Integer deleteRoleAndUser(List<Integer> ids) {
        map.put("sign",86);
        map.put("ids",ids);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        if(null!=getdata&&""!=getdata){
            num=jsonUtil.getIntFromDataNode(getdata);
        }
        return num>=0?num:-100;
    }
}
