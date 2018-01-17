/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.service.IGatewayService;
import com.yishu.util.DateUtil;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-11 16:32 admin
 * @since JDK1.7
 */
@Service("gatewayService")
public class GatewayServiceImpl implements IGatewayService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("GatewayServiceImpl");

    String gatewayIp=null;
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

    /**
     * 获取 当前帐号ownerPhoneNumber指定网关gatewayCode的数据服务器ip
     * 添加网关前，先获取网关所在服务器的IP (lock.qixutech.com).进行网关、门锁、开锁相关动作都需要gatewayIp.
     *
     * @return //( type=Map structure: { "result" : int(0成功,1失败) , "gatewayIp" : String(网关数据所在IP) } )
     */
    @Override
    public String getGatewayIp(String ownerPhoneNumber, String gatewayCode) {
        reqSign=5;
        logger.info("sign:"+reqSign+" operation:getGatewayIp");
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\"}";
        rawData= HttpUtil.httpsPostToQixu(reqData);
//        logger.info(rawData);

        /*@deprecated: getGatewayIp()返回值 type=Map structure: { "result" : int(0成功,1失败) , "gatewayIp" : String(网关数据所在IP) }.
        Map resultMap=new HashMap();
        if (respFail()){
            resultMap.put("result",1);
            resultMap.put("gatewayIp","");
        }else {
            resultMap.put("result",0);
            resultMap.put("gatewayIp",rootNode.path("gatewayIp").asText());
        }
        return resultMap;
        */

        if (respFail()){
            logger.info("获取gatewayIp失败");
            return null;
        }
        String gatewayIp = rootNode.path("gatewayIp").asText();
        logger.info("获取的gatewayIp:"+gatewayIp);
        return gatewayIp;
    }

    @Override
    public Map hasGatewayAdded(String ownerPhoneNumber, String gatewayCode) {

        /* !!弃用httpsPostToIp(gatewayIp,reqData).逻辑错误:向还未存在的gatewayIp发送请求.
        Map resultMap=new HashMap();
        gatewayIp = getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            // 此处不能return null.因为未找到gatewayCode的gatewayIp,说明此网关gatewayCode未被添加过.
            resultMap.put("result",0);
            resultMap.put("alreadyPhoneNumber","");
        }
        reqData=null;
        reqSign=6;
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        */

        reqSign=6;
        logger.info("sign:"+reqSign+" operation:hasGatewayAdded");
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\"}";
        Map resultMap=new HashMap();
        rawData = HttpUtil.httpsPostToQixu(reqData);
        logger.info(rawData);
//        if ("".equals(rawData)) {
//            return null;
//        }

        respSign();
        resultMap.put("result",respSign);
        if (0==respSign){
            resultMap.put("alreadyPhoneNumber","");
        }
        if (1==respSign){
            resultMap.put("alreadyPhoneNumber",rootNode.path("alreadyPhoneNumber").asText());
        }

        logger.info("resultMap:"+resultMap);
        return resultMap;
    }

    @Override
    public Map getGatewayLANIp(String ownerPhoneNumber, String gatewayCode) {
        reqSign=30;
        logger.info("sign:"+reqSign+" operation:getGatewayLANIp");
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\"}";
        Map resultMap=new HashMap();
        rawData = HttpUtil.httpsPostToQixu(reqData);
        logger.info(rawData);
//        if ("".equals(rawData)) {
//            return null;
//        }

        respSign();
        resultMap.put("result",respSign);
        if (0==respSign){
            resultMap.put("ip",rootNode.path("ip").asText());
            resultMap.put("alreadyPhoneNumber","");
        }
        if (1==respSign){
            resultMap.put("ip","");
            resultMap.put("alreadyPhoneNumber",rootNode.path("alreadyPhoneNumber").asText());
        }

        return resultMap;
    }

    @Override
    public int isCorrectGatewayVerificationCode(String ownerPhoneNumber, String gatewayCode, String opCode) {
        gatewayIp = getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return -1;
        }
        reqData=null;

        reqSign=7;
        logger.info("sign:"+reqSign+" operation:isCorrectGatewayVerificationCode");
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"opCode\":\""+opCode+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"opCode\":\""+opCode+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        logger.info(rawData);
        return respSign();
    }

    @Override
    public boolean registerGatewayInfo(String ownerPhoneNumber, String gatewayCode, String gatewayName, String gatewayLocation, String gatewayComment, String opCode) {
        gatewayIp = getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return false;
        }
        reqData=null;

        reqSign=8;
        logger.info("sign:"+reqSign+" operation:registerGatewayInfo");
        timetag= DateUtil.getFormat2TimetagStr();
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"gatewayName\":\""+gatewayName+"\",\"gatewayLocation\":\""+gatewayLocation+"\",\"gatewayComment\":\""+gatewayComment+"\",\"opCode\":\""+opCode+"\",\"timetag\":\""+timetag+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"gatewayName\":\""+gatewayName+"\",\"gatewayLocation\":\""+gatewayLocation+"\",\"gatewayComment\":\""+gatewayComment+"\",\"opCode\":\""+opCode+"\",\"timetag\":\""+timetag+"\"}";
        logger.info("reqData:"+reqData);
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        logger.info(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    @Override
    public boolean modifyGatewayInfo(String ownerPhoneNumber, String gatewayCode, String gatewayName, String gatewayLocation, String gatewayComment) {
        gatewayIp = getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return false;
        }
        reqData=null;

        reqSign=9;
        logger.info("sign:"+reqSign+" operation:modifyGatewayInfo");
        timetag= DateUtil.getFormat2TimetagStr();
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"gatewayName\":\""+gatewayName+"\",\"gatewayLocation\":\""+gatewayLocation+"\",\"gatewayComment\":\""+gatewayComment+"\",\"timetag\":\""+timetag+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"gatewayName\":\""+gatewayName+"\",\"gatewayLocation\":\""+gatewayLocation+"\",\"gatewayComment\":\""+gatewayComment+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        logger.info(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteGateway(String ownerPhoneNumber, String gatewayCode) {
        gatewayIp = getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return false;
        }
        reqData=null;

        reqSign=10;
        logger.info("sign:"+reqSign+" operation:deleteGateway");
        timetag= DateUtil.getFormat2TimetagStr();
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"timetag\":\""+timetag+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        logger.info(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }
}
