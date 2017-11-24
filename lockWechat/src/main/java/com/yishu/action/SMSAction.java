/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.action;

import com.yishu.pojo.SMSVerificationCode;
import com.yishu.service.ISMSService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-23 9:59 admin
 * @since JDK1.7
 */
public class SMSAction {
    public SMSAction() {
        logger.info(">>>Initialization SMSAction......................................");
    }
    private static final org.slf4j.Logger logger= LoggerFactory.getLogger("SMSAction");
    @Autowired
    ISMSService smsService;

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    /**
     * Object jsonResult——返回的JSON格式的Model
     */
    private Object jsonResult;
    public Object getJsonResult() {
        return jsonResult;
    }

    private String phoneNumber;
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * request param {String phoneNumber}
     * @return
     */
    public String sendVerifyCode(){
//        session.setAttribute("OPENID","oaxT-szv3F0hz8ZCy6Ke5kTe8QNA");
//        logger.info("sendVerifyCode.action");
        long time1=new Date().getTime();
        session.setAttribute("phoneNumber",phoneNumber);
        Map resultMap=smsService.sendVerifyCode(phoneNumber);
        SMSVerificationCode smsVerificationCode= (SMSVerificationCode) resultMap.get("verificationCode");
        if (null!=smsVerificationCode){
            session.setAttribute("smsVerificationCode",smsVerificationCode);
        }
//        logger.info("smsVerificationCode : "+session.getAttribute("smsVerificationCode"));
        jsonResult=resultMap;
        long time2=new Date().getTime();
        logger.warn("SMSAction-sendVerifyCode 用时: "+(time2-time1));
        return "json";
    }

    private String verificationCode;
    public String getVerificationCode() {
        return verificationCode;
    }
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    /**
     * 校验短信验证码是否有效.
     * 将用户提交的verificationCode与session中的smsVerificationCode比对.
     *
     * request param {String verificationCode}
     * @return
     */
    public String checkVerifyCode(){
        logger.info("checkVerifyCode.action");
        Map <String,Object> resultMap=new HashMap<>(1);
        phoneNumber= (String) session.getAttribute("phoneNumber");
//        session.removeAttribute("phoneNumber");
        SMSVerificationCode smsVerificationCode= (SMSVerificationCode) session.getAttribute("smsVerificationCode");
//        session.removeAttribute("smsVerificationCode");
        logger.info("smsVerificationCode : "+smsVerificationCode);
        if (null!=smsVerificationCode && smsVerificationCode.getPhoneNumber().equals(phoneNumber)){
            if (smsVerificationCode.getVerificationCode().equals(verificationCode)){
                if (new Date().getTime() - smsVerificationCode.getCreateTime() < ISMSService.EXPIRE_TIME){
                    //验证码有效
                    logger.info("验证码有效");
                    session.setAttribute("phoneNumber",phoneNumber);
                    resultMap.put("result",1);
                }else {
                    logger.info("短信验证码超时");
                    resultMap.put("result",2);
                    resultMap.put("errMsg","expired");
                }
            }else {
                logger.info("短信验证码无效");
                resultMap.put("result",3);
                resultMap.put("errMsg","invalid verificationCode");
            }
        }else {
            logger.info("没有发给"+phoneNumber+"的短信验证码");
            resultMap.put("result",4);
            resultMap.put("errMsg","no verificationCode with "+phoneNumber);
        }
        jsonResult=resultMap;
        return "json";
    }
}
