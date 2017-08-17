package com.yishu.util;

import com.yishu.pojo.WechatTokenAndTicket;
import net.sf.json.JSONObject;

import java.util.Date;

/**
 * 管理 access_token 和 jsapi_token
 * 获取 access_token 和 jsapi_token 方式: WechatTokenAndTicket tokenAndTicket = TokenSingleton.getTokenAndTicket();
 *
 * @author admin
 * @since 2017-08-17
 */
public class TokenSingleton {

    private TokenSingleton() {
    }
    private static TokenSingleton tokenSingleton=null;
    public static TokenSingleton getInstance(){
        if(null==tokenSingleton){
            tokenSingleton=new TokenSingleton();
        }
        return tokenSingleton;
    }

//
    private static final String APPID="wx6eo2v3fd4af3";
    private static final String APPSECRET="c7058bjh8g97m87cf8a2a7632f800a1";
    private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String JSAPI_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    private static WechatTokenAndTicket tokenAndTicket=new WechatTokenAndTicket();
    /**
     * 获取access_token
     * @return
     */
    public static void setAccessToken(){
        String url=ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        JSONObject jsonObject=HttpComponent.doGetStr(url);
        if(null!=jsonObject){
            tokenAndTicket.setAccess_token(jsonObject.getString("access_token"));
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
        }
    }

    public WechatTokenAndTicket getTokenAndTicket(){
        Long now=new Date().getTime();
        if( null!=tokenAndTicket.getAccess_token() && now-tokenAndTicket.getToken_ctime()-60000 < tokenAndTicket.getToken_expiresIn() ){
            //access_token 未超时，依然有效
        }else {
            //access_token 超时,重新获取
            setAccessToken();
            setJsapiToken();
        }
        return tokenAndTicket;
    }
}
