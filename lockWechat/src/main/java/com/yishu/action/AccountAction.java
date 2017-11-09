package com.yishu.action;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.entities.Parameterizable;
import com.yishu.domain.WechatUser;
import com.yishu.pojo.LockUser;
import com.yishu.jwt.*;
import com.yishu.service.IWechatService;
import com.yishu.util.*;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @bottom Copyright &#169; {inceptionYear}&#x2013;{currentYear} {organizationName}. All rights reserved.
 */
public class AccountAction extends ActionSupport implements Parameterizable,SessionAware {
    public AccountAction() {
        System.out.println(">>>Initialization AccountAction......................................");
        logger.info(">>>Initialization AccountAction......................................");
    }
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("AccountAction");

    @Autowired
    IWechatService wechatService;

    private String ownerPhoneNumber;
    private String openid;

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();

    /**
     * 已获得openid,再获得ownerPhoneNumber.
     * WechatUser关联了openid与ownerPhoneNumber.
     *
     * @return
     */
    public String wxLogin () {
        ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        if (null==ownerPhoneNumber){
            openid= (String) session.getAttribute(IWechatService.OPENID);
            logger.info("openid :"+openid);
//            setOpenid(openid);
            //有openid无ownerPhoneNumber
            WechatUser wechatUser = wechatService.findWechatUserByopenid(openid);
            logger.info("wechatUser : "+wechatUser);
            if (wechatUser != null) {
                ownerPhoneNumber=wechatUser.getLockUser().getPhonenumber();
                logger.info("ownerPhoneNumber :"+ownerPhoneNumber);
                session.setAttribute("ownerPhoneNumber",ownerPhoneNumber);
//                setOwnerPhoneNumber(ownerPhoneNumber);
            }else{
                //已获得openid,无ownerPhoneNumber.交由register.jsp获取短信验证码.
                return "register";
            }
        }
        return "main";
    }

    private String registerPhoneNumber;
    public String getRegisterPhoneNumber() {
        return registerPhoneNumber;
    }
    public void setRegisterPhoneNumber(String registerPhoneNumber) {
        this.registerPhoneNumber = registerPhoneNumber;
    }

    /**
     * 调用短信接口,给用户提交的手机号码发送验证码
     *
     */
    public String sendVerifyCode() throws Exception {
        logger.info("客户端提交的手机号码："+registerPhoneNumber);
        String verifyCodeStr=VerifyCodeUtils.generateVerifyCodeNum(6);
        SendSmsResponse smsResponse=null;
        String sms_BizId=null;
        smsResponse=SmsUtil.sendVerifyCode(registerPhoneNumber,verifyCodeStr);
        session.setAttribute("verifyCode",verifyCodeStr);
        sms_BizId=smsResponse.getBizId();
        session.setAttribute("sms_BizId",sms_BizId);
        if(smsResponse.getCode() == null || ! smsResponse.getCode().equals("OK")){
            logger.error("发送短信验证码失败");
            return "register";//发送短信验证码失败,重新发送.
        }
        logger.info("发送 短信验证码为："+verifyCodeStr+" ,流水号为："+sms_BizId);
        return "register2";
    }

    private String verifyCode;
    public String getVerifyCode() {
        return verifyCode;
    }
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    /**
     * 接收客户端提交的验证码,查询服务器发送的验证码.并检验两者是否相同,相同则注册用户信息.
     *
     */
    public String checkVerifyCodeThenRegister() throws ClientException, ParseException {
        String sms_BizId=null;
        sms_BizId= (String) session.getAttribute("sms_BizId");
        logger.info("查询 已发送短信 流水号为："+sms_BizId);
        SendSmsResponse smsResponse=null;
        QuerySendDetailsResponse querySendDetailsResponse = SmsUtil.querySendDetails(sms_BizId);
//        System.out.println("短信明细查询接口返回数据----------------");
//        System.out.println("Code=" + querySendDetailsResponse.getCode());
//        System.out.println("Message=" + querySendDetailsResponse.getMessage());
        String phoneNum=null;
        String sendDateStr=null;
        int i = 0;
        for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs()) {
//            System.out.println("SmsSendDetailDTO["+i+"]:");
//            System.out.println("Content=" + smsSendDetailDTO.getContent());
//            System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
//            System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
//            System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
            logger.info("查询已发送的短信 内容为："+smsSendDetailDTO.getContent());
            phoneNum=smsSendDetailDTO.getPhoneNum();
            sendDateStr=smsSendDetailDTO.getSendDate();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeLag= new Date().getTime()- (dateFormat.parse(sendDateStr).getTime());
        WechatUser wechatUser=null;
        LockUser lockUser=null;
        logger.info("客户端提交验证码:"+verifyCode+" 服务端发送验证码:"+session.getAttribute("verifyCode"));
        logger.info("验证码发送时间:"+sendDateStr);
        if(timeLag < 5*60*1000 && verifyCode.equals(session.getAttribute("verifyCode")) ){
            //验证码未超时并且客户端提交的验证码与服务器发送的验证码相同,即验证码有效,注册用户信息.
            logger.info("短信验证码有效!");
            wechatUser = new WechatUser();
            wechatUser.setOpenid(openid);
            wechatUser.setCreatetime(DataUtil.fromDate24H());
            lockUser=new LockUser();
            lockUser.setPhonenumber(phoneNum);
            wechatUser.setLockUser(lockUser);
            wechatService.addSubscribe(wechatUser);
            session.setAttribute("ownerPhoneNumber",phoneNum);
        }else {
            //验证码无效(超时或不正确),重新获取.
            logger.info("短信验证码无效!");
//            session.setAttribute("errMsg","验证码超时，请重新获取验证码。");
//            return "error";
            return "register";
        }
//        System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
        return "main";
    }
    //********************************************************************************************************

    Map<String, String> params;
    @Override
    public void addParam(String s, String s1) {
    }
    @Override
    public void setParams(Map<String, String> map) {
        this.params =map;
    }
    @Override
    public Map<String, String> getParams() {
        return this.params;
    }

    private Map sessionMap;
    @Override
    public void setSession(Map<String, Object> map) {
        this.sessionMap =map;
    }

    /**
     * Object jsonResult——返回的JSON格式的Model
     */
    private Object jsonResult;
    public Object getJsonResult() {
        return jsonResult;
    }
}