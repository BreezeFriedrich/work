package com.yishu.model.json;

import java.util.List;

public class DeviceData {
	public int result;
	public List<DeviceInfo> devices;
	
	public class DeviceInfo{
		public String gatewayName;
		public String gatewayCode;
		public String gatewayLocation;
		public String gatewayComment;
		public String gatewayStatus;
		public List<LockInfo> lockLists;
		
		public class LockInfo{
			public String lockName;
			public String lockCode;
			public String lockLocation;
			public String lockComment;
			public int lockStatus;
			public int lockPower;
		}
	}
}
