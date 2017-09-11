/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service;

import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-11 16:33 admin
 * @since JDK1.7
 */
public interface IGatewayService {
    public Map getGatewayIp(String ownerPhoneNumber,String gatewayCode);
    public Map hasGatewayAdded(String ownerPhoneNumber,String gatewayCode);
    public int isCorrectGatewayVerificationCode(String ownerPhoneNumber,String gatewayCode,String opCode);
    public boolean addGateway(String ownerPhoneNumber,String gatewayCode,String gatewayName,String gatewayLocation,String gatewayComment,String opCode);
    public boolean modifyGatewayInfo(String ownerPhoneNumber,String gatewayCode,String gatewayName,String gatewayLocation,String gatewayComment);
    public boolean deleteGateway(String ownerPhoneNumber,String gatewayCode);
}
