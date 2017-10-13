/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.web;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yishu.domain.User;
import com.yishu.service.IWechatService;
import com.yishu.util.DataUtil;
import com.yishu.util.GetOpenid;
import com.yishu.util.StringUtil;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-12 17:22 admin
 * @since JDK1.7
 */
public class OpenIdInterceptor extends AbstractInterceptor{
    @Autowired
    IWechatService wechatService;
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String url = request.getScheme() + "://" + request.getServerName() + request.getRequestURI();
        // String actionName =
        // actionInvocation.getInvocationContext().getName();
        String stime = DataUtil.fromDate24H();
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        String u_openid = (String) session.getAttribute(IWechatService.SESSION_USER);
/*
        if (u_openid == null) {
            if (StringUtil.bIsNotNull(code)) {
                GetOpenid go = new GetOpenid();
                // String openId=CommonUtil.getOpenId(code);
                String openId = go.getOpenid(code);
                if (openId != null && !openId.equals("")) {
                    User user = wechatService.findUserByopenid(openId);
                    if (user != null) {
                        // 将登录时间录入
                        // userService.updateUserLogintime(stime,user.getOpenid());
                        session.setAttribute(IWechatService.SESSION_USER, openId);
                    } else {
                        user = new User();
                        user.setOpenid(openId);
                        user.setCreatetime(stime);
                        wechatService.addSubscribe(user);
                        session.setAttribute(IWechatService.SESSION_USER, openId);
                        System.out.println("--------------------生成用户3");
                    }
                    return actionInvocation.invoke();
                } else {
                    response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6234fc4a502ef625&redirect_uri="
                            + url + "&response_type=code&scope=snsapi_base#wechat_redirect");
                    // return null;
                    return actionInvocation.invoke();
                }
            } else {
                // 无公众号测试 暂时屏蔽
                response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6234fc4a502ef625&redirect_uri="
                        + url + "&response_type=code&scope=snsapi_base#wechat_redirect");
                // return null;
                return actionInvocation.invoke();
            }
        } else {
            return actionInvocation.invoke();
        }
        */

        if (u_openid == null) {
            if (StringUtil.bIsNotNull(code)) {
                GetOpenid go = new GetOpenid();
                // String openId=CommonUtil.getOpenId(code);
                String openId = go.getOpenid(code);
                if (openId != null && !openId.equals("")) {
                    User user = wechatService.findUserByopenid(openId);
                    if (user != null) {
                        // 将登录时间录入
                        // userService.updateUserLogintime(stime,user.getOpenid());
                        session.setAttribute(IWechatService.SESSION_USER, openId);
                    } else {
                        user = new User();
                        user.setOpenid(openId);
                        user.setCreatetime(stime);
                        wechatService.addSubscribe(user);
                        session.setAttribute(IWechatService.SESSION_USER, openId);
                        System.out.println("--------------------生成用户3");
                    }
                } else {
                    response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6234fc4a502ef625&redirect_uri="
                            + url + "&response_type=code&scope=snsapi_base#wechat_redirect");
                }
            } else {
                // 无公众号测试 暂时屏蔽
                response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6234fc4a502ef625&redirect_uri="
                        + url + "&response_type=code&scope=snsapi_base#wechat_redirect");
            }
        }
        return actionInvocation.invoke();
    }
}