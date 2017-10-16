/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import com.yishu.domain.WechatTokenAndTicket;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;

//获取token的
@Component("getToken")
public class GetToken {
    @Value("${APPID}")
    private static String APPID;// 微信公众号下的AppID
    @Value("${APPSECRET}")
    private static String APPSECRET;// 微信公众号下的secret

    public static String TOKEN="WechatTokenAndTicket";

    private static String gettoken() {
        JSONObject jsonObject = null;
        String access_token = null;
        String json = null;
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;

        json = HttpUtil.doGet(url);
        try {
            json = new String(json.getBytes("ISO-8859-1"), "utf-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        jsonObject = JSONObject.fromObject(json);
        access_token = jsonObject.getString("access_token");
        System.out.println("access_token==============" + access_token);
        return access_token;
    }

    //
    public static String getAccessToken() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext.getServletContext();
        WechatTokenAndTicket wat=(WechatTokenAndTicket) servletContext.getAttribute(TOKEN);
        String access_token=null;
        long create_time=0;

        if(wat!=null){
            access_token=wat.getAccess_token();
            create_time=wat.getToken_ctime();
        }else{
            wat=new WechatTokenAndTicket();
        }

        if (access_token == null) {
            access_token = GetToken.gettoken();
            create_time = System.currentTimeMillis();
            wat.setAccess_token(access_token);
            wat.setToken_ctime(create_time);
            servletContext.setAttribute(TOKEN, wat);
            return access_token;
        }

        // 判断是否有效(间隔不能超过7200s,注意服务器主机的系统时间不能错)
        if ((System.currentTimeMillis() - create_time)/1000 < 5500) {
            return access_token;
        } else {
            access_token = GetToken.gettoken();
            create_time = System.currentTimeMillis();
            wat.setAccess_token(access_token);
            wat.setToken_ctime(create_time);
            servletContext.setAttribute(TOKEN, wat);
            return access_token;
        }
    }

}
