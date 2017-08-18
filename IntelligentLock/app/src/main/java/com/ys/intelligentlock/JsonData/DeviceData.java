package com.ys.intelligentlock.JsonData;

/**
 * Created by admin on 2017/1/11.
 */

import java.io.Serializable;
import java.util.List;

public class DeviceData {

    public int result;
    public List<DeviceInfo> devices;

    public class DeviceInfo implements Serializable{
        public String gatewayName;
        public String gatewayCode;
        public String gatewayLocation;
        public String gatewayComment;
        public int gatewayStatus;

        public List<LockInfo> lockLists;


        public class LockInfo implements Serializable{
            public String lockName;
            public String lockCode;
            public String lockLocation;
            public String lockComment;
            public int lockStatus;
            public int lockPower;
        }
    }
}
