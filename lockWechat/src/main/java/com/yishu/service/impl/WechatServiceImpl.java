/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.yishu.dao.WechatDao;
import com.yishu.domain.WechatUser;
import com.yishu.service.IWechatService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-12 17:27 admin
 * @since JDK1.7
 */
@Service("wechatService")
public class WechatServiceImpl implements IWechatService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("WechatServiceImpl");
    @Autowired
    private WechatDao wechatDao;

    @Override
    public int addSubscribe(WechatUser wechatUser) {
        return wechatDao.addSubscribe(wechatUser);
    }

    @Override
    public int UnSubscribe(WechatUser wechatUser) {
        return wechatDao.unSubscribe(wechatUser);
    }

    @Override
    public WechatUser findWechatUserByopenid(String openid) {
        return wechatDao.findWechatUserByopenid(openid);
    }

    @Override
    public int UnSubscribe2(WechatUser wechatUser) {
        return wechatDao.unSubscribe2(wechatUser);
    }
}