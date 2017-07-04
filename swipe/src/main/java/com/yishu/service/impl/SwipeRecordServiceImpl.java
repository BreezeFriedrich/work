package com.yishu.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yishu.model.SwipeRecord;
import com.yishu.service.SwipeRecordService;
import com.yishu.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

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

    //Jackson
//    public List<SwipeRecord> getDataListFromJson(String param){
//        try {
//            JsonNode node=objectMapper.readTree(param);
//            result=node.get("result").asInt();
//            if(0==result){
//                long timeTag1=new Date().getTime();
//                swipeRecordList=objectMapper.readValue(String.valueOf(node.get("data")), new TypeReference<ArrayList<SwipeRecord>>() { });
//                long timeTag2=new Date().getTime();
//                logger.info("TIME{swipeRecordList=objectMapper.readValue(String.valueOf(node.get(\"data\"))}:"+(timeTag2-timeTag1));
//            }
//            return swipeRecordList;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    //fastjson
public List<SwipeRecord> getDataListFromJson(String str){
    if(null!=str&&str.contains("\"result\":")&&str.contains("\"data\":")){
//        str=str.substring(str.indexOf("{"),str.lastIndexOf("}"));
        int resultIndex=str.indexOf("\"result\":")+8;
        int dataIndex=str.indexOf("\"data\":")+6;
        if(resultIndex<dataIndex){
            result= Integer.parseInt(str.substring(resultIndex+1,str.indexOf(",")));
            if(0==result){
//                data="{"+str.substring(dataIndex+1);
                data=str.substring(dataIndex+1,str.lastIndexOf("}"));
                swipeRecordList=JSON.parseArray(data,SwipeRecord.class);
                return swipeRecordList;
            }
        }else{
            data=str.substring(dataIndex+1,str.lastIndexOf(","));
            result= Integer.parseInt(str.substring(resultIndex+1,str.lastIndexOf("}")).trim());
            if(0==result){
                swipeRecordList=JSON.parseArray(data,SwipeRecord.class);
                return swipeRecordList;
            }
        }
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
        postdata="{\"sign\":20}";
        getdata=HttpUtil.postData(postdata);

        swipeRecordList=getDataListFromJson(getdata);
        if(swipeRecordList.size()>0){
//            logger.info("swipeRecordList:"+String.valueOf(swipeRecordList));
            return swipeRecordList;
        }
        return null;
    }

    @Override
    public List<SwipeRecord> listByTimezoneWhenFail(String startTime, String endTime) {
        postdata="{\"sign\":21,\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
        getdata=HttpUtil.postData(postdata);

        swipeRecordList=getDataListFromJson(getdata);
        if(swipeRecordList.size()>0){
            return swipeRecordList;
        }
        return null;
    }

    @Override
    public List<SwipeRecord> listAllWithStrategy(HashMap paramMap) {

        paramMap.put("sign",22);
        try {
            postdata=objectMapper.writeValueAsString(paramMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        postdata="{\"sign\":14,\"orderColumn\":\"" + orderColumn + "\",\"orderDir\":\"" + orderDir + "\",\"strategy\":"+gson.toJson(strategy)+"}";
        getdata=HttpUtil.postData(postdata);

        swipeRecordList=getDataListFromJson(getdata);
        if(swipeRecordList.size()>0){
            return swipeRecordList;
        }
        return null;
    }

    @Override
    public List<SwipeRecord> listByTimezone(String startTime, String endTime) {
        postdata="{\"sign\":23,\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
        getdata=HttpUtil.postData(postdata);

        swipeRecordList=getDataListFromJson(getdata);
        if(swipeRecordList.size()>0){
//            logger.info("swipeRecordList:"+String.valueOf(swipeRecordList));
            return swipeRecordList;
        }
        return null;
    }

    @Override
    public int countByParam(String endTime, int result, String deviceid) {
        postdata="{\"sign\":24,\"endTime\":\""+endTime+"\",\"result\":\""+result+"\",\"deviceid\":\""+deviceid+"\"}";
        getdata=HttpUtil.postData(postdata);

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
        postdata="{\"sign\":25,\"endTime\":\""+endTime+"\",\"result\":\""+result+"\",\"deviceid\":\""+deviceid+"\"}";
        getdata=HttpUtil.postData(postdata);

        affectedNum=0;
        if(null!=getdata&&""!=getdata){
            affectedNum=getDataIntFromJson(getdata);
        }
        return affectedNum;
    }
}
