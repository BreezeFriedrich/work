package com.yishu.service;

public interface DeviceService{
	public String getDeviceInfo(String ownerPhoneNumber);
	public String getDeviceTree(String ownerPhoneNumber);
	public String getUnlockAccountInfo(String ownerPhoneNumber,String gatewayCode,String lockCode);
	public String getCertiAuthInfo(String ownerPhoneNumber,String gatewayCode,String lockCode);
	public String getPwdAuthInfo(String ownerPhoneNumber,String gatewayCode,String lockCode);
	public int doCertiAuth(String ownerPhoneNumber,String gatewayCode, String lockCode,String name,String cardNumb,String startTime,String endTime);
	public int doPwdAuth(String ownerPhoneNumber,String gatewayCode, String lockCode,String password,String startTime,String endTime);
	public int doCertiCancelAuth(String ownerPhoneNumber,String lockCode,String serviceNumb,String cardNumb);
	public int doPwdCancelAuth(String ownerPhoneNumber,String lockCode,String gatewayCode,String serviceNumb);
}
