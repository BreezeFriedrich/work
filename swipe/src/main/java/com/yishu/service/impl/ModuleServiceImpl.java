package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        postdata="{\"sign\":1,\"status\":0}";
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

        postdata="{\"sign\":2}";
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
        postdata="{\"sign\":5}";
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

//    @Override
//    public void discardDuplicate() {
//        deviceStatusDao.discardDuplicate();
//    }
}
