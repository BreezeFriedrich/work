package com.yishu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yishu.model.DeviceStatus;
import com.yishu.service.ModuleService;
import com.yishu.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by admin on 2017/5/10.
 */
@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

    private static final Logger logger= LoggerFactory.getLogger(ModuleServiceImpl.class);

    String postdata=null;
    String getdata=null;
    String data=null;
    int result=0;
    Iterator it=null;
    List<DeviceStatus> deviceStatusList=null;
    int countNum=0;
    int affectedNum=0;
    ObjectMapper objectMapper=new ObjectMapper();

//    @Override
//    public void deleteByTime(String endTime) {
//        deviceStatusDao.deleteByTime(endTime);
//    }
//
//    @Override
//    public void deleteByDeviceid(String deviceid) {
//        deviceStatusDao.deleteByDeiviceid(deviceid);
//    }
//
//    @Override
//    public List<DeviceStatus> listByDeviceid(String deviceid) {
//        return deviceStatusDao.listByDeviceid(deviceid);
//    }

    public List<DeviceStatus> getDataListFromJson(String param){
        try {
            JsonNode node=objectMapper.readTree(param);
            result=node.get("result").asInt();
            if(0==result){
                deviceStatusList=objectMapper.readValue(String.valueOf(node.get("data")), new TypeReference<ArrayList<DeviceStatus>>() { });
            }
            return deviceStatusList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getDataIntFromJson(String param){
        try {
            JsonNode node=objectMapper.readTree(param);
            result=node.get("result").asInt();
            if(0==result){
                countNum=node.get("data").asInt();
            }
            return countNum;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -100;
    }

    @Override
    public List<DeviceStatus> listByStatus(int status) {
//        ObjectMapper mapper=new ObjectMapper();
//        String senddata=" {\"sign\":\""+1+"\",\"status\":\""+0+"\"}";
//        Date time1=new Date();
//        for(int i=0;i<10;i++) {
//            String data = HttpUtil.postData(senddata);
//            logger.info("#DATA     ~ " + data);
//        }
//        Date time2=new Date();
//        int diff= (int) (time2.getTime()-time1.getTime())/10;
//        logger.info("DIFF -Time:"+diff);

        postdata="{\"sign\":0,\"status\":0}";
        getdata=HttpUtil.postData(postdata);
        logger.info("#DATA     ~ "+getdata);

        deviceStatusList=getDataListFromJson(getdata);
        if(deviceStatusList.size()>0){
            return deviceStatusList;
        }
        return null;
    }

    @Override
    public List<DeviceStatus> listAllWithoutDuplicate() {

        postdata="{\"sign\":1}";
        getdata=HttpUtil.postData(postdata);
        logger.info("#DATA     ~ "+getdata);

        deviceStatusList=getDataListFromJson(getdata);
        if(deviceStatusList.size()>0){
            logger.info("deviceStatusList:"+String.valueOf(deviceStatusList));
            return deviceStatusList;
        }
        return null;
    }

    @Override
    public List<DeviceStatus> listAll() {
        postdata="{\"sign\":2}";
        getdata=HttpUtil.postData(postdata);
        logger.info("#DATA     ~ "+getdata);

        if(null!=getdata&&""!=getdata){
            deviceStatusList=getDataListFromJson(getdata);
        }
        if(deviceStatusList.size()>0){
//            logger.info("deviceStatusList:"+String.valueOf(deviceStatusList));
            return deviceStatusList;
        }
        return null;
    }

    @Override
    public List<DeviceStatus> listAllWithStrategy(HashMap paramMap) {

        Gson gson=new Gson();
        paramMap.put("sign",3);
        try {
            postdata=objectMapper.writeValueAsString(paramMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        logger.info("postdata:"+postdata);
        getdata=HttpUtil.postData(postdata);
//        logger.info("#DATA     ~ "+getdata);

        deviceStatusList=getDataListFromJson(getdata);
        if(deviceStatusList.size()>0){
            return deviceStatusList;
        }
        return null;
    }

    @Override
    public List<DeviceStatus> listByTimezone(String startTime, String endTime) {
        postdata="{\"sign\":4,\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
//        logger.info("#request-DATA     ~ "+postdata);
        getdata=HttpUtil.postData(postdata);
        logger.info("#DATA     ~ "+getdata);

        deviceStatusList=getDataListFromJson(getdata);
        if(deviceStatusList.size()>0){
//            logger.info("deviceStatusList:"+String.valueOf(deviceStatusList));
            return deviceStatusList;
        }
        return null;
    }

    @Override
    public List<DeviceStatus> listByParam(String endTime, int status, String deviceid) {
        postdata="{\"sign\":5,\"endTime\":\""+endTime+"\",\"status\":\""+status+"\",\"deviceid\":\""+deviceid+"\"}";
        getdata=HttpUtil.postData(postdata);
        logger.info("#DATA     ~ "+getdata);

        if(null!=getdata&&""!=getdata){
            deviceStatusList=getDataListFromJson(getdata);
        }
        if(deviceStatusList.size()>0){
//            logger.info("deviceStatusList:"+String.valueOf(deviceStatusList));
            return deviceStatusList;
        }
        return null;
    }

    @Override
    public int countByParam(String endTime, int status, String deviceid) {
        postdata="{\"sign\":6,\"endTime\":\""+endTime+"\",\"status\":\""+status+"\",\"deviceid\":\""+deviceid+"\"}";
        getdata=HttpUtil.postData(postdata);
        logger.info("#DATA     ~ "+getdata);

        if(null!=getdata&&""!=getdata){
            countNum=getDataIntFromJson(getdata);
        }
        if(countNum>=0){
//            logger.info("deviceStatusList:"+String.valueOf(deviceStatusList));
            return countNum;
        }
        return -100;
    }

    @Override
    public int deleteByParam(String endTime, int status, String deviceid) {
        postdata="{\"sign\":7,\"endTime\":\""+endTime+"\",\"status\":\""+status+"\",\"deviceid\":\""+deviceid+"\"}";
        getdata=HttpUtil.postData(postdata);
        logger.info("#DATA     ~ "+getdata);

        affectedNum=0;
        if(null!=getdata&&""!=getdata){
            affectedNum=getDataIntFromJson(getdata);
        }
        return affectedNum;
    }

//    @Override
//    public void discardDuplicate() {
//        deviceStatusDao.discardDuplicate();
//    }
}
