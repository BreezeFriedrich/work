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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-12 17:22 admin
 * @since JDK1.7
 */
@Component
public class OpenIdInterceptor extends AbstractInterceptor{
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("OpenIdInterceptor");

    @Autowired
    private WechatWebAccessTokenUtil wechatWebAccessTokenUtil;

    public OpenIdInterceptor() {
        LOG.info(">>>Initialization OpenIdInterceptor......................................");
    }
    private static String APPID;
    private static String redirectURL;

    public String getAPPID() {
        return APPID;
    }

    @Value("${APPID:wx6234fc4a502ef625}")
    public void setAPPID(String APPID) {
        OpenIdInterceptor.APPID = APPID;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    @Value("${redirectURL:https://lockwx.manxing1798.com/lockWechat/login/wxLogin.action}")
    public void setRedirectURL(String redirectURL) {
        OpenIdInterceptor.redirectURL = redirectURL;
    }

    /**
     * 获得openid.
     *
     */
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        HttpServletRequest request=ServletActionContext.getRequest();
        HttpServletResponse response=ServletActionContext.getResponse();
        HttpSession session=request.getSession();
        String code = request.getParameter("code");
//        LOG.info("网页授权code: "+code);
        String openid = (String) session.getAttribute("OPENID");
//        LOG.info("网页授权openid: "+openid);
        LOG.info("网页授权code: "+code+", 网页授权openid: "+openid);
        if(null==openid){
            LOG.info("网页授权openid: "+openid);
            if(null==code){
                LOG.info("网页授权code: "+code);
                response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="
                        + redirectURL + "&response_type=code&scope=snsapi_base#wechat_redirect");
            }else{
                LOG.info("网页授权code: "+code);
                WechatWebAccessToken webAccessToken=wechatWebAccessTokenUtil.getWechatWebAccessTokenByCode(code);
                openid=webAccessToken.getOpenid();
                session.setAttribute("OPENID",openid);
//                String accessToken=webAccessToken.getAccess_token();
//                return actionInvocation.invoke();
                response.sendRedirect(redirectURL);
            }
        }
        LOG.info("网页授权code: "+code+", 网页授权openid: "+openid);
        return actionInvocation.invoke();
    }
    /*
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        //使用struts2操作session,获取session.
        //ActionContext ctx = invocation.getInvocationContext();
        //Map<String,Object> session = ctx.getSession();
        //String openid = (String) session.get("OPENID");

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
//        String redirectURL= "https://lockwx.manxing1798.com/lockWechat/login/wxLogin.action";
        String urlFrom = request.getScheme() + "://" + request.getServerName() + request.getRequestURI();
        LOG.info("网页授权请求发起url: "+urlFrom);
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        LOG.info("网页授权code: "+code);
        String openid = (String) session.getAttribute("OPENID");
        LOG.info("网页授权openid: "+openid);

        if (null==openid) {
            if (StringUtil.bIsNotNull(code)) {
                //有code,无openid,获取openid
//                WechatWebAccessTokenUtil wechatWebAccessTokenUtil = new WechatWebAccessTokenUtil();
                LOG.info("网页授权code: "+code);
                WechatWebAccessToken wechatWebAccessToken=wechatWebAccessTokenUtil.getWechatWebAccessTokenByCode(code);
                String openId=wechatWebAccessToken.getOpenid();
                String access_token=wechatWebAccessToken.getAccess_token();
                if (StringUtil.bIsNotNull(openId)) {
                    LOG.info("网页授权openId: "+openId);
                    session.setAttribute("OPENID",openId);
                    openid=openId;
                }else {
                    throw new Exception(this.getClass()+":已获取code为"+code+",但是获取的openId为空");
                }
            }else {
                //code为空,获取code
                response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="
                        + redirectURL + "&response_type=code&scope=snsapi_base#wechat_redirect");
            }
        }

        LOG.info("网页授权code: "+code);
        LOG.info("网页授权openid: "+openid);
        return actionInvocation.invoke();
    }
    */

//    public void printConfig(){
//        LOG.info("APPID:"+APPID+",redirectURL:"+redirectURL);
//    }
}