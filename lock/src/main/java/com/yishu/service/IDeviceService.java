package com.yishu.service;

import com.yishu.pojo.Device;
import com.yishu.pojo.GatewayLock;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-03 11:01 admin
 * @since JDK1.7
 */
public interface IDeviceService {
    public List getUserGatewayIp(String ownerPhoneNumber);
    public List getDeviceInfo(String ownerPhoneNumber);
    public Map getGatewaysAndLocks(String ownerPhoneNumber);
    public List getAbnormalDevice(String ownerPhoneNumber);
    public int countAbnormalDevice(String ownerPhoneNumber);
    public List<GatewayLock> convertDeviceToGatewayLock(Device device);
}
