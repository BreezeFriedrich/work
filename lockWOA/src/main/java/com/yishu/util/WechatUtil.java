package com.yishu.util;

import com.yishu.pojo.AccessToken;
import net.sf.json.JSONObject;

public class WechatUtil {
    private static final String APPID="wx6eo2v3fd4af3";
    private static final String APPSECRET="c7058bjh8g97m87cf8a2a7632f800a1";
    private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 获取access_token
     * @return
     */
    public static AccessToken getAccessToken(){
        AccessToken accessToken=new AccessToken();
        String url=ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        JSONObject jsonObject=HttpComponent.doGetStr(url);
        if(null!=jsonObject){
            accessToken.setToken(jsonObject.getString("access_token"));
            accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return accessToken;
    }
}