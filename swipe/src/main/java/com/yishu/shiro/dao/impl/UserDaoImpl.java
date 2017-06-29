package com.yishu.shiro.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.shiro.dao.UserDao;
import com.yishu.shiro.model.Resource;
import com.yishu.shiro.model.Role;
import com.yishu.shiro.model.User;
import com.yishu.util.HttpUtil;
import com.yishu.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/6/27.
 */
public class UserDaoImpl implements UserDao {
    private static final Logger logger= LoggerFactory.getLogger(UserDaoImpl.class);

    JsonUtil jsonUtil=new JsonUtil();
    HashMap map=new HashMap();
    String postdata="";
    String getdata="";
    ObjectMapper objectMapper=new ObjectMapper();
    int result=0;
    int num=0;
    User user=null;
    List<User> userList =null;
    List<Role> roleList =null;
    List<Resource> resourceList =null;
    List<String> stringList =null;
    String data="";
    JsonNode dataNode=null;

    public JsonNode getDataNode(String str){
        JsonNode rootNode=null;
        JsonNode resultNode=null;
        JsonNode dataNode=null;
        try {
            rootNode=objectMapper.readTree(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultNode=rootNode.path("sign");
        result=resultNode.asInt();
        dataNode=rootNode.path("data");
        User user=null;
        try {
            user=objectMapper.treeToValue(dataNode,User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return dataNode;
    }

    @Override
    public Integer add(User user) {
        map.put("sign",50);
        map.put("user",user);
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
    public Integer update(User user) {
        map.put("sign",51);
        map.put("user",user);
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
        map.put("sign",52);
        map.put("userId",id);
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
        map.put("sign",53);
        map.put("userIds",ids);
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
    public User load(Integer id) {
        map.put("sign",54);
        map.put("userId",id);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        dataNode=jsonUtil.getDataNode(getdata);
        try {
            user=objectMapper.treeToValue(dataNode,User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User loadByUserName(String username) {
        map.put("sign",55);
        map.put("username",username);
//        postdata=jsonUtil.writeTreeToString(map);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        dataNode=jsonUtil.getDataNode(getdata);
        try {
            user=objectMapper.treeToValue(dataNode,User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> listUser() {
        map.put("sign",56);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        dataNode=jsonUtil.getDataNode(getdata);
//        try {
//            userList=objectMapper.treeToValue(dataNode,List.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        try {
            userList =objectMapper.readValue(dataNode.traverse(), new TypeReference<List<User>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public List<User> listByRole(Integer rid) {
        map.put("sign",57);
        map.put("roleId",rid);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        dataNode=jsonUtil.getDataNode(getdata);
        try {
            userList =objectMapper.readValue(dataNode.traverse(), new TypeReference<List<User>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public List<Role> listUserRole(Integer uid) {
        map.put("sign",58);
        map.put("userId",uid);
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
    public List<Resource> listAllResources(Integer uid) {
        map.clear();
        postdata=null;
        map.put("sign",59);
        map.put("userId",uid);
        logger.info("map"+map.toString());
//        postdata=jsonUtil.writeTreeToString(map);
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
    public List<String> listRoleSnByUser(Integer uid) {
        map.put("sign",60);
        map.put("userId",uid);
//        postdata=jsonUtil.writeTreeToString(map);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        postdata=null;
        dataNode=jsonUtil.getDataNode(getdata);
        try {
            stringList =objectMapper.readValue(dataNode.traverse(), new TypeReference<List<String>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }
}
