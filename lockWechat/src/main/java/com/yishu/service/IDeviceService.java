package com.yishu.service;

import java.util.List;

public interface IDeviceService {
    public List getAllGatewayIp(String ownerPhoneNumber);
    public String getDeviceInfo(String ownerPhoneNumber);
}
