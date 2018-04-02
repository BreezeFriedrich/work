/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.service.ILoginService;
import com.yishu.util.DateUtil;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-21 13:36 admin
 * @since JDK1.7
 */
@Service("loginService")
public class LoginServiceImpl implements ILoginService{
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("LoginServiceImpl");

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

    /**
     * 微信登录，判断openid是否存在
     *
     * @param openid
     * @return
     */
    @Override
    public Map openidExist(String openid) {
        timetag= DateUtil.getFormat2TimetagStr();
        reqSign=201;
        reqData="{\"sign\":"+reqSign+",\"openid\":\""+openid+"\",\"timetag\":\""+timetag+"\"}";
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
        LOG.info("ownerPhoneNumber",ownerPhoneNumber);
        String ownerName=rootNode.path("ownerName").asText();
        resultMap.put("ownerName",ownerName);

        return resultMap;
    }

    /**
     * 绑定openid
     *
     * @param openid
     * @param ownerPhoneNumber
     * @param ownerPassword
     * @return
     */
    @Override
    public int bindOpenidToPhone(String openid, String ownerPhoneNumber, String ownerPassword) {
        timetag= DateUtil.getFormat2TimetagStr();
        reqSign=202;
        reqData="{\"sign\":"+reqSign+",\"openid\":\""+openid+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"ownerPassword\":\""+ownerPassword+"\",\"timetag\":\""+timetag+"\"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData:"+rawData);
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: -1 绑定失败; 0 登录成功,openid已经与phoneNumber绑定（openid如果不匹配，则替换）; 1 登录失败,密码错误; 2 手机号不存在，需要注册手机号
        respSign=rootNode.path("result").asInt();
        return respSign;
    }

    /**
     * 手机号码未注册
     *
     * @param ownerName
     * @param ownerPhoneNumber
     * @param ownerPassword
     * @param openid
     * @return
     */
    @Override
    public boolean register(String ownerName, String ownerPhoneNumber, String ownerPassword, String openid) {
        timetag= DateUtil.getFormat2TimetagStr();
        reqSign=203;
        reqData="{\"sign\":"+reqSign+",\"ownerName\":\""+ownerName+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"ownerPassword\":\""+ownerPassword+"\",\"openid\":\""+openid+"\",\"timetag\":\""+timetag+"\"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData:"+rawData);
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: 0 成功 ,1 失败 openid已经与phoneNumber绑定
        respSign=rootNode.path("result").asInt();
        if (0==respSign){
            return true;
        }
        return false;
    }
}
