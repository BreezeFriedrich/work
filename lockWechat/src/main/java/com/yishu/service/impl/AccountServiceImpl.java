/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.service.IAccountService;
import com.yishu.util.DateUtil;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-16 10:13 admin
 * @since JDK1.7
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);

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

    @Override
    public boolean modifyNickname(String ownerPhoneNumber, String newName) {
        timetag= DateUtil.getFormat2TimetagStr();
        reqSign=24;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"newName\":\""+newName+"\",\"timetag\":\""+timetag+"\"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData:"+rawData);
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: 0 成功  1  失败
        respSign=rootNode.path("result").asInt();
        return 0==respSign;
    }

    @Override
    public boolean modifyPassword(String ownerPhoneNumber, String newPassword) {
        timetag= DateUtil.getFormat2TimetagStr();
        reqSign=23;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"newPassword\":\""+newPassword+"\",\"timetag\":\""+timetag+"\"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData:"+rawData);
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: 0 成功  1  失败
        respSign=rootNode.path("result").asInt();
        return 0==respSign;
    }

    @Override
    public Map queryAuthPassword(String ownerPhoneNumber) {
        reqSign=32;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData:"+rawData);
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: 0 成功 ;1 失败 ;2 未设置手势密码
        respSign=rootNode.path("result").asInt();
        String authPassword=rootNode.path("authPassword").asText();//authPassword授权密码,由数字与字母组成.
        Map resultMap=new HashMap();
        resultMap.put("result",respSign);
        resultMap.put("authPassword",authPassword);
        LOG.info("return resultMap:"+resultMap);
        return resultMap;
    }

    /**
     * 判断开锁授权密码是否有效.若还未设置授权密码,则待检授权密码视为有效,返回true.
     *
     * @param authPassword 待检授权密码
     * @return true 待检授权密码是真实密码或者还未设置开锁授权密码; false 待检授权密码与真实开锁授权密码不同.
     */
    @Override
    public Map validAuthPassword(String ownerPhoneNumber, String authPassword) {
        reqSign=32;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData:"+rawData);
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: 0 成功 ;1 失败 ;2 未设置开锁授权密码
        respSign=rootNode.path("result").asInt();
        Map resultMap=new HashMap();
        if(0==respSign){
            String realAuthPassword=rootNode.path("authPassword").asText();//authPassword授权密码,由数字与字母组成.
            if (realAuthPassword.equals(authPassword)){
                resultMap.put("result",0);
                resultMap.put("msg","授权码校验结果为正确");
            }else {
                resultMap.put("result",1);
                resultMap.put("msg","授权码校验结果为错误");
            }
        }else if (2==respSign){
            resultMap.put("result",2);
            resultMap.put("msg","未设置开锁授权码");
        }else if (1==respSign){
            resultMap.put("result",1);
            resultMap.put("msg","授权码获取失败");
        }else {
            resultMap.put("result",3);
            resultMap.put("msg","授权码获取结果未定义");
        }
        LOG.info("resultMap:"+resultMap);
        return resultMap;
    }

    @Override
    public boolean proofAuthpassword(String ownerPhoneNumber, String authPassword) {
        reqSign=32;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData:"+rawData);
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: 0 成功 ;1 失败 ;2 未设置开锁授权密码
        respSign=rootNode.path("result").asInt();
        if(0==respSign) {
            String realAuthPassword = rootNode.path("authPassword").asText();//authPassword授权密码,由数字与字母组成.
            if (realAuthPassword.equals(authPassword)) {
                return true;
            }
        }
        return false;
    }
    /*
    @Override
    public boolean validAuthPassword(String ownerPhoneNumber, String authPassword) {
        reqSign=32;
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToOwner(reqData);
        Map resultMap=new HashMap();
        LOG.info("rawData:"+rawData);
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: 0 成功 ;1 失败 ;2 未设置手势密码
        respSign=rootNode.path("result").asInt();
        if(0==respSign){
            // 0 成功
            String realAuthPassword=rootNode.path("authPassword").asText();//authPassword授权密码,由数字与字母组成.
            return realAuthPassword.equals(authPassword);
        }else if(2==respSign){
            // 2 未设置手势密码
            LOG.info("sign:"+reqSign+" operation:获取开锁授权密码"+" result:"+respSign+" 结果:未设置手势密码");
            return true;
        }
        return false;
    }
     */

    @Override
    public boolean authAuthPassword(String ownerPhoneNumber, String authPassword) {
        timetag= DateUtil.getFormat2TimetagStr();
        reqSign=31;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"authPassword\":\""+authPassword+"\",\"timetag\":\""+timetag+"\"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData:"+rawData);
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: 0 成功  1  失败
        respSign=rootNode.path("result").asInt();
        return 0==respSign;
    }

    /*
    @Override
    public Map queryAuthPassword(String ownerPhoneNumber) {
        reqSign=28;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToOwner(reqData);
        Map resultMap=new HashMap();
        LOG.info("rawData:"+rawData);
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: 0 成功 ;1 失败 ;2 未设置手势密码
        respSign=rootNode.path("result").asInt();
        String authPassword=rootNode.path("authPassword").asText();
        resultMap.put("result",respSign);
        resultMap.put("authPassword",authPassword);
        LOG.info("return resultMap:"+resultMap);
        return resultMap;
    }

    @Override
    public boolean authAuthPassword(String ownerPhoneNumber, String authPassword) {
        timetag= DateUtil.getFormat2TimetagStr();
        reqSign=29;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"authPassword\":\""+authPassword+"\",\"timetag\":\""+timetag+"\"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData:"+rawData);
//        if ("".equals(rawData)) {
//            return null;
//        }
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: 0 成功  1  失败
        respSign=rootNode.path("result").asInt();
        return 0==respSign;
    }
    */

    /**
     * 微信端登录,可用来获取ownerName.
     *
     */
    @Override
    public Map wechatLogin(String openid) {
        timetag= DateUtil.getFormat2TimetagStr();
        reqSign=201;
        reqData="{\"sign\":"+reqSign+",\"openid\":"+openid+",\"timetag\":"+timetag+"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData:"+rawData);
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: -1 查询失败 ,0 openid存在 ,1 openid不存在
        respSign=rootNode.path("result").asInt();
        Map resultMap=new HashMap();
        resultMap.put("result",respSign);
        String ownerPhoneNumber=rootNode.path("ownerPhoneNumber").asText();
        resultMap.put("ownerPhoneNumber",ownerPhoneNumber);
        String ownerName=rootNode.path("ownerName").asText();
        resultMap.put("ownerName",ownerName);
        LOG.info("resultMap",resultMap);

        return resultMap;
    }
}
