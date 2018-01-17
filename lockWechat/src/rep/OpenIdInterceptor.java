/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.web;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.yishu.domain.WechatWebAccessToken;
import com.yishu.service.IWechatService;
import com.yishu.util.DataUtil;
import com.yishu.util.WechatWebAccessTokenUtil;
import com.yishu.util.StringUtil;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;
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
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("OpenIdInterceptor");

    public OpenIdInterceptor() {
        System.out.println(">>>Initialization OpenIdInterceptor......................................");
    }

    @Autowired
    IWechatService wechatService;
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        String url= "https://lockwx.manxing1798.com/lockWechat/login/wxLogin.action";
//        String url = request.getScheme() + "://" + request.getServerName() + request.getRequestURI();
        logger.error("网页授权请求发起url: "+url);
        // String actionName = actionInvocation.getInvocationContext().getName();
        String stime = DataUtil.fromDate24H();
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        logger.error("网页授权code: "+code);
        String openid = (String) session.getAttribute(IWechatService.OPENID);
        String ownerPhoneNumber=null;

        /*参考源
        if (u_openid == null) {
            if (StringUtil.bIsNotNull(code)) {
                WechatWebAccessTokenUtil go = new WechatWebAccessTokenUtil();
                // String openId=CommonUtil.getOpenId(code);
                String openId = go.getOpenidByCode(code);
                if (openId != null && !openId.equals("")) {
                    WechatUser user = wechatService.findWechatUserByopenid(openId);
                    if (user != null) {
                        // 将登录时间录入
                        // userService.updateUserLogintime(stime,user.getOpenid());
                        session.setAttribute(IWechatService.SESSION_USER, openId);
                    } else {
                        user = new WechatUser();
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

        /*
        if (openid == null) {
            if (StringUtil.bIsNotNull(code)) {
                logger.error("网页授权code: "+code);
                WechatWebAccessTokenUtil go = new WechatWebAccessTokenUtil();
                String openId = go.getOpenidByCode(code);
                if (StringUtil.bIsNotNull(openId)) {
                    logger.error("网页授权openId: "+openId);
                    session.setAttribute(IWechatService.OPENID, openId);

                    WechatUser user = wechatService.findWechatUserByopenid(openId);
                    if (user != null) {
                        // 将登录时间录入
                        // userService.updateUserLogintime(stime,user.getOpenid());
                        session.setAttribute(IWechatService.OPENID, openId);
                    } else {
                        user = new WechatUser();
                        user.setOpenid(openId);
                        user.setCreatetime(stime);
                        wechatService.addSubscribe(user);
                        session.setAttribute(IWechatService.OPENID, openId);
                        System.out.println("--------------------生成用户3");
                    }

                } else {
                    //携带code返回redirect_uri
                    //网页授权回调域名url写顶级域名即可不要带上wwww等前缀.redirect_uri参数将会自动urlEncode.
                    response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6234fc4a502ef625&redirect_uri="
                            + url + "&response_type=code&scope=snsapi_base#wechat_redirect");
                }
            } else {
                //获取code
                response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6234fc4a502ef625&redirect_uri="
                        + url + "&response_type=code&scope=snsapi_base#wechat_redirect");
            }
        }
        logger.error("网页授权code: "+code);
        logger.error("网页授权openid: "+openid);
        return actionInvocation.invoke();
        */


        String openId = null;
        if (null==openid) {
            if (StringUtil.bIsNotNull(code)) {
                //有code,无openid,获取openid
//                logger.error("网页授权code: "+code);
                WechatWebAccessTokenUtil go = new WechatWebAccessTokenUtil();
                WechatWebAccessToken wechatWebAccessToken=go.getWechatWebAccessTokenByCode(code);
                openId=wechatWebAccessToken.getOpenid();
                String access_token=wechatWebAccessToken.getAccess_token();
                if (StringUtil.bIsNotNull(openId)) {
                    logger.error("网页授权openId: "+openId);
                    session.setAttribute(IWechatService.OPENID,openId);
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

        logger.error("网页授权code: "+code);
        logger.error("网页授权openid: "+openid);
        return actionInvocation.invoke();
    }
}