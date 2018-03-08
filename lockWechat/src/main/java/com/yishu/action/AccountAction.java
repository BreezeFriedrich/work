/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.action;

import com.yishu.domain.WechatUser;
import com.yishu.service.IAccountService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-16 11:12 admin
 * @since JDK1.7
 */
public class AccountAction {
    public AccountAction() {
        logger.info(">>>Initialization AccountAction......................................");
    }
    private org.slf4j.Logger logger= LoggerFactory.getLogger(AccountAction.class);

    @Autowired
    private IAccountService accountService;

    /**
     * Object jsonResult——返回的JSON格式的Model
     */
    private Object jsonResult;
    public Object getJsonResult() {
        return jsonResult;
    }

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
//    Map<String,Object> sessionMap=ActionContext.getContext().getSession();

    private String openid;
    private String ownerPhoneNumber;
    private String ownerPassword;
    private String newName;
    private String newPassword;
    private String gesturePassword;
    private String newGesturePassword;

    public String getNewName() {
        return newName;
    }
    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getGesturePassword() {
        return gesturePassword;
    }
    public void setGesturePassword(String gesturePassword) {
        this.gesturePassword = gesturePassword;
    }

    public String getNewGesturePassword() {
        return newGesturePassword;
    }
    public void setNewGesturePassword(String newGesturePassword) {
        this.newGesturePassword = newGesturePassword;
    }

    public String modifyNickname(){
        logger.info("-->>-- account/modifyNickname.action -->>--");
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        boolean resultBoolean=accountService.modifyNickname(ownerPhoneNumber,newName);
        jsonResult=resultBoolean;
        return "json";
    }

    /**
     * 修改用户登录密码(App登录、WEB登录所用)
     * 1.判断session 中 openid. 2.用session中openid进行微信登录. 3.若登录成功则进行修改用户登录密码.
     *
     */
    public String modifyPassword(){
        logger.info("-->>-- account/modifyPassword.action -->>--");
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        boolean resultBoolean;
        if (null!=session.getAttribute("OPENID")){
            openid= (String) session.getAttribute("OPENID");
            Map loginResultMap=accountService.wechatLogin(openid);
            if(0 == (int) loginResultMap.get("result")){
                resultBoolean=accountService.modifyPassword(ownerPhoneNumber,newPassword);
                jsonResult=resultBoolean;
                return "json";
            }
        }
        jsonResult=false;
        return "json";
    }

    /**
     * param gesturePassword 新授权手势密码.
     * @return
     */
    /*
    public String authGesturePassword(){
        logger.info("-->>-- account/authGesturePassword.action -->>--");
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        boolean resultBoolean;
        boolean isValidGesturePwd=accountService.validGesturePassword(ownerPhoneNumber,gesturePassword);
        if (isValidGesturePwd){
            resultBoolean=accountService.authGesturePassword(ownerPhoneNumber,newGesturePassword);
        }else {
            resultBoolean=false;
        }
        jsonResult=resultBoolean;
        return "json";
    }
    */
    public String authGesturePassword(){
        logger.info("-->>-- account/authGesturePassword.action -->>--");
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        Map resultMap=new HashMap();
        Map validateMap=accountService.validGesturePassword(ownerPhoneNumber,gesturePassword);
        if(0 == (int) validateMap.get("result")){
            boolean authBoolean=accountService.authGesturePassword(ownerPhoneNumber,newGesturePassword);
            if (authBoolean){
                resultMap.put("result",0);
                resultMap.put("msg","修改授权码成功");
            }else {
                resultMap.put("result",1);
                resultMap.put("msg","修改授权码失败");
            }
        }else {
            resultMap=validateMap;
        }
        jsonResult=resultMap;
        return "json";
    }

    public String queryGesturePassword(){
        logger.info("-->>-- account/queryGesturePassword.action -->>--");
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        Map resultMap=accountService.queryGesturePassword(ownerPhoneNumber);
        jsonResult=resultMap;
        return "json";
    }

    public String validGesturePassword(){
        logger.info("-->>-- account/validGesturePassword.action -->>--");
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        Map resultMap=accountService.validGesturePassword(ownerPhoneNumber,gesturePassword);
        jsonResult=resultMap;
        return "json";
    }

    public String wechatLogin(){
        logger.info("-->>-- account/wechatLogin.action -->>--");
        if ("".equals(openid)||null==openid){
            openid= (String) session.getAttribute("OPENID");
        }
        Map resultMap=accountService.wechatLogin(openid);
        jsonResult=resultMap;
        return "json";
    }

    public String getUserFromSession(){
        logger.info("-->>-- account/getUserFromSession.action -->>--");
        openid= (String) session.getAttribute("OPENID");
        ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        ownerPassword=(String) session.getAttribute("ownerPassword");
        WechatUser wechatUser=new WechatUser();
        wechatUser.setOpenid(openid);
        wechatUser.setPhonenumber(ownerPhoneNumber);
        Map resultMap=new HashMap();
        resultMap.put("wechatUser",wechatUser);
        jsonResult=resultMap;
        return "json";
    }
}
