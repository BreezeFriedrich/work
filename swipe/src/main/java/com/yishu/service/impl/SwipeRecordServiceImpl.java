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

    @Override
    public List<SwipeRecord> listAll() {
        postdata="{\"sign\":3}";
        getdata=HttpUtil.postData(postdata);
//        logger.info("#DATA     ~ "+getdata);

        swipeRecordList=getDataListFromJson(getdata);
        if(swipeRecordList.size()>0){
//            logger.info("deviceStatusList:"+String.valueOf(swipeRecordList));
            return swipeRecordList;
        }
        return null;
    }

    @Override
    public List<SwipeRecord> listByTime(String beginTime, String endTime) {
        postdata="{\"sign\":4,\"beginTime\":\""+beginTime+"\",\"endTime\":\""+endTime+"\"}";
        logger.info("#request-DATA     ~ "+postdata);
        getdata=HttpUtil.postData(postdata);
        logger.info("#DATA     ~ "+getdata);

        swipeRecordList=getDataListFromJson(getdata);
        if(swipeRecordList.size()>0){
            logger.info("deviceStatusList:"+String.valueOf(swipeRecordList));
            return swipeRecordList;
        }
        return null;
    }
}
