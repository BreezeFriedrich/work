/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.web;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-31 14:59 admin
 * @since JDK1.7
 */
public class OwnerphonenumberInterceptor implements Interceptor {
    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        HttpServletRequest request= ServletActionContext.getRequest();
//        HttpServletResponse response=ServletActionContext.getResponse();
        HttpSession session=request.getSession();
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        if(null!=ownerPhoneNumber){
            return actionInvocation.invoke();
        }
        return Action.LOGIN;
    }
}
