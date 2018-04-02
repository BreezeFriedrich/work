/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.web;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.yishu.service.IAccountService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-31 9:55 admin
 * @since JDK1.7
 */
public class AuthpasswordInterceptor implements Interceptor {
    public AuthpasswordInterceptor() {
        LOG.info(">>>Initialization AuthpasswordInterceptor......................................");
    }

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("AuthpasswordInterceptor");
    @Autowired
    private IAccountService accountService;

    private String errMsg;
    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    private String redirectUrl;
    public String getRedirectUrl() {
        return redirectUrl;
    }
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
//        response.sendRedirect(redirectURL);
        HttpServletRequest request=ServletActionContext.getRequest();
//        HttpServletResponse response=ServletActionContext.getResponse();
        HttpSession session=request.getSession();
        if(null!=session.getAttribute("authPassword")){
            LOG.info("authPassword:"+session.getAttribute("authPassword"));
            return actionInvocation.invoke();
        }
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        Map resultMap=accountService.queryAuthPassword(ownerPhoneNumber);
        LOG.info("queryAuthPassword:result="+resultMap.get("result"));
        if(0==(int)(resultMap.get("result"))){
            //获取到授权码
            /*
            String authPaassword=(String) resultMap.get("authPassword");
            String reqAuthpassword=request.getParameter("authPassword");
            if(null!=reqAuthpassword && reqAuthpassword.equals(authPaassword)){
                return actionInvocation.invoke();
            }else {
                errMsg="没有提交授权码或授权码不正确";
            }
            */
            redirectUrl=request.getRequestURI();
            return "proofAuthpassword";
        }else if(2==(int)(resultMap.get("result"))){
            //未设置手势密码
            return "setAuthpassword";
        }else{
            errMsg="获取不到授权码";
        }
        return Action.ERROR;
    }
}
