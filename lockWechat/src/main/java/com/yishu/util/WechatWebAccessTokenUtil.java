/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import com.yishu.domain.WechatUser;
import com.yishu.domain.WechatWebAccessToken;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

//根据code 获取 openid
@Component("wechatWebAccessTokenUtil")
public class WechatWebAccessTokenUtil {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("WechatWebAccessTokenUtil");

    private static String APPID;
    private static String APPSECRET;

    public String getAPPID() {
        return APPID;
    }

    @Value("${APPID:wx6234fc4a502ef625}")
    public void setAPPID(String APPID) {
        WechatWebAccessTokenUtil.APPID = APPID;
    }

    public String getAPPSECRET() {
        return APPSECRET;
    }

    @Value("${APPSECRET:897c9b5b60804e4c9f4609cd00dd875c}")
    public void setAPPSECRET(String APPSECRET) {
        WechatWebAccessTokenUtil.APPSECRET = APPSECRET;
    }

    public void printConfig(){
        //@Value("${}")搭配setters、component-scan 、@Component读取配置.
        LOG.info("APPID : "+APPID+", APPSECRET : "+APPSECRET);
    }

    public String getOpenidByCode(String code) {
        return getWechatWebAccessTokenByCode(code).getOpenid();
    }

    /**
     * 获取微信网页授权令牌 wechat web access_token.
     *
     * @param code
     * @return
     */
    public WechatWebAccessToken getWechatWebAccessTokenByCode(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + APPID
                + "&secret="
                + APPSECRET
                + "&code="
                + code
                + "&grant_type=authorization_code";

        LOG.info("url : "+url);
        long fetchTime=new Date().getTime();
        String jsonStr = HttpUtil.doGet(url);
        LOG.info("获取网页授权openid & access_token,微信返回 : "+jsonStr);
        try{
            jsonStr = new String(jsonStr.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        String openid = null;
        WechatWebAccessToken wechatWebAccessToken=new WechatWebAccessToken();
        try{
//            wechatWebAccessToken.setFetchtime(new Date().getTime());
            jsonObject = JSONObject.fromObject(jsonStr);
            wechatWebAccessToken.setOpenid(jsonObject.getString("openid"));
            wechatWebAccessToken.setAccess_token(jsonObject.getString("access_token"));
//            wechatWebAccessToken.setExpires_in(jsonObject.getString("expires_in"));
            wechatWebAccessToken.setExpires_in(jsonObject.getInt("expires_in"));
            wechatWebAccessToken.setRefresh_token(jsonObject.getString("refresh_token"));
            wechatWebAccessToken.setScope(jsonObject.getString("scope"));
            long deadline=fetchTime+jsonObject.getInt("expires_in")*1000;
            wechatWebAccessToken.setDeadline(deadline);
        } catch (Exception e1){
            return null;
        }
        return wechatWebAccessToken;
    }

    /**
     * 刷新网页授权的access_token.
     *
     * @param refreshToken 上一次获得的wechatWebAccessToken.getRefresh_token().
     * @return
     */
    public WechatWebAccessToken refreshWebAccessToken(String refreshToken) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="
                + APPID
                + "&grant_type=refresh_token"
                + "&refresh_token="
                + refreshToken;
        long fetchTime=new Date().getTime();
        String jsonStr = HttpUtil.doGet(url);
        try{
            jsonStr = new String(jsonStr.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        WechatWebAccessToken wechatWebAccessToken=new WechatWebAccessToken();
        try{
            jsonObject = JSONObject.fromObject(jsonStr);
            wechatWebAccessToken.setOpenid(jsonObject.getString("openid"));
            wechatWebAccessToken.setAccess_token(jsonObject.getString("access_token"));
            wechatWebAccessToken.setExpires_in(jsonObject.getInt("expires_in"));
            wechatWebAccessToken.setRefresh_token(jsonObject.getString("refresh_token"));
            wechatWebAccessToken.setScope(jsonObject.getString("scope"));
            long deadline=fetchTime+jsonObject.getInt("expires_in")*1000;
            wechatWebAccessToken.setDeadline(deadline);
        } catch (Exception e1){
            return null;
        }
        return wechatWebAccessToken;
    }

    /**
     * 拉取微信用户信息
     *
     * @param access_token
     * @param openid
     * @return
     */
    public WechatUser getWechatUserinfo(String access_token,String openid) {
        //https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + access_token
                + "&openid="
                + openid
                + "&lang=zh_CN";
        String jsonStr = HttpUtil.doGet(url);
        try{
            jsonStr = new String(jsonStr.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        WechatUser wechatUser=new WechatUser();
        try{
            jsonObject = JSONObject.fromObject(jsonStr);
            wechatUser.setOpenid(jsonObject.getString("openid"));
            wechatUser.setNickname(jsonObject.getString("nickname"));
            wechatUser.setSex(Integer.parseInt(jsonObject.getString("sex")));
            wechatUser.setCountry(jsonObject.getString("country"));
            wechatUser.setProvince(jsonObject.getString("province"));
            wechatUser.setCity(jsonObject.getString("city"));
            wechatUser.setHeadimgurl(jsonObject.getString("headimgurl"));
            wechatUser.setUnionid(jsonObject.getString("unionid"));
            //privilege	用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
            return wechatUser;
        } catch (Exception e1){
        }
        return null;
    }

    /**
     * 检验授权凭证（access_token）是否有效
     *
     * @param access_token
     * @param openid
     * @return
     */
    public boolean validWWAccessToken (String access_token,String openid) {
        // https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
        String url = "https://api.weixin.qq.com/sns/auth?access_token="
                + access_token
                + "&openid"
                + openid;
        String jsonStr = HttpUtil.doGet(url);
        try{
            jsonStr = new String(jsonStr.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        try{
            jsonObject = JSONObject.fromObject(jsonStr);
            if(0==jsonObject.getInt("errcode")){
                return true;
            }
        } catch (Exception e1){
        }
        return false;
    }
}