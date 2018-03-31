/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service;

import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-16 10:08 admin
 * @since JDK1.7
 */
public interface IAccountService {
    public boolean modifyNickname(String ownerPhoneNumber,String newName);
    public boolean modifyPassword(String ownerPhoneNumber,String newPassword);
    public Map queryAuthPassword(String ownerPhoneNumber);
//    public boolean validAuthPassword(String ownerPhoneNumber,String authPassword);
    public Map validAuthPassword(String ownerPhoneNumber,String authPassword);
    public boolean proofAuthpassword(String ownerPhoneNumber,String authPassword);
    public boolean authAuthPassword(String ownerPhoneNumber,String authPassword);
    public Map wechatLogin(String openid);
}
