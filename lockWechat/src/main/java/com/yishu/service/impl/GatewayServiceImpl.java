/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.service.IGatewayService;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-11 16:32 admin
 * @since JDK1.7
 */
@Service("gatewayService")
public class GatewayServiceImpl implements IGatewayService {
    org.slf4j.Logger logger= LoggerFactory.getLogger(this.getClass());

    String timetag;

    int reqSign;
    String reqData;
    /**
     * https数据请求,获取的原数据
     */
    String rawData;
    ObjectMapper objectMapper=new ObjectMapper();
    JsonNode rootNode= null;
    /**
     * https数据请求,成功(0)与失败(1)的标志
     */
    int respSign;

    private boolean respFail(){
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respSign=rootNode.path("result").asInt();
        logger.info("respSign:"+String.valueOf(respSign));
        if(0 == respSign){
            return false;
        }
        return true;
    }

    private int respSign(){
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respSign=rootNode.path("result").asInt();
        logger.info("respSign:"+String.valueOf(respSign));
        return respSign;
    }

    @Override
    public Map getGatewayIp(String ownerPhoneNumber, String gatewayCode) {
        reqSign=5;
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\"}";
        rawData= HttpUtil.doPostToQixu(reqData);
        System.err.println(rawData);

        Map resultMap=new HashMap();
        if (respFail()){
            resultMap.put("result",1);
            resultMap.put("gatewayIp","");
        }else {
            resultMap.put("result",0);
            resultMap.put("gatewayIp",rootNode.path("gatewayIp").asText());
        }

        return resultMap;
    }

    @Override
    public Map hasGatewayAdded(String ownerPhoneNumber, String gatewayCode) {
        reqSign=6;
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\"}";
        rawData= HttpUtil.doPostToQixu(reqData);
        System.err.println(rawData);

        Map resultMap=new HashMap();
        if (respFail()){
            resultMap.put("result",1);
            resultMap.put("gatewayIp","");
        }else {
            resultMap.put("result",0);
            resultMap.put("gatewayIp",rootNode.path("alreadyPhoneNumber").asText());
        }

        return resultMap;
    }

    @Override
    public int isCorrectGatewayVerificationCode(String ownerPhoneNumber, String gatewayCode, String opCode) {
        reqSign=7;
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"opCode\":\""+opCode+"\"}";
        rawData= HttpUtil.doPostToQixu(reqData);
        System.err.println(rawData);
        return respSign();
    }

    @Override
    public boolean addGateway(String ownerPhoneNumber, String gatewayCode, String gatewayName, String gatewayLocation, String gatewayComment, String opCode) {
        reqSign=8;
        timetag= String.valueOf(new Date().getTime());
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"gatewayName\":\""+gatewayName+"\",\"gatewayLocation\":\""+gatewayLocation+"\",\"gatewayComment\":\""+gatewayComment+"\",\"opCode\":\""+opCode+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.doPostToQixu(reqData);
        System.err.println(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    @Override
    public boolean modifyGatewayInfo(String ownerPhoneNumber, String gatewayCode, String gatewayName, String gatewayLocation, String gatewayComment) {
        reqSign=9;
        timetag= String.valueOf(new Date().getTime());
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"gatewayName\":\""+gatewayName+"\",\"gatewayLocation\":\""+gatewayLocation+"\",\"gatewayComment\":\""+gatewayComment+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.doPostToQixu(reqData);
        System.err.println(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteGateway(String ownerPhoneNumber, String gatewayCode) {
        reqSign=10;
        timetag= String.valueOf(new Date().getTime());
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.doPostToQixu(reqData);
        System.err.println(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }
}
