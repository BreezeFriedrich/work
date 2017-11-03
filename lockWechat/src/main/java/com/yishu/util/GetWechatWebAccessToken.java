/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import com.yishu.domain.WechatWebAccessToken;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

//根据code 获取 openid
@Component("getWechatWebAccessToken")
public class GetWechatWebAccessToken
{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("GetWechatWebAccessToken");

    @Value("${APPID}")
    private String APPID;// 微信公众号下的AppID
    @Value("${APPSECRET}")
    private String SECRET;// 微信公众号下的secret

    public void printConfig(){
        //@Value("${}")搭配component-scan 、@Component读取配置.
        System.out.println("APPSECRET : "+SECRET);
    }

    public String getOpenidByCode(String code) {
        return getWechatWebAccessTokenByCode(code).getOpenid();
    }

    public WechatWebAccessToken getWechatWebAccessTokenByCode(String code) {
//        logger.error("APPID : "+APPID);
        String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + APPID
                + "&secret="
                + SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";

        String jsonStr = HttpUtil.doGet(get_access_token_url);
        try{
            jsonStr = new String(jsonStr.getBytes("ISO-8859-1"), "utf-8");
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
//        logger.error(jsonStr);
        JSONObject jsonObject = null;
        String openid = null;
        WechatWebAccessToken wechatWebAccessToken=new WechatWebAccessToken();
        try{
            jsonObject = JSONObject.fromObject(jsonStr);
            wechatWebAccessToken.setOpenid(jsonObject.getString("openid"));
            wechatWebAccessToken.setAccess_token(jsonObject.getString("access_token"));
            wechatWebAccessToken.setExpires_in(jsonObject.getString("expires_in"));
            wechatWebAccessToken.setRefresh_token(jsonObject.getString("refresh_token"));
            wechatWebAccessToken.setScope(jsonObject.getString("scope"));
        }
        catch (Exception e1){
            return null;
        }
        return wechatWebAccessToken;
    }
}