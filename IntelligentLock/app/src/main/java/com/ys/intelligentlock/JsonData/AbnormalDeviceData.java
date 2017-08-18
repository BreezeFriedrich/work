package com.ys.intelligentlock.JsonData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/2/6.
 */

public class AbnormalDeviceData implements Serializable{
    public List<AbnormalDeviceInfo> abnormalDeviceLists;

    public class AbnormalDeviceInfo implements Serializable {
        public int type;              // type 1 gateway   2 lock
        public String abnormalDeviceName;
        public String abnormalDeviceCode;
        public String abnormalDeviceLocation;
        public String abnormalDeviceComment;
        public int abnormalDeviceStatus;
        public int lockPower;
    }

}
