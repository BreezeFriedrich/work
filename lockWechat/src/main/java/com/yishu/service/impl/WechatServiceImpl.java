/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.yishu.domain.User;
import com.yishu.service.IWechatService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-12 17:27 admin
 * @since JDK1.7
 */
@Service("wechatService")
public class WechatServiceImpl implements IWechatService {
    @Override
    public int addSubscribe(User user) {
        return 0;
    }

    @Override
    public int UnSubscribe(User user) {
        return 0;
    }

    @Override
    public User findUserByopenid(String openid) {
        return null;
    }

    @Override
    public int UnSubscribe2(User user) {
        return 0;
    }
}