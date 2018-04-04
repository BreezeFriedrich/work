package com.yishu.pojo;

import java.util.List;

/**
 * Created by WindSpring on 2018/4/4.
 */
public class GatewayAndRecord {
    private Gateway gateway;
    private List<LockAndRecord> lockAndRecords;

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    public List<LockAndRecord> getLockAndRecords() {
        return lockAndRecords;
    }

    public void setLockAndRecords(List<LockAndRecord> lockAndRecords) {
        this.lockAndRecords = lockAndRecords;
    }
}
