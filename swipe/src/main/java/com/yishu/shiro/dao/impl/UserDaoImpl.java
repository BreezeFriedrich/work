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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/6/27.
 */
public class UserDaoImpl implements UserDao {

    JsonUtil jsonUtil=new JsonUtil();
    HashMap map=new HashMap();
    String postdata="";
    String getdata="";
    ObjectMapper objectMapper=new ObjectMapper();
    int result=0;
    int num=0;
    User user=null;
    List<User> list=null;
    String data="";
    JsonNode dataNode=null;

    public int getIntFromData(String param){
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
        map.put("User",user);
        jsonUtil.writeTreeToString(map,postdata);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        if(null!=getdata&&""!=getdata){
            num=getIntFromData(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer update(User user) {
        map.put("sign",51);
        map.put("User",user);
        jsonUtil.writeTreeToString(map,postdata);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        if(null!=getdata&&""!=getdata){
            num=getIntFromData(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer delete(Integer id) {
        map.put("sign",52);
        map.put("id",id);
        jsonUtil.writeTreeToString(map,postdata);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        if(null!=getdata&&""!=getdata){
            num=getIntFromData(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public Integer batchDelete(List<Integer> ids) {
        map.put("sign",53);
        map.put("ids",ids);
        jsonUtil.writeTreeToString(map,postdata);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        if(null!=getdata&&""!=getdata){
            num=getIntFromData(getdata);
        }
        return num>=0?num:-100;
    }

    @Override
    public User load(Integer id) {
        map.put("sign",54);
        map.put("id",id);
        jsonUtil.writeTreeToString(map,postdata);
        map.clear();
        getdata=HttpUtil.postData(postdata);
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
        jsonUtil.writeTreeToString(map,postdata);
        map.clear();
        getdata=HttpUtil.postData(postdata);
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
        jsonUtil.writeTreeToString(map,postdata);
        map.clear();
        getdata=HttpUtil.postData(postdata);
        dataNode=jsonUtil.getDataNode(getdata);
//        try {
//            list=objectMapper.treeToValue(dataNode,List.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        try {
            list=objectMapper.readValue(dataNode.traverse(), new TypeReference<List<User>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<User> listByRole(Integer rid) {
        return null;
    }

    @Override
    public List<Role> listUserRole(Integer uid) {
        return null;
    }

    @Override
    public List<Resource> listAllResources(Integer uid) {
        return null;
    }

    @Override
    public List<String> listRoleSnByUser(Integer uid) {
        return null;
    }
}
