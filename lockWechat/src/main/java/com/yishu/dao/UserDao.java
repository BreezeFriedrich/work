/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.dao;

import com.yishu.pojo.User;

public interface UserDao {
    User findUserByName(String username);
}