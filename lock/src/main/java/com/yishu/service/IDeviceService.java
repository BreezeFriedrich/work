package com.yishu.service;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-03 11:01 admin
 * @since JDK1.7
 */
public interface IDeviceService {
    public List getUserGatewayIp(String ownerPhoneNumber);
    public List getDeviceInfo(String ownerPhoneNumber);
    public List getAbnormalDevice(String ownerPhoneNumber);
    public int countAbnormalDevice(String ownerPhoneNumber);
}
