package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.entities.Parameterizable;
import com.yishu.service.ILoginService;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            ownerName= (String) map.get("ownerName");
            if(null!=ownerName){
                session.setAttribute("ownerName",ownerName);
            }
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