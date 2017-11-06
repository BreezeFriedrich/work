/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.assist;

import com.yishu.domain.WechatUser;
import com.yishu.domain.WechatWebAccessToken;
import com.yishu.service.IWechatService;
import com.yishu.util.GetWechatWebAccessToken;
import com.yishu.util.StringUtil;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-19 9:57 admin
 * @since JDK1.7
 */
public class Test {
    public static void main(String[] args){
        if (StringUtil.bIsNotNull(code)) {
            System.out.println("HI");
        }
            //有code,无openid,获取openid
//                logger.error("网页授权code: "+code);
            GetWechatWebAccessToken go = new GetWechatWebAccessToken();
            WechatWebAccessToken wechatWebAccessToken=go.getWechatWebAccessTokenByCode(code);
            String openId=wechatWebAccessToken.getOpenid();
            String access_token=wechatWebAccessToken.getAccess_token();
            if (StringUtil.bIsNotNull(openId)) {
//                    logger.error("网页授权openId: "+openId);
                session.setAttribute(IWechatService.OPENID, openId);

                WechatUser wechatUser = wechatService.findWechatUserByopenid(openId);
                if (wechatUser != null) {
                    // 将登录时间录入
                    // userService.updateUserLogintime(stime,wechatUser.getOpenid());
                    ownerPhoneNumber=wechatUser.getLockUser().getOwnername();
                } else {
                    wechatUser = new WechatUser();
                    wechatUser.setOpenid(openId);
                    wechatUser.setCreatetime(stime);
                    wechatService.addSubscribe(wechatUser);
//                        System.out.println("--------------------生成用户3");
                }
            }

        System.out.println("HI");
    }
}
