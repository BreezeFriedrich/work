/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.assist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.domain.WechatTokenAndTicket;
import com.yishu.pojo.OperationalError;
import com.yishu.util.HttpUtil;
import com.yishu.util.TokenSingleton;
import net.sf.json.JSONObject;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-28 15:32 admin
 * @since JDK1.7
 */
public class WechatTest {
    public static void main(String[] args){
        //创建微信公众号自定义菜单(个人订阅号不能通过接口创建自定义菜单,可通过后台创建也可升级订阅号为企业服务号)
        try {
            WechatTokenAndTicket tokenAndTicket= TokenSingleton.getSingleton().getTokenAndTicket();
            System.out.println("access_token: "+tokenAndTicket.getAccess_token());
            System.out.println("有效时间: "+tokenAndTicket.getToken_expiresIn());

            ObjectMapper objectMapper=new ObjectMapper();
            String menu=objectMapper.writeValueAsString(WechatUtil.initMenu());
//            String menu = JSONObject.fromObject(WechatUtil.initMenu()).toString();
            OperationalError error = WechatUtil.createMenu(tokenAndTicket.getAccess_token(),menu);
            int errorCode=error.getErrcode();
            if (0==errorCode){
                System.out.println("创建菜单成功");
            }else {
                System.out.println("错误码: "+errorCode);
                System.out.println("错误提示: "+error.getErrmsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //通过code换取网页授权access_token及用户标识openid. 参考https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        url.replace("APPID","").replace("SECRET","");
        String str= HttpUtil.doGet(url);
        System.out.println(str);
    }
}
