/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.dao;

import com.yishu.domain.WechatUser;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-13 18:14 admin
 * @since JDK1.7
 */
public interface WechatUserDao {
    /*
    public int addSubscribe(WechatUser wechatUser);
    public int unSubscribe(WechatUser wechatUser);
    public int unSubscribe2(WechatUser wechatUser);
    */

    public Integer add(WechatUser wechatUser);
    public Integer updateByOpenid(WechatUser wechatUser);
    public Integer deleteByOpenid(String openid);
    public WechatUser findWechatUserByOpenid(String openid);
}
