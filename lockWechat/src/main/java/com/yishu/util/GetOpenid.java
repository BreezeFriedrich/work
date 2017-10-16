/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

//根据code 获取 openid
@Component("getOpenid")
public class GetOpenid
{
    @Value("${APPID}")
    private String APPID;// 微信公众号下的AppID
    @Value("${APPSECRET}")
    private String SECRET;// 微信公众号下的secret

    public void printConfig(){
        //@Value("${}")搭配component-scan 、@Component读取配置.
        System.out.println("APPSECRET : "+SECRET);
    }

    public String getOpenidByCode(String code)
    {
        String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + APPID
                + "&secret="
                + SECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";

        String json = HttpUtil.doGet(get_access_token_url);
        try{
            json = new String(json.getBytes("ISO-8859-1"), "utf-8");
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        JSONObject js = null;
        String openid = null;
        try{
            js = JSONObject.fromObject(json);
            openid = js.getString("openid");
        }
        catch (Exception e1){
            return null;
        }

        return openid;
    }
}