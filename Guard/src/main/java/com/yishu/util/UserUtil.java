package com.yishu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserUtil {
	
	public static String CheckLogin(String ownerPhoneNumber,String ownerPassword){
//		String ip="112.25.233.122";
		int sign=27;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String timetag=sdf.format(new Date());System.out.println("util-CheckLogin-timetag:"+timetag);
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"ownerPassword\":\""+ownerPassword+"\",\"timetag\":\""+timetag+"\"}";
		System.out.println("util-CheckLogin-data:"+data);
		String strData= HttpUtil.postData(data);
		System.out.println("util-CheckLogin-strData:"+strData);
		return strData;
	}
	
}
