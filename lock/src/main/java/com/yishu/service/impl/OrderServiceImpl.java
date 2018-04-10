package com.yishu.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yishu.pojo.AuthOrder;
import com.yishu.pojo.CardInfo;
import com.yishu.service.IOrderService;
import com.yishu.util.DateUtil;
import com.yishu.util.FilterList;
import com.yishu.util.FilterListHook;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-12 16:44 admin
 * @since JDK1.7
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService{
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("OrderServiceImpl");

    int reqSign;
    String reqData;
    String rawData;
    String timetag;

    @Override
    public List<AuthOrder> getAuthOrderFromDate(String ownerPhoneNumber, Date theDate) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(theDate);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date startDate=calendar.getTime();
        final long startMoment=startDate.getTime();
        calendar.setTime(theDate);
        calendar.add(Calendar.DAY_OF_MONTH,15);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        Date endDate=calendar.getTime();
        final long endMoment=endDate.getTime();
        return getAuthOrder(ownerPhoneNumber,startMoment,endMoment);
    }

    @Override
    public List<AuthOrder> getAuthOrder(String ownerPhoneNumber, final long startTime, long endTime) {
        reqSign=2002;
        timetag= DateUtil.getFormat2TimetagStr();
        String startTimeStr=DateUtil.yyyyMMddHHmm.format(new Date(startTime));
        String endTimeStr=DateUtil.yyyyMMddHHmm.format(new Date(endTime));
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTimeStr+"\",\"endTime\":\""+endTimeStr+"\",\"timetag\":\""+timetag+"\"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToGateway(reqData);
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

        //filter-recordList-Bytime,过滤掉过时的开锁授权.
        List<AuthOrder> authOrderListNext = null;
        authOrderListNext = FilterList.filter(authOrderList, new FilterListHook<AuthOrder>() {
            @Override
            public boolean test(AuthOrder authOrder) {
                try {
                    long endTime = DateUtil.yyyyMMddHHmm.parse(authOrder.getEndTime()).getTime();
                    if(new Date().getTime() < endTime){
                        authOrder.setEndTime(String.valueOf(endTime));
                        long startTime = DateUtil.yyyyMMddHHmm.parse(authOrder.getStartTime()).getTime();
                        authOrder.setStartTime(String.valueOf(startTime));
                        return true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        return authOrderListNext;
    }

    @Override
    public boolean increaseOrder(String ownerPhoneNumber, String roomTypeId, String roomId, long startTime, long endTime, String password, List<CardInfo> cardInfoList) {
        reqSign=2001;
        timetag= DateUtil.getFormat2TimetagStr();
        String startTimeStr=DateUtil.yyyyMMddHHmm.format(new Date(startTime));
        String endTimeStr=DateUtil.yyyyMMddHHmm.format(new Date(endTime));
        Map reqMap=new HashMap(9);
        reqMap.put("sign",reqSign);
        reqMap.put("ownerPhoneNumber",ownerPhoneNumber);
        reqMap.put("roomTypeId",roomTypeId);
        reqMap.put("roomId",roomId);
        reqMap.put("startTime",startTimeStr);
        reqMap.put("endTime",endTimeStr);
        reqMap.put("password",password);
        reqMap.put("cardInfoList",cardInfoList);
        reqMap.put("timetag",timetag);
        ObjectMapper objectMapper=new ObjectMapper();
//        try {
//            reqData=objectMapper.writeValueAsString(reqMap);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        Gson gson=new Gson();
        reqData=gson.toJson(reqMap);
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToGateway(reqData);
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
    public boolean modifyAuthOrder(String ownerPhoneNumber, String orderNumber, String password, String roomId, long startTime, long endTime, List<CardInfo> cardInfoList) {
        reqSign=2003;
        timetag= DateUtil.getFormat2TimetagStr();
        String startTimeStr=DateUtil.yyyyMMddHHmm.format(new Date(startTime));
        String endTimeStr=DateUtil.yyyyMMddHHmm.format(new Date(endTime));
        Map reqMap=new HashMap(9);
        reqMap.put("sign",reqSign);
        reqMap.put("ownerPhoneNumber",ownerPhoneNumber);
        reqMap.put("orderNumber",orderNumber);
        reqMap.put("password",password);
        reqMap.put("roomId",roomId);
        reqMap.put("startTime",startTimeStr);
        reqMap.put("endTime",endTimeStr);
        reqMap.put("cardInfoList",cardInfoList);
        reqMap.put("timetag",timetag);
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            reqData=objectMapper.writeValueAsString(reqMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToGateway(reqData);
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
        rawData= HttpUtil.httpsPostToGateway(reqData);
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
