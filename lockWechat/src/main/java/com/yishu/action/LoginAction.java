package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.entities.Parameterizable;
import com.yishu.dao.LockUserDao;
import com.yishu.service.ILoginService;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @bottom Copyright &#169; {inceptionYear}&#x2013;{currentYear} {organizationName}. All rights reserved.
 */
public class LoginAction extends ActionSupport implements Parameterizable,SessionAware {
    public LoginAction() {
        LOG.info(">>>Initialization LoginAction......................................");
    }
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("LoginAction");

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
        LOG.info("login&session ￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥");
//        LOG.info("wxLogin.action");
        LOG.info("cDate:"+session.getAttribute("cDate"));
//        session.setAttribute("ownerPhoneNumber","13905169824");

        openid= (String) session.getAttribute("OPENID");
        LOG.info("openid :"+openid);
        Map map=loginService.openidExist(openid);
        //如果http返回的字段result=-1,则http请求查询openid遭遇失败.
        if (-1==(int)map.get("result")){
            errMsg="http请求查询后台openid遭遇失败";
            LOG.info("http请求查询后台openid遭遇失败");
            return ActionSupport.ERROR;
        }
        //0==result,则openid存在,直接登录.
        if (0==(int)map.get("result")){
            ownerPhoneNumber= (String) map.get("ownerPhoneNumber");
            LOG.info("ownerPhoneNumber :"+ownerPhoneNumber);
            session.setAttribute("ownerPhoneNumber",ownerPhoneNumber);
            /*使用struts2操作session,设置session.
            ActionContext.getContext().getSession().put("ownerPhoneNumber", ownerPhoneNumber);
             */
            LOG.warn("openid存在,即将登录");
            return "main";
        }
        //如果http返回的字段result=1,则openid不存在,进入注册流程.
        if (1==(int)map.get("result")){
            LOG.warn("openid不存在,即将进入注册流程");
            return "SMSVerifyCode";
        }
        return ActionSupport.ERROR;
    }

    private String phoneNumber;
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

/*
    public String sendVerifyCode() throws Exception {
        LOG.warn("sendVerifyCode.action");
        LOG.info("客户端提交的手机号码："+phoneNumber);
        String verifyCodeStr=VerifyCodeUtils.generateVerifyCodeNum(6);
        SendSmsResponse smsResponse=null;
        String sms_BizId=null;
        smsResponse=SmsUtil.sendVerifyCode(phoneNumber,verifyCodeStr);
        session.setAttribute("ownerPhoneNumber",phoneNumber);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        session.setAttribute("sendDateStr",dateFormat.format(new Date()));
        session.setAttribute("verifyCode",verifyCodeStr);
        sms_BizId=smsResponse.getBizId();
        session.setAttribute("sms_BizId",sms_BizId);
        LOG.info("发送 短信验证码为："+verifyCodeStr+" ,流水号为："+sms_BizId);
        Map <String,Object> resultMap=new HashMap<>(1);
        if(smsResponse.getCode() == null || ! smsResponse.getCode().equals("OK")){
            LOG.error("发送短信验证码失败");
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

    public String checkVerifyCode() throws ClientException, ParseException {
        LOG.warn("checkVerifyCode.action");
        String sms_BizId=null;
        sms_BizId= (String) session.getAttribute("sms_BizId");
        String phoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        LOG.info("查询 已发送短信 流水号为："+sms_BizId);
        SendSmsResponse smsResponse=null;
        QuerySendDetailsResponse querySendDetailsResponse = SmsUtil.querySendDetails(phoneNumber,sms_BizId);
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
            LOG.info("查询已发送的短信 内容为："+smsSendDetailDTO.getContent());
            phoneNum=smsSendDetailDTO.getPhoneNum();
            sendDateStr=smsSendDetailDTO.getSendDate();
        }
        if (null != sendDateStr){
            LOG.warn("获得短信回执,短信发送时间 : "+ sendDateStr);
        }else {
            sendDateStr= (String) session.getAttribute("sendDateStr");
            LOG.info("短信发送时间 : "+sendDateStr);
        }
        if (null != phoneNum){
            LOG.warn("获得短信回执,短信发送目标手机 : "+ phoneNum);
        }else {
            phoneNum= (String) session.getAttribute("ownerPhoneNumber");
            LOG.info("短信发送目标手机 : "+phoneNum);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeLag= new Date().getTime()- (dateFormat.parse(sendDateStr).getTime());
        LOG.info("客户端提交验证码:"+verifyCode+" 服务端发送验证码:"+session.getAttribute("verifyCode"));
        LOG.info("验证码发送时间:"+sendDateStr);
        Map <String,Object> resultMap=new HashMap<>(1);
        if(timeLag < 5*60*1000 && verifyCode.equals(session.getAttribute("verifyCode")) ){
            //验证码未超时并且客户端提交的验证码与服务器发送的验证码相同,即验证码有效,注册用户信息.
            LOG.info("短信验证码有效!");
            resultMap.put("result",1);
            session.setAttribute("ownerPhoneNumber",phoneNum);
        }else {
            //验证码无效(超时或不正确),重新获取.
            LOG.info("短信验证码无效!");
            resultMap.put("result",2);
            resultMap.put("errMsg","短信验证码错误或超时");
        }
        jsonResult=resultMap;
        return "json";
    }
*/

    public String bindOpenid(){
        LOG.info("bindOpenid.action");
        openid= (String) session.getAttribute("OPENID");
        phoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        int result=loginService.bindOpenidToPhone(openid,phoneNumber,ownerPassword);
        Map resultMap=new HashMap<String,Object>(1);
        resultMap.put("result",result);
        if (0==result){
            //已绑定openid到phone,直接登录.
            session.setAttribute("ownerPhoneNumber",phoneNumber);
//            return "main";
        }
        if (2==result){
            //手机号不存在，需要注册手机号.
            session.setAttribute("ownerPassword",ownerPassword);
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

    private String ownerName;
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String register(){
        LOG.info("register.action");
        openid= (String) session.getAttribute("OPENID");
        phoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        ownerPassword=(String) session.getAttribute("ownerPassword");
        session.removeAttribute("ownerPhoneNumber");
        session.removeAttribute("ownerPassword");
        boolean booleanResult=loginService.register(ownerName,phoneNumber,ownerPassword,openid);
        LOG.info("register结果为: "+booleanResult);
        if (booleanResult){
            session.setAttribute("ownerPhoneNumber",phoneNumber);
        }
        Map resultMap=new HashMap(1);
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