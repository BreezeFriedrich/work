/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.IdentityCard;
import com.yishu.pojo.UnlockPwd;
import com.yishu.pojo.UnlockPwds;
import com.yishu.service.IGatewayService;
import com.yishu.service.ILockService;
import com.yishu.service.IUnlockService;
import com.yishu.util.DateUtil;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-08 15:43 admin
 * @since JDK1.7
 */
@Service("unlockService")
public class UnlockServiceImpl implements IUnlockService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("UnlockServiceImpl");

    @Autowired
    private IGatewayService gatewayService;
    @Autowired
    private ILockService lockService;

    String gatewayIp=null;

    String timetag;
    /**
     * 开锁信息序列号 32位 timetag 14位+使用者手机号11位+随机整数7位
     */
    String serviceNumb;

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

    private String getServiceNumb(String ownerPhoneNumber,String timetag){
        StringBuffer stringBuffer=new StringBuffer();
        int randomNum= (int) (Math.random()*10000000);
        stringBuffer.append(timetag)
                    .append(ownerPhoneNumber)
                    .append(randomNum);
        return new String(stringBuffer);
    }

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

    /**
     * sign=17,获取门锁授权开锁身份证信息
     *
     * @return List<IdentityCard>
     */
    @Override
    public List getUnlockId(String ownerPhoneNumber, String gatewayCode, String lockCode) {
        gatewayIp = gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return null;
        }

        reqSign=17;
        timetag= DateUtil.getFormat2TimetagStr();
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        logger.info(rawData);

        if (respFail()){//请求数据失败
            return null;
        }
        JsonNode unlockIdsNode=rootNode.path("userList");
        List unlockIdList=new ArrayList<IdentityCard>();
        try {
            unlockIdList=objectMapper.readValue(unlockIdsNode.traverse(), new TypeReference<List<IdentityCard>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return unlockIdList;
    }

    /**
     * 添加身份证开锁授权
     *
     * @param ownerPhoneNumber 开锁帐号
     * @param name 开锁者姓名
     * @param dnCode 开锁者身份证dn码 ,如果非nfc方式添加身份证则为空
     * @return 操作结果
     */
    @Override
    public boolean authUnlockById(String ownerPhoneNumber, String gatewayCode, String lockCode, String name, String cardNumb, String dnCode, String startTime, String endTime) {
        gatewayIp = gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return false;
        }

        reqSign=18;
        timetag= DateUtil.getFormat2TimetagStr();
        serviceNumb=getServiceNumb(ownerPhoneNumber,timetag);
//        logger.info("startTime-1 : "+startTime);
//        logger.info("endTime-1   : "+endTime);
        try {
            startTime=DateUtil.format1tillminStringToformat2tillminString(startTime);
            endTime=DateUtil.format1tillminStringToformat2tillminString(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        logger.info("startTime-2 : "+startTime);
//        logger.info("endTime-2   : "+endTime);
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"name\":\""+name+"\",\"cardNumb\":\""+cardNumb+"\",\"dnCode\":\""+dnCode+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"name\":\""+name+"\",\"cardNumb\":\""+cardNumb+"\",\"dnCode\":\""+dnCode+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        logger.info(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    /**
     * 取消身份证开锁授权
     *
     * @return 操作结果
     */
    @Override
    public boolean prohibitUnlockById(String ownerPhoneNumber, String lockCode, String cardNumb,String serviceNumb) {
        gatewayIp = lockService.getLockIp(ownerPhoneNumber,lockCode);
        if (null == gatewayIp) {
            return false;
        }

        reqSign=19;
        timetag= DateUtil.getFormat2TimetagStr();
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"cardNumb\":\""+cardNumb+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"cardNumb\":\""+cardNumb+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        logger.info(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    /**
     * 获取智能锁开锁密码
     *
     * @return 开锁密码对象UnlockPwds
     */
    @Override
    public UnlockPwds getUnlockPwd(String ownerPhoneNumber, String gatewayCode, String lockCode) {
        gatewayIp = gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return null;
        }

        reqSign=20;
        timetag= DateUtil.getFormat2TimetagStr();
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        logger.info(rawData);

        if (respFail()){//请求数据失败
            return null;
        }
        String defaultPassword1 = rootNode.path("defaultPassword1").asText();
        String defaultPassword2 = rootNode.path("defaultPassword2").asText();
        JsonNode unlockPwdNode=rootNode.path("passwordList");
        List unlockPwdList=new ArrayList<UnlockPwd>();
        try {
            unlockPwdList=objectMapper.readValue(unlockPwdNode.traverse(), new TypeReference<List<UnlockPwd>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        UnlockPwds unlockPwds = new UnlockPwds();
        unlockPwds.setDefaultPassword1(defaultPassword1);
        unlockPwds.setDefaultPassword2(defaultPassword2);
        unlockPwds.setPasswordList(unlockPwdList);
        return unlockPwds;
    }

    /**
     * 添加智能锁密码
     *
     * @return
     */
    @Override
    public boolean authUnlockByPwd(String ownerPhoneNumber, String gatewayCode, String lockCode, String password, String startTime, String endTime) {
        gatewayIp = gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return false;
        }

        reqSign=21;
        timetag= DateUtil.getFormat2TimetagStr();
        serviceNumb=getServiceNumb(ownerPhoneNumber,timetag);
        logger.info("startTime-1 : "+startTime);
        logger.info("endTime-1   : "+endTime);
        try {
            startTime=DateUtil.format1tillminStringToformat2tillminString(startTime);
            endTime=DateUtil.format1tillminStringToformat2tillminString(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        logger.info("startTime-2 : "+startTime);
        logger.info("endTime-2   : "+endTime);
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"password\":\""+password+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"password\":\""+password+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        logger.info(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    /**
     * 取消智能锁密码
     *
     * @param gatewayCode 这是https json协议中多余的一项
     * @return
     */
    @Override
    public boolean prohibitUnlockByPwd(String ownerPhoneNumber, String gatewayCode, String lockCode,String serviceNumb) {
        gatewayIp = gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return false;
        }

        reqSign=22;
        timetag= DateUtil.getFormat2TimetagStr();
//        serviceNumb=getServiceNumb(ownerPhoneNumber,timetag);
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"gatewayCode\":\""+gatewayCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"gatewayCode\":\""+gatewayCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        logger.info(rawData);

        if (respFail()){
            return false;
        }

        return true;
    }
}
