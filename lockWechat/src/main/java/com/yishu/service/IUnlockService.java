/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service;

import com.yishu.pojo.UnlockPwds;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-08 15:40 admin
 * @since JDK1.7
 */
public interface IUnlockService {
    public List getUnlockId(String ownerPhoneNumber,String gatewayCode,String lockCode);
    public boolean authUnlockById(String ownerPhoneNumber,String gatewayCode,String lockCode,String name,String cardNumb,String dnCode,long startTime,long endTime);
    public boolean prohibitUnlockById(String ownerPhoneNumber,String lockCode,String cardNumb,String serviceNumb);
    public UnlockPwds getUnlockPwd(String ownerPhoneNumber,String gatewayCode,String lockCode);
    public boolean authUnlockByPwd(String ownerPhoneNumber,String gatewayCode,String lockCode,String password,long startTime,long endTime);
    public boolean prohibitUnlockByPwd(String ownerPhoneNumber,String gatewayCode,String lockCode,String serviceNumb);
}
