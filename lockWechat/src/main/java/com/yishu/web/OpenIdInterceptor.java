/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.web;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yishu.domain.WechatWebAccessToken;
import com.yishu.util.WechatWebAccessTokenUtil;
import com.yishu.util.StringUtil;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-12 17:22 admin
 * @since JDK1.7
 */
public class OpenIdInterceptor extends AbstractInterceptor{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("OpenIdInterceptor");

    public OpenIdInterceptor() {
//        System.out.println(">>>Initialization OpenIdInterceptor......................................");
        logger.info(">>>Initialization OpenIdInterceptor......................................");
    }

    /**
     * 获得openid.
     *
     */
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        /*使用struts2操作session,获取session.
        ActionContext ctx = invocation.getInvocationContext();
        Map<String,Object> session = ctx.getSession();
        String openid = (String) session.get("OPENID");
         */
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String url= "https://lockwx.manxing1798.com/lockWechat/account/wxLogin.action";
        String urlFrom = request.getScheme() + "://" + request.getServerName() + request.getRequestURI();
//        logger.info("网页授权请求发起url: "+urlFrom);
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
//        logger.info("网页授权code: "+code);
        String openid = (String) session.getAttribute("OPENID");

        if (null==openid) {
            if (StringUtil.bIsNotNull(code)) {
                //有code,无openid,获取openid
                WechatWebAccessTokenUtil go = new WechatWebAccessTokenUtil();
                WechatWebAccessToken wechatWebAccessToken=go.getWechatWebAccessTokenByCode(code);
                String openId=wechatWebAccessToken.getOpenid();
                String access_token=wechatWebAccessToken.getAccess_token();
                if (StringUtil.bIsNotNull(openId)) {
//                    logger.info("网页授权openId: "+openId);
                    session.setAttribute("OPENID",openId);
                    openid=openId;
                }else {
                    throw new Exception(this.getClass()+":已获取code为"+code+",但是获取的openId为空");
                }
            }else {
                //code为空,获取code
                response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6234fc4a502ef625&redirect_uri="
                        + url + "&response_type=code&scope=snsapi_base#wechat_redirect");
            }
        }

//        logger.info("网页授权code: "+code);
//        logger.info("网页授权openid: "+openid);
        return actionInvocation.invoke();
    }
}