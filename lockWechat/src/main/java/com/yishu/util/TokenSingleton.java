/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import com.yishu.domain.WechatApiTokenAndTicket;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * 管理 access_token 和 jsapi_token
 * 获取 access_token 和 jsapi_token 方式: WechatApiTokenAndTicket tokenAndTicket = TokenSingleton.getTokenAndTicket();
 *
 * @author admin
 * @since 2017-08-17
 */
public class TokenSingleton {
    private TokenSingleton() {}
    private static TokenSingleton tokenSingleton=null;
    //单例-双重检查锁
    public static TokenSingleton getSingleton(){
        if(null==tokenSingleton){
            synchronized (TokenSingleton.class){
                if (null==tokenSingleton){
                    tokenSingleton=new TokenSingleton();
                }
            }
        }
        return tokenSingleton;
    }

//
    private static final String APPID="wxb7b8f059e2522f3b";
    private static final String APPSECRET="3db80f620e8dcfebd920f859bf1a7302";
    private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String JSAPI_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    private static WechatApiTokenAndTicket tokenAndTicket=new WechatApiTokenAndTicket();
    /**
     * 获取access_token
     * @return
     */
    public static void setAccessToken(){
        String url=ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        JSONObject jsonObject=HttpComponent.doGetStr(url);
        System.out.println("从微信服务器获取access_token,返回的操作结果: "+jsonObject);
        if(null!=jsonObject){
            tokenAndTicket.setAccess_token(jsonObject.getString("access_token"));
            tokenAndTicket.setToken_ctime(new Date().getTime());
            tokenAndTicket.setToken_expiresIn(jsonObject.getLong("expires_in"));
        }
    }

    /**
     * 从微信服务器获取js api token之前必须先获取access_token
     */
    public static void setJsapiToken(){
        String url=JSAPI_TOKEN_URL.replace("ACCESS_TOKEN", tokenAndTicket.getAccess_token());
        JSONObject jsonObject=HttpComponent.doGetStr(url);
        if(null!=jsonObject){
            tokenAndTicket.setJsapi_ticket(jsonObject.getString("ticket"));
            tokenAndTicket.setTicket_ctime(new Date().getTime());
        }
    }

    public WechatApiTokenAndTicket getTokenAndTicket(){
        Long now=new Date().getTime();
        if(null != tokenAndTicket.getAccess_token() && now-tokenAndTicket.getToken_ctime() < tokenAndTicket.getToken_expiresIn()*1000/2 ){
            //access_token 未超时，依然有效
            System.out.println("!!!access_token 未超时，依然有效");
        }else {
            System.out.println("access_token 超时，重新获取");
            //access_token 超时,重新获取
            setAccessToken();
            setJsapiToken();
        }
        return tokenAndTicket;
    }
}