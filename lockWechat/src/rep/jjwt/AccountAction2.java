package com.yishu.action;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.entities.Parameterizable;
import com.yishu.domain.WechatUser;
import com.yishu.pojo.LockUser;
import com.yishu.pojo.User;
import com.yishu.jwt.*;
import com.yishu.service.IUserService;
import com.yishu.service.IWechatService;
import com.yishu.util.*;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        System.out.println(">>>Initialization LoginAction......................................");
    }
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("LoginAction");

//    @Autowired
//    private IUserService userService;
    @Autowired
    private Audience audience;

    public String loginTest(){
        String userName=loginPara.getUsername();
        String pwd=loginPara.getPassword();
        if ("18255683932".equals(userName) && "123456".equals(pwd)){
            sessionMap.put("account",userName);
            return Action.SUCCESS;
        }
        return Action.LOGIN;
    }

    /**
     * 应用帐户登录.再(由jjwt包)产生JSON web token,存于session域、ValueStack
     *
     * @return
     */
/*
    public String login () {
        System.out.println(">>>action method login..................................................");

        ResultMsg resultMsg=getJwtAccessToken(loginPara);
//        sessionMap.put("jwtAccessToken",resultMsg.getJwtAccessToken());
//        jsonMap=new HashMap<String,Object>();
//        jsonMap.put("LoginPara",loginPara);
//        jsonMap.put("JwtAccessToken",jwtAccessToken);
//        return "JSON_RESULT";
        if (0==resultMsg.getErrcode()) {
            sessionMap.put("jwtAccessToken",jwtAccessToken);
            sessionMap.remove("authenticateErrMsg");
//            return "JSON_RESULT";
            return Action.SUCCESS;
        } else {
            sessionMap.put("authenticateErrMsg",resultMsg.getErrmsg());
            sessionMap.remove("jwtAccessToken");
        }
        return Action.LOGIN;
    }
*/
    /**
     * 验证account(username+password),若通过验证则授权JwtAccessToken
     * 流程:1.比对request body 中的 LoginPara.clientId & 配置 jwt.properties 中的 audience.clientId;
     *     2.根据request body 中 LoginPara.userName,数据库中取出User帐户性息(其中User.password为经过加盐MD5处理后的值);
     *     3.将request body 中 LoginPara.password 做加盐MD5处理: MD5.getMD5(loginPara.getPassword()+user.getSalt());
     *     4.比对 2 和 3 中的password值,若成功则执行 5 ;
     *     5.根据 LoginPara 产生 jwt 认证所需的JwtAccessToken ;
//     * @param loginPara
     * @return
     */
/*
    public ResultMsg getJwtAccessToken (LoginPara loginPara) {
        ResultMsg resultMsg;
        try{
            /*
            //验证clientId
            if(null == loginPara.getClientId() || 0 != (loginPara.getClientId().compareTo(audience.getClientId()) )){
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_CLIENTID.getErrcode(), ResultStatusCode.INVALID_CLIENTID.getErrmsg(), null);
                return resultMsg;
            }
            */
/*
            //验证用户名密码
            System.out.println("LoginPara = "+loginPara+": {"+"'username'"+" : "+loginPara.getUsername()+" , "+"'password'"+" : "+loginPara.getPassword()+"}");
            WechatUser user = userService.findUserByName(loginPara.getUsername());
            System.out.println("user = "+user+": {"+"'name'"+" : "+user.getName()+","+"'password'"+" : "+user.getPassword()+"}");
            if (user == null){
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(),
                        ResultStatusCode.INVALID_PASSWORD.getErrmsg(), null);
                return resultMsg;
            } else {
                String md5Password = MD5.getMD5(loginPara.getPassword()+user.getSalt());
                System.out.println("md5Password : "+md5Password);
                System.out.println("md5Password : "+user.getPassword());
                if (md5Password.compareTo(user.getPassword()) != 0){
                    resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(), ResultStatusCode.INVALID_PASSWORD.getErrmsg(), null);
                    return resultMsg;
                }
            }
            System.out.println("!start to create JwtAccessToken");

            //拼装accessToken
            String accessToken = JwtHelper.createJWT(loginPara.getUsername(), String.valueOf(user.getId()),
                    user.getRole(), audience.getClientId(), audience.getName(),
                    audience.getExpiresSecond() * 1000, audience.getBase64Secret());

            //返回accessToken
            jwtAccessToken = new JwtAccessToken();
            jwtAccessToken.setAccess_token(accessToken);
            jwtAccessToken.setExpiration(new Date().getTime()+audience.getExpiresSecond());
            jwtAccessToken.setToken_type("jwt");
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(), jwtAccessToken);
            return resultMsg;
        } catch(Exception ex) {
            System.out.println("!exception");
            resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrcode(), ResultStatusCode.SYSTEM_ERR.getErrmsg(), null);
            return resultMsg;
        }
    }
*/

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

    /*
    获得ActionContext实例，以便访问Servlet API
    ActionContext ctx = ActionContext.getContext();
    // 存入application
    ctx.getApplication().put("msg", "application信息");
    // 保存session
    ctx.getSession().put("msg", "seesion信息");
    // 保存request信息
    HttpServletRequest request = ServletActionContext.getRequest();
    request.setAttribute("msg", "request信息");
    ActionContext ctx = ActionContext.getContext();
    Map<String,Object> sessionMap=ctx.getSession();
    */

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();

    public String wxLogin () {
        ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        if (null==ownerPhoneNumber){
            openid= (String) session.getAttribute(IWechatService.OPENID);
            //有openid无ownerPhoneNumber
            WechatUser wechatUser = wechatService.findWechatUserByopenid(openid);
            if (wechatUser != null) {
                ownerPhoneNumber=wechatUser.getLockUser().getPhonenumber();
                session.setAttribute("ownerPhoneNumber",ownerPhoneNumber);
                setOwnerPhoneNumber(ownerPhoneNumber);
            }
            return "register";
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

    public String register() throws Exception {
        String verifyCodeStr=VerifyCodeUtils.generateVerifyCodeNum(6);
        SendSmsResponse smsResponse=null;
        String sms_BizId=null;
        smsResponse=SmsUtil.sendVerifyCode(registerPhoneNumber,verifyCodeStr);
        session.setAttribute("verifyCode",verifyCodeStr);
        sms_BizId=smsResponse.getBizId();
        session.setAttribute("sms_BizId",sms_BizId);
        if(smsResponse.getCode() == null || ! smsResponse.getCode().equals("OK")){
            throw new Exception("发送短信验证码失败");
        }
        return "register2";
    }

    private String verifyCode;
    public String getVerifyCode() {
        return verifyCode;
    }
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String register2() throws ClientException, ParseException {
        String sms_BizId=null;
        sms_BizId= (String) session.getAttribute("sms_BizId");
        SendSmsResponse smsResponse=null;
//        QuerySendDetailsResponse querySendDetailsResponse = SmsUtil.querySendDetails(sms_BizId);
//        querySendDetailsResponse.getCode();
        QuerySendDetailsResponse querySendDetailsResponse = SmsUtil.querySendDetails(sms_BizId);
        System.out.println("短信明细查询接口返回数据----------------");
        System.out.println("Code=" + querySendDetailsResponse.getCode());
        System.out.println("Message=" + querySendDetailsResponse.getMessage());
        String phoneNum=null;
        String sendDateStr=null;
        int i = 0;
        for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs()) {
            System.out.println("SmsSendDetailDTO["+i+"]:");
            System.out.println("Content=" + smsSendDetailDTO.getContent());
            System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
            System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
            phoneNum=smsSendDetailDTO.getPhoneNum();
            System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
            sendDateStr=smsSendDetailDTO.getSendDate();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeLag= new Date().getTime()- (dateFormat.parse(sendDateStr).getTime());
        WechatUser wechatUser=null;
        LockUser lockUser=null;
        if(timeLag < 5*60*1000){
            if (verifyCode.equals(session.getAttribute("verifyCode"))){
                wechatUser = new WechatUser();
                wechatUser.setOpenid(openid);
                wechatUser.setCreatetime(DataUtil.fromDate24H());
                lockUser=new LockUser();
                lockUser.setPhonenumber(phoneNum);
                wechatUser.setLockUser(lockUser);
                wechatService.addSubscribe(wechatUser);
                session.setAttribute("ownerPhoneNumber",phoneNum);
            }
        }else {
            session.setAttribute("errMsg","验证码超时，请重新获取验证码。");
            return "error";
        }
        System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
        return "main";
    }
    //********************************************************************************************************
    private Map<String,Object> jsonMap;
    public Map<String, Object> getJsonMap() {
        return jsonMap;
    }

    private LoginPara loginPara=new LoginPara();
    public LoginPara getLoginPara() {
        return loginPara;
    }
    public void setLoginPara(LoginPara loginPara) {
        this.loginPara = loginPara;
    }

    private String authenticateErrMsg;
    public String getAuthenticateErrMsg() {
        return authenticateErrMsg;
    }
    public void setAuthenticateErrMsg(String authenticateErrMsg) {
        this.authenticateErrMsg = authenticateErrMsg;
    }

    private JwtAccessToken jwtAccessToken;
    /*jwtAccessToken设置了getXXX，json插件将jwtAccessToken转为json*/
    public JwtAccessToken getJwtAccessToken() {
        return jwtAccessToken;
    }
    public void setJwtAccessToken(JwtAccessToken jwtAccessToken) {
        this.jwtAccessToken = jwtAccessToken;
    }

    private String salt;
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

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
}