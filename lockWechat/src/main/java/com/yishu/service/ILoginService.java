/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service;

import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-21 13:36 admin
 * @since JDK1.7
 */
public interface ILoginService {
    public Map openidExist(String openid);
    public int bindOpenidToPhone(String openid,String ownerPhoneNumber,String ownerPassword);
    public boolean register(String ownerName,String ownerPhoneNumber,String ownerPassword,String openid);
//    public User getUserByPhoneAndOpenid();
//    public boolean saveUser(User user);
}
