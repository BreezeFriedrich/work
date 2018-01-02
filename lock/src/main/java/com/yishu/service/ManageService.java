package com.yishu.service;

import java.util.Date;

public interface ManageService {
	String getJunior(String ownerPhoneNumber, int grade);
	String delJunior(String ownerPhoneNumber, String juniorPhoneNumber, int grade);
	String addJunior(String ownerPhoneNumber, String juniorPhoneNumber, String juniorName, String juniorLocation, int grade);
	String getDevices(String ownerPhoneNumber);
	String judgeLock(String ownerPhoneNumber, String lockCode);
	String addLock(String ownerPhoneNumber, String gatewayCode, String lockName, String lockCode, String lockLocation, String lockComment);
	String delLock(String ownerPhoneNumber, String lockCode);
	String getIDAuth(String ownerPhoneNumber, String gatewayCode, String lockCode);
	String getPwdAuth(String ownerPhoneNumber, String gatewayCode, String lockCode);
	String getTimetag(Date date);
	String delGateway(String ownerPhoneNumber, String gatewayCode);
	String addGateway(String ownerPhoneNumber, String gatewayCode, String gatewayName, String gatewayLocation, String opCode);
	String delIDAuth(String ownerPhoneNumber, String cardNumb, String serviceNumb, String lockCode);
	String delPwdAuth(String ownerPhoneNumber, String gatewayCode, String serviceNumb, String lockCode);
	String doIDAuth(String ownerPhoneNumber, String gatewayCode, String lockCode, String name, String cardNumb, String startTime, String endTime);
	String doPwdAuth(String ownerPhoneNumber, String gatewayCode, String lockCode, String password, String startTime, String endTime);
	String getServiceNumb(String ownerPhoneNumber, String timetag);
}
