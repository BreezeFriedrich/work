package com.yishu.service.impl;

import org.springframework.stereotype.Service;
import com.yishu.service.DeviceService;
import com.yishu.util.DeviceUtil;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService{
	@Override
	public String getDeviceInfo(String ownerPhoneNumber) {
		String JsonData=null;
		JsonData=DeviceUtil.getDeviceInfo(ownerPhoneNumber);
		return JsonData;
	}

	@Override
	public String getDeviceTree(String ownerPhoneNumber) {
		return null;
	}

	@Override
	public String getUnlockAccountInfo(String ownerPhoneNumber, String gatewayCode,String lockCode) {
		String JsonData=null;
		JsonData=DeviceUtil.getUnlockAccountInfo(ownerPhoneNumber,gatewayCode,lockCode);
		System.out.println("JsonData:"+JsonData);
		return JsonData;
	}
	
	@Override
	public String getCertiAuthInfo(String ownerPhoneNumber, String gatewayCode,String lockCode) {
		String JsonData=null;
		JsonData=DeviceUtil.getCertiAuthInfo(ownerPhoneNumber,gatewayCode,lockCode);
		System.out.println("JsonData:"+JsonData);
		return JsonData;
	}

	@Override
	public String getPwdAuthInfo(String ownerPhoneNumber, String gatewayCode,String lockCode) {
		String JsonData=null;
		JsonData=DeviceUtil.getPwdAuthInfo(ownerPhoneNumber,gatewayCode,lockCode);
		System.out.println("JsonData:"+JsonData);
		return JsonData;
	}

	@Override
	public int doCertiAuth(String ownerPhoneNumber, String gatewayCode,String lockCode, String name, String cardNumb, String startTime,String endTime) {
		int result=1;
		String data=DeviceUtil.doCertiAuth(ownerPhoneNumber, gatewayCode, lockCode, name, cardNumb, startTime, endTime);
//		result=Integer.parseInt(data.substring(data.indexOf(':')+1, data.indexOf('}')));
//		System.out.println("doCertiAuthResult:"+result);
		System.out.println("doCertiAuthData:"+data);
		return 0;
	}
	
	@Override
	public int doPwdAuth(String ownerPhoneNumber, String gatewayCode,String lockCode, String password, String startTime,String endTime) {
		int result=1;
		String data=DeviceUtil.doPwdAuth(ownerPhoneNumber, gatewayCode, lockCode,password, startTime, endTime);
		result=Integer.parseInt(data.substring(data.indexOf(':')+1, data.indexOf('}')));
		System.out.println("result:"+result);
		return result;
	}

	@Override
	public int doCertiCancelAuth(String ownerPhoneNumber, String lockCode,
			String serviceNumb, String cardNumb) {
		int result=1;
		String data=DeviceUtil.doCertiCancelAuth(ownerPhoneNumber, lockCode, serviceNumb, cardNumb);
		result=Integer.parseInt(data.substring(data.indexOf(':')+1, data.indexOf('}')));
		System.out.println("result:"+result);
		return result;
	}

	@Override
	public int doPwdCancelAuth(String ownerPhoneNumber, String lockCode,
			String gatewayCode, String serviceNumb) {
		int result=1;
		String data=DeviceUtil.doPwdCancelAuth(ownerPhoneNumber, lockCode, gatewayCode, serviceNumb);
		result=Integer.parseInt(data.substring(data.indexOf(':')+1, data.indexOf('}')));
		System.out.println("result:"+result);
		return result;
	}
}
