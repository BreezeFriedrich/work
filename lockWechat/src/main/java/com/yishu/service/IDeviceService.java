package com.yishu.service;

import com.yishu.pojo.Device;

import java.util.List;

public interface IDeviceService {
    public List getUserGatewayIp(String ownerPhoneNumber);
    public List<Device> getDeviceInfo(String ownerPhoneNumber);
    public List getAbnormalDevice(String ownerPhoneNumber);
    public int countAbnormalDevice(String ownerPhoneNumber);
}
