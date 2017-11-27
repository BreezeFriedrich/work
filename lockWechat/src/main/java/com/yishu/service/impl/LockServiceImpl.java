/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.Device;
import com.yishu.pojo.Lock;
import com.yishu.service.IDeviceService;
import com.yishu.service.IGatewayService;
import com.yishu.service.ILockService;
import com.yishu.util.DateUtil;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-12 10:22 admin
 * @since JDK1.7
 */
@Service("lockService")
public class LockServiceImpl implements ILockService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("LockServiceImpl");

    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IGatewayService gatewayService;

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

    @Override
    public Map hasLockAdded(String ownerPhoneNumber, String lockCode) {
        Map resultMap=new HashMap();
        gatewayIp = getLockIp(ownerPhoneNumber,lockCode);
        if (null == gatewayIp) {
            // 此处不能return null.因为未找到lockCode关联的gatewayIp,说明此门锁lockCode未被添加过.
            resultMap.put("result",0);
            resultMap.put("alreadyPhoneNumber","");
            return resultMap;
        }
        reqData=null;

        reqSign=11;
        System.err.println("sign:"+reqSign+" operation:hasLockAdded");
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        System.err.println(rawData);
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

        return resultMap;
    }

    @Override
    public boolean addLock(String ownerPhoneNumber, String gatewayCode, String lockCode, String lockName, String lockLocation, String lockComment) {
        /* !!弃用httpsPostToIp(gatewayIp,reqData).逻辑错误:向还未关联的gatewayIp发送请求.
        gatewayIp = getLockIp(ownerPhoneNumber,lockCode);
        if (null == gatewayIp) {
            return false;
        }
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        */

        reqSign=12;
        System.err.println("sign:"+reqSign+" operation:addLock");
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"lockName\":\""+lockName+"\",\"lockLocation\":\""+lockLocation+"\",\"lockComment\":\""+lockComment+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.httpsPostToQixu(reqData);
        System.err.println(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    @Override
    public boolean modifyLockInfo(String ownerPhoneNumber, String lockCode, String lockName, String lockLocation, String lockComment) {
        gatewayIp = getLockIp(ownerPhoneNumber,lockCode);
        if (null == gatewayIp) {
            return false;
        }
        reqData=null;

        reqSign=13;
        System.err.println("sign:"+reqSign+" operation:modifyLockInfo");
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"lockName\":\""+lockName+"\",\"lockLocation\":\""+lockLocation+"\",\"lockComment\":\""+lockComment+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        System.err.println(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteLock(String ownerPhoneNumber, String lockCode) {
        gatewayIp = getLockIp(ownerPhoneNumber,lockCode);
        if (null == gatewayIp) {
            return false;
        }
        reqData=null;

        reqSign=14;
        System.err.println("sign:"+reqSign+" operation:deleteLock");
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        System.err.println(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    /**
     * 获取锁lockCode关联的网关gatewayCode,进一步获取它的数据服务器gatewayIp.
     *
     * @return 可能的值: 1.null 2.String gatewayIp
     */
    @Override
    public String getLockIp(String ownerPhoneNumber, String lockCode) {
        List accountDeviceList=deviceService.getDeviceInfo(ownerPhoneNumber);

        String gatewayCode=null;
        Iterator deviceListItr;
        Iterator lockListItr;
        Device device;
        Lock lock;
        for (deviceListItr=accountDeviceList.iterator();null == gatewayCode && deviceListItr.hasNext();){
            device= (Device) deviceListItr.next();
            for (lockListItr=device.getLockLists().iterator(); null == gatewayCode && lockListItr.hasNext();) {
                if ( lockCode.equals(((Lock)lockListItr.next()).getLockCode()) ){
                    gatewayCode = device.getGatewayCode();
                }
            }
        }
        if (null == gatewayCode){
            return null;
        }
        String gatewayIp = gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        return gatewayIp;
    }
}
