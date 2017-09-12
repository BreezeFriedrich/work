/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service;

import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-12 10:21 admin
 * @since JDK1.7
 */
public interface ILockService {
    public Map hasLockAdded(String ownerPhoneNumber,String lockCode);
    public boolean addLock(String ownerPhoneNumber,String gatewayCode,String lockCode,String lockName,String lockLocation,String lockComment);
    public boolean modifyLockInfo(String ownerPhoneNumber,String lockCode,String lockName,String lockLocation,String lockComment);
    public boolean deleteLock(String ownerPhoneNumber,String lockCode);

    public String getLockIp(String ownerPhoneNumber,String lockCode);
}
