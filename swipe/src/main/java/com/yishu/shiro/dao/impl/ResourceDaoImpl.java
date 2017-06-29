package com.yishu.shiro.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.shiro.dao.ResourceDao;
import com.yishu.shiro.model.Resource;
import com.yishu.shiro.model.RoleResource;
import com.yishu.util.HttpUtil;
import com.yishu.util.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/6/29.
 */
public class ResourceDaoImpl implements ResourceDao {

    JsonUtil jsonUtil=new JsonUtil();
    HashMap map=new HashMap();
    String postdata="";
    String getdata="";
    ObjectMapper objectMapper=new ObjectMapper();
    Resource resource=null;
    RoleResource roleResource=null;
    List<Resource> resourceList =null;
    JsonNode dataNode=null;
    int num=0;

    @Override
    public Integer add(Resource res) {
        map.put("sign",90);
        map.put("resource",res);
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
    public Integer delete(int id) {
        map.put("sign",91);
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
    public Integer update(Resource res) {
        map.put("sign",92);
        map.put("resource",res);
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
    public List<Resource> listResource() {
        map.put("sign",93);
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
    public Resource load(int id) {
        map.put("sign",94);
        map.put("id",id);
        postdata=jsonUtil.writeObject(map);
        map.clear();
        getdata= HttpUtil.postData(postdata);
        postdata=null;
        dataNode=jsonUtil.getDataNode(getdata);
        try {
            resource=objectMapper.treeToValue(dataNode,Resource.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resource;
    }
}
