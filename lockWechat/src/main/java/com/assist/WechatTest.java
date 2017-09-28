/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.assist;

import com.yishu.domain.WechatTokenAndTicket;
import com.yishu.util.TokenSingleton;
import net.sf.json.JSONObject;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-28 15:32 admin
 * @since JDK1.7
 */
public class WechatTest {
    public static void main(String[] args){
        try {
            WechatTokenAndTicket tokenAndTicket= TokenSingleton.getInstance().getTokenAndTicket();
            System.out.println("access_token: "+tokenAndTicket.getAccess_token());
            System.out.println("有效时间: "+tokenAndTicket.getToken_expiresIn());

            String menu = JSONObject.fromObject(WechatUtil.initMenu()).toString();
            int result = WechatUtil.createMenu(tokenAndTicket.getAccess_token(),menu);
            if (0==result){
                System.out.println("创建菜单成功");
            }else {
                System.out.println("错误码: "+result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
