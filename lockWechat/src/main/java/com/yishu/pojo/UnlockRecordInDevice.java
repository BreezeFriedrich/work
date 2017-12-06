/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.pojo;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-12-06 15:31 admin
 * @since JDK1.7
 */
public class UnlockRecordInDevice {
    private List<UnlockRecord> unlockRecordList;
    private Device device;

    public List<UnlockRecord> getUnlockRecordList() {
        return unlockRecordList;
    }

    public void setUnlockRecordList(List<UnlockRecord> unlockRecordList) {
        this.unlockRecordList = unlockRecordList;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
