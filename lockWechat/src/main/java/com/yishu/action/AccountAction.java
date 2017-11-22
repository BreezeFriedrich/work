package com.yishu.action;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.entities.Parameterizable;
import com.yishu.dao.LockUserDao;
import com.yishu.service.ILoginService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @bottom Copyright &#169; {inceptionYear}&#x2013;{currentYear} {organizationName}. All rights reserved.
 */
public class AccountAction extends ActionSupport implements Parameterizable,SessionAware {
    public AccountAction() {
        logger.info(">>>Initialization AccountAction......................................");
    }
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("AccountAction");

    @Autowired
    ILoginService loginService;
    @Autowired
    LockUserDao lockUserDao;

    private String code;
    private String state;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    private String ownerPhoneNumber;
    private String ownerPassword;
    private String openid;
    private String ownerName;
    private int registerMode;
    private String errMsg;

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public String getOwnerPassword() {
        return ownerPassword;
    }

    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(int registerMode) {
        this.registerMode = registerMode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
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
        logger.warn("wxLogin.action");
        openid= (String) session.getAttribute("OPENID");
        logger.info("openid :"+openid);
        //有openid无ownerPhoneNumber
        /*
        WechatUser wechatUser = wechatService.findWechatUserByopenid(openid);
        logger.info("wechatUser : "+wechatUser);
        if (wechatUser != null) {
            LockUser lockUserTemp=lockUserDao.findLockUserById(wechatUser.getLockUserId());
            ownerPhoneNumber=lockUserTemp.getPhonenumber();
            logger.info("ownerPhoneNumber :"+ownerPhoneNumber);
            session.setAttribute("ownerPhoneNumber",ownerPhoneNumber);
        }else{
            //已获得openid,无ownerPhoneNumber.交由register.jsp获取短信验证码.
            return "register";
        }
        return "main";
        */
        Map map=loginService.openidExist(openid);
        //如果http返回的字段result=-1,则http请求查询openid遭遇失败.
        if (-1==(int)map.get("result")){
            errMsg="http请求查询后台openid遭遇失败";
            logger.info("http请求查询后台openid遭遇失败");
            return ActionSupport.ERROR;
        }
        //0==result,则openid存在,直接登录.
        if (0==(int)map.get("result")){
            ownerPhoneNumber= (String) map.get("ownerPhoneNumber");
            logger.info("ownerPhoneNumber :"+ownerPhoneNumber);
            session.setAttribute("ownerPhoneNumber",ownerPhoneNumber);
            logger.warn("openid存在,即将登录");
            return "main";
        }
        //如果http返回的字段result=1,则openid不存在,进入注册流程.
        if (1==(int)map.get("result")){
            logger.warn("openid不存在,即将进入注册流程");
            return "SMSVerifyCode";
        }
        return ActionSupport.ERROR;
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
    /*
    public String sendVerifyCode() throws Exception {
        logger.info("客户端提交的手机号码："+registerPhoneNumber);
        String verifyCodeStr=VerifyCodeUtils.generateVerifyCodeNum(6);
        SendSmsResponse smsResponse=null;
        String sms_BizId=null;
        smsResponse=SmsUtil.sendVerifyCode(registerPhoneNumber,verifyCodeStr);
        session.setAttribute("registerPhoneNumber",registerPhoneNumber);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        session.setAttribute("sendDateStr",dateFormat.format(new Date()));
        session.setAttribute("verifyCode",verifyCodeStr);
        sms_BizId=smsResponse.getBizId();
        session.setAttribute("sms_BizId",sms_BizId);
        if(smsResponse.getCode() == null || ! smsResponse.getCode().equals("OK")){
            logger.error("发送短信验证码失败");
            return "SMSVerifyCode";//发送短信验证码失败,重新发送.
        }
        logger.info("发送 短信验证码为："+verifyCodeStr+" ,流水号为："+sms_BizId);
        return "bindOpenid";
    }
    */
    public String sendVerifyCode() throws Exception {
        logger.warn("sendVerifyCode.action");
        logger.info("客户端提交的手机号码："+registerPhoneNumber);
        String verifyCodeStr=VerifyCodeUtils.generateVerifyCodeNum(6);
        SendSmsResponse smsResponse=null;
        String sms_BizId=null;
        smsResponse=SmsUtil.sendVerifyCode(registerPhoneNumber,verifyCodeStr);
        session.setAttribute("registerPhoneNumber",registerPhoneNumber);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        session.setAttribute("sendDateStr",dateFormat.format(new Date()));
        session.setAttribute("verifyCode",verifyCodeStr);
        sms_BizId=smsResponse.getBizId();
        session.setAttribute("sms_BizId",sms_BizId);
        logger.info("发送 短信验证码为："+verifyCodeStr+" ,流水号为："+sms_BizId);
        Map <String,Object> resultMap=new HashMap<>(1);
        if(smsResponse.getCode() == null || ! smsResponse.getCode().equals("OK")){
            logger.error("发送短信验证码失败");
            resultMap.put("result",false);
        }
        resultMap.put("smsverifycode",verifyCodeStr);
        jsonResult=resultMap;
        return "json";
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
    /*
    public String checkVerifyCodeThenRegister() throws ClientException, ParseException {
        String sms_BizId=null;
        sms_BizId= (String) session.getAttribute("sms_BizId");
        String registerPhoneNumber= (String) session.getAttribute("registerPhoneNumber");
        logger.info("查询 已发送短信 流水号为："+sms_BizId);
        SendSmsResponse smsResponse=null;
        QuerySendDetailsResponse querySendDetailsResponse = SmsUtil.querySendDetails(registerPhoneNumber,sms_BizId);
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
        if (null != sendDateStr){
            logger.warn("获得短信回执,短信发送时间 : "+ sendDateStr);
        }else {
            sendDateStr= (String) session.getAttribute("sendDateStr");
            logger.info("短信发送时间 : "+sendDateStr);
        }
        if (null != phoneNum){
            logger.warn("获得短信回执,短信发送目标手机 : "+ phoneNum);
        }else {
            phoneNum= (String) session.getAttribute("registerPhoneNumber");
            logger.info("短信发送目标手机 : "+phoneNum);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeLag= new Date().getTime()- (dateFormat.parse(sendDateStr).getTime());
        WechatUser wechatUser=null;
        LockUser lockUser=null;
        logger.info("客户端提交验证码:"+verifyCode+" 服务端发送验证码:"+session.getAttribute("verifyCode"));
        logger.info("验证码发送时间:"+sendDateStr);
        openid= (String) session.getAttribute("OPENID");
        logger.info("session中OPENID 为 ",openid);
        if(timeLag < 5*60*1000 && verifyCode.equals(session.getAttribute("verifyCode")) ){
            //验证码未超时并且客户端提交的验证码与服务器发送的验证码相同,即验证码有效,注册用户信息.
            logger.info("短信验证码有效!");
            wechatUser = new WechatUser();
            wechatUser.setOpenid(openid);
            wechatUser.setCreatetime(DataUtil.fromDate24H());
            wechatService.addSubscribe(wechatUser,phoneNum);

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
    */
    public String checkVerifyCode() throws ClientException, ParseException {
        logger.warn("checkVerifyCode.action");
        String sms_BizId=null;
        sms_BizId= (String) session.getAttribute("sms_BizId");
        String registerPhoneNumber= (String) session.getAttribute("registerPhoneNumber");
        logger.info("查询 已发送短信 流水号为："+sms_BizId);
        SendSmsResponse smsResponse=null;
        QuerySendDetailsResponse querySendDetailsResponse = SmsUtil.querySendDetails(registerPhoneNumber,sms_BizId);
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
        if (null != sendDateStr){
            logger.warn("获得短信回执,短信发送时间 : "+ sendDateStr);
        }else {
            sendDateStr= (String) session.getAttribute("sendDateStr");
            logger.info("短信发送时间 : "+sendDateStr);
        }
        if (null != phoneNum){
            logger.warn("获得短信回执,短信发送目标手机 : "+ phoneNum);
        }else {
            phoneNum= (String) session.getAttribute("registerPhoneNumber");
            logger.info("短信发送目标手机 : "+phoneNum);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeLag= new Date().getTime()- (dateFormat.parse(sendDateStr).getTime());
        logger.info("客户端提交验证码:"+verifyCode+" 服务端发送验证码:"+session.getAttribute("verifyCode"));
        logger.info("验证码发送时间:"+sendDateStr);
        openid= (String) session.getAttribute("OPENID");
        logger.info("session中OPENID 为 ",openid);
        Map <String,Object> resultMap=new HashMap<>(1);
        if(timeLag < 5*60*1000 && verifyCode.equals(session.getAttribute("verifyCode")) ){
            //验证码未超时并且客户端提交的验证码与服务器发送的验证码相同,即验证码有效,注册用户信息.
            logger.info("短信验证码有效!");
            resultMap.put("result",1);
            session.setAttribute("ownerPhoneNumber",phoneNum);
        }else {
            //验证码无效(超时或不正确),重新获取.
            logger.info("短信验证码无效!");
            resultMap.put("result",2);
            resultMap.put("errMsg","短信验证码错误或超时");
        }
        jsonResult=resultMap;
        return "json";
    }

    public String bindOpenid(){
        logger.warn("bindOpenid.action");
        openid= (String) session.getAttribute("OPENID");
        int result=loginService.bindOpenidToPhone(openid,ownerPhoneNumber,ownerPassword);
        session.setAttribute("ownerPhoneNumber",ownerPhoneNumber);
        session.setAttribute("ownerPassword",ownerPassword);
        Map resultMap=new HashMap<String,Object>(1);
        resultMap.put("result",result);
        if (0==result){
            //已绑定openid到phone,直接登录.
//            return "main";
        }
        if (2==result){
            //手机号不存在，需要注册手机号.
//            return "register";
        }
        if (1==result){
            //登录失败,密码错误
            resultMap.put("errMsg","登录失败,密码错误");
        }
        if (-1==result){
            //绑定失败
            resultMap.put("errMsg","绑定失败");
        }
        jsonResult=resultMap;
        return "json";
    }

    public String register(){
        logger.warn("register.action");
        openid= (String) session.getAttribute("OPENID");
        boolean booleanResult=loginService.register(ownerName,ownerPhoneNumber,ownerPassword,openid);
        Map resultMap=new HashMap();
        resultMap.put("result",booleanResult);
        jsonResult=resultMap;
        return "json";
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