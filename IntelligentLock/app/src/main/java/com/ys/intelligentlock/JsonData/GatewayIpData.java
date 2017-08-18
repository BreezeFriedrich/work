package com.ys.intelligentlock.JsonData;

import java.util.List;

/**
 * Created by admin on 2017/1/20.
 */

public class GatewayIpData {
    public int result;
    public List<GatewayIpInfo> gatewayIpList;
    public class GatewayIpInfo{
        public String gatewayCode;
        public String gatewayIp;
    }
}
