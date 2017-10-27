/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

//根据code 获取 openid
@Component("getOpenid")
public class GetOpenid
{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("GetOpenid");

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
        logger.error("APPID : "+APPID);
        logger.error("APPSECRET : "+SECRET);
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
        logger.error(json);
        JSONObject js = null;
        String openid = null;
        try{
            js = JSONObject.fromObject("GetOpenid.getOpenidByCode(code) 微信返回的json: "+json);
            openid = js.getString("openid");
        }
        catch (Exception e1){
            return null;
        }

        logger.error("GetOpenid.getOpenidByCode(code) 获取的openid: "+openid);
        return openid;
    }
}