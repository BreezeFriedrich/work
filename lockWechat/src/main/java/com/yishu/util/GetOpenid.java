/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;

//根据code 获取 openid
public class GetOpenid
{
    private static String APPID = "wx6234fc4a502ef625";// 微信公众号下的AppID
    private static String SECRET = "897c9b5b60804e4c9f4609cd00dd875c";// 微信公众号下的secret

    public static String getOpenid(String code)
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