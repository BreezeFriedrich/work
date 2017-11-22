/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service;

import com.yishu.domain.WechatUser;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-12 17:27 admin
 * @since JDK1.7
 */
public interface IWechatService {
//    public static String SESSION_USER="USERSESSION";
    public static String OPENID="OPENID";
    public static String ACCESS_TOKEN="ACCESS_TOKEN";

    public int addSubscribe(WechatUser wechatUser,String phonenumber);

    public int updateByOpenid(WechatUser wechatUser);

    public int UnSubscribe(WechatUser wechatUser);

    public WechatUser findWechatUserByopenid(String openid);
}
