/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.dao;

import com.yishu.pojo.LockUser;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-10 10:18 admin
 * @since JDK1.7
 */
public interface LockUserDao {
    public List<LockUser> listAllByPhone(String phonenumber);
    public Integer add(LockUser lockUser);
    public Integer updateByPhonenumber(LockUser lockUser);
    public LockUser findLockUserById(int id);
}
