/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.SMSVerificationCode;
import com.yishu.service.ISMSService;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-23 9:34 admin
 * @since JDK1.7
 */
@Service("smsService")
public class SMSServiceImpl implements ISMSService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("SMSServiceImpl");

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
    public Map sendVerifyCode(String phoneNumber) {
        reqSign=33;
        reqData="{\"sign\":"+reqSign+",\"sendSmsPhoneNumber\":\""+phoneNumber+"\",\"ownerPhoneNumber\":\""+"\"}";
        LOG.info("reqData:"+reqData);
        long time1=new Date().getTime();
        rawData = HttpUtil.httpsPostToGateway(reqData);
        LOG.info("rawData:"+rawData);
        long time2=new Date().getTime();
        LOG.warn("SMSServiceImpl-sendVerifyCode 用时: "+(time2-time1));
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //字段result: 0 成功 ,1 失败
        respSign=rootNode.path("result").asInt();
        Map resultMap=new HashMap();
        resultMap.put("result",respSign);
        if (0==respSign){
            String verificationCodeStr=rootNode.path("verificationCode").asText();
//            LOG.info("verificationCodeStr toString"+rootNode.path("verificationCode").toString());
//            LOG.info("verificationCodeStr asText"+rootNode.path("verificationCode").asText());
            SMSVerificationCode smsVerificationCode=new SMSVerificationCode();
            smsVerificationCode.setPhoneNumber(phoneNumber);
            smsVerificationCode.setVerificationCode(verificationCodeStr);
            smsVerificationCode.setCreateTime(new Date().getTime());
            resultMap.put("verificationCode",smsVerificationCode);
        }

        return resultMap;
    }
}
