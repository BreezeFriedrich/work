package com.yishu.model.json;

import java.util.ArrayList;
import java.util.List;

public class LockOperateJson {
	
	public int totals;
	public List<LockOperateRecord> rows=new ArrayList<LockOperateRecord>();
	
	public class LockOperateRecord{
		public String lockCode;
		public String gatewayCode;
		public int openMode;
		public String timetag;
		
		public String name;
		public String cardNumb;
		public String dnCode;
		public String serviceNumb;
		
		public String password;

		@Override
		public String toString() {
			return "LockOperateRecord [lockCode=" + lockCode + ", gatewayCode="
					+ gatewayCode + ", openMode=" + openMode + ", timetag="
					+ timetag + ", name=" + name + ", cardNumb=" + cardNumb
					+ ", dnCode=" + dnCode + ", serviceNumb=" + serviceNumb
					+ ", password=" + password + "]";
		}

	}
	
}
