package com.yishu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.AuthOrder;
import com.yishu.pojo.CardInfo;
import com.yishu.service.IOrderService;
import com.yishu.util.DateUtil;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-12 16:44 admin
 * @since JDK1.7
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService{
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    int reqSign;
    String reqData;
    String rawData;
    String timetag;

    @Override
    public List<AuthOrder> getAuthOrder(String ownerPhoneNumber, String startTime, String endTime) {
        reqSign=2002;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToQixu(reqData);
        LOG.info("rawData : "+rawData);

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode rootNode= null;
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int respSign=rootNode.path("result").asInt();
        if (0!=respSign){//请求数据失败
            return null;
        }
        JsonNode orderListNode=rootNode.path("orderList");
        List<AuthOrder> authOrderList = null;
        try {
            authOrderList=objectMapper.readValue(orderListNode.traverse(), new TypeReference<List<AuthOrder>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authOrderList;
    }

    @Override
    public boolean increaseOrder(String ownerPhoneNumber, String roomTypeId, String roomId, String startTime, String endTime, String password, List<CardInfo> cardInfoList) {
        reqSign=2001;
        timetag= DateUtil.getFormat2TimetagStr();
//        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"roomTypeId\":\""+roomTypeId+"\",\"roomId\":\""+roomId+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"password\":\""+password+"\",\"timetag\":\""+timetag+"\"}";
        Map reqMap=new HashMap(9);
        reqMap.put("sign",reqSign);
        reqMap.put("ownerPhoneNumber",ownerPhoneNumber);
        reqMap.put("roomTypeId",roomTypeId);
        reqMap.put("roomId",roomId);
        reqMap.put("startTime",startTime);
        reqMap.put("endTime",endTime);
        reqMap.put("password",password);
        reqMap.put("cardInfoList",cardInfoList);
        reqMap.put("timetag",timetag);
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            reqData=objectMapper.writeValueAsString(reqMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToQixu(reqData);
        LOG.info("rawData : "+rawData);
        JsonNode rootNode= null;
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int respSign=rootNode.path("result").asInt();
        if(0==respSign){
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyAuthOrder(String ownerPhoneNumber, String orderNumber, String password, String roomId, String startTime, String endTime, List<CardInfo> cardInfoList) {
        reqSign=2003;
        timetag= DateUtil.getFormat2TimetagStr();
        Map reqMap=new HashMap(9);
        reqMap.put("sign",reqSign);
        reqMap.put("ownerPhoneNumber",ownerPhoneNumber);
        reqMap.put("orderNumber",orderNumber);
        reqMap.put("password",password);
        reqMap.put("roomId",roomId);
        reqMap.put("startTime",startTime);
        reqMap.put("endTime",endTime);
        reqMap.put("cardInfoList",cardInfoList);
        reqMap.put("timetag",timetag);
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            reqData=objectMapper.writeValueAsString(reqMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToQixu(reqData);
        LOG.info("rawData : "+rawData);

        JsonNode rootNode= null;
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int respSign=rootNode.path("result").asInt();
        if(0==respSign){
            return true;
        }
        return false;
    }

    @Override
    public boolean cancleAuthOrder(String ownerPhoneNumber, String orderNumber) {
        reqSign=2004;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"orderNumber\":\""+orderNumber+"\"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToQixu(reqData);
        LOG.info("rawData : "+rawData);

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode rootNode= null;
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int respSign=rootNode.path("result").asInt();
        if(0==respSign){
            return true;
        }
        return false;
    }
}
