package com.yishu.service;

import java.util.List;

public interface IDeviceService {
    public List getUserGatewayIp(String ownerPhoneNumber);
    public List getDeviceInfo(String ownerPhoneNumber);
}
