/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service;

import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-23 9:34 admin
 * @since JDK1.7
 */
public interface ISMSService {
    public static int EXPIRE_TIME=1000*60*5;
    public Map sendVerifyCode(String phoneNumber);
}
