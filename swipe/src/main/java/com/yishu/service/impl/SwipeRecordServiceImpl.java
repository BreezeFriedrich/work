package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yishu.model.SwipeRecord;
import com.yishu.service.SwipeRecordService;
import com.yishu.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by admin on 2017/5/22.
 */
@Service("swipeRecordService")
public class SwipeRecordServiceImpl implements SwipeRecordService {

    private static final Logger logger= LoggerFactory.getLogger(SwipeRecordServiceImpl.class);

    String postdata=null;
    String getdata=null;
    String data=null;
    int result=0;
    Iterator it=null;
    List<SwipeRecord> swipeRecordList=null;
    int countNum=0;
    int affectedNum=0;
    ObjectMapper objectMapper=new ObjectMapper();

    public List<SwipeRecord> getDataListFromJson(String param){
        try {
//            logger.info("param:"+param);
            JsonNode node=objectMapper.readTree(param);
            result=node.get("result").asInt();
            if(0==result){
                swipeRecordList=objectMapper.readValue(String.valueOf(node.get("data")), new TypeReference<ArrayList<SwipeRecord>>() { });
            }
            return swipeRecordList;
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
    public List<SwipeRecord> listAll() {
        postdata="{\"sign\":3}";
        getdata=HttpUtil.postData(postdata);
        logger.info("#DATA     ~ "+getdata);

        swipeRecordList=getDataListFromJson(getdata);
        if(swipeRecordList.size()>0){
//            logger.info("swipeRecordList:"+String.valueOf(swipeRecordList));
            return swipeRecordList;
        }
        return null;
    }

    @Override
    public List<SwipeRecord> listByTimezoneWhenFail(String startTime, String endTime) {
        postdata="{\"sign\":13,\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
        getdata=HttpUtil.postData(postdata);
        logger.info("#DATA     ~ "+getdata);

        swipeRecordList=getDataListFromJson(getdata);
        if(swipeRecordList.size()>0){
            return swipeRecordList;
        }
        return null;
    }

    @Override
    public List<SwipeRecord> listByTimezone(String startTime, String endTime) {
        postdata="{\"sign\":4,\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
//        logger.info("#request-DATA     ~ "+postdata);
        getdata=HttpUtil.postData(postdata);
        logger.info("#DATA     ~ "+getdata);

        swipeRecordList=getDataListFromJson(getdata);
        if(swipeRecordList.size()>0){
//            logger.info("swipeRecordList:"+String.valueOf(swipeRecordList));
            return swipeRecordList;
        }
        return null;
    }

    @Override
    public int countByParam(String endTime, int result, String deviceid) {
        postdata="{\"sign\":9,\"endTime\":\""+endTime+"\",\"result\":\""+result+"\",\"deviceid\":\""+deviceid+"\"}";
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
    public int deleteByParam(String endTime, int result, String deviceid) {
        postdata="{\"sign\":10,\"endTime\":\""+endTime+"\",\"result\":\""+result+"\",\"deviceid\":\""+deviceid+"\"}";
        getdata=HttpUtil.postData(postdata);
        logger.info("#DATA     ~ "+getdata);

        affectedNum=0;
        if(null!=getdata&&""!=getdata){
            affectedNum=getDataIntFromJson(getdata);
        }
        return affectedNum;
    }
}
