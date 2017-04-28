package com.yishu.model.json;

import java.util.List;

public class LockOperateData {
	
	public int result;
	public List<RecordInfo> recordList;
	
	public class RecordInfo{
		public String lockCode;
		public String gatewayCode;
		public int openMode;
		public String timetag;
		public Card cardInfo;
		public Password passwordInfo;
		
		public class Card{
			public String name;
			public String cardNumb;
			public String dnCode;
			public String serviceNumb;
		}
		public class Password{
			public String password;
			public String serviceNumb;
		}
		@Override
		public String toString() {
			return "RecordInfo [lockCode=" + lockCode + ", gatewayCode="
					+ gatewayCode + ", openMode=" + openMode + ", timetag="
					+ timetag + ", cardInfo=" + cardInfo + ", passwordInfo="
					+ passwordInfo + "]";
		}		
	}

	@Override
	public String toString() {
		return "LockOperateData [result=" + result + ", RecordList="
				+ recordList + "]";
	}
	
}
