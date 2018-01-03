package com.yishu.service.impl;

import com.yishu.service.ManageService;
import com.yishu.util.HttpUtil;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service("manageService")
public class ManageServiceImpl implements ManageService {
	/**
	 * 得到下级用户
	 * @param ownerPhoneNumber
	 * @param grade
	 * @return
	 */
	public String getJunior(String ownerPhoneNumber, int grade) {
		int sign=304;
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"grade\":\""+grade+"\"}";
		String result= HttpUtil.postData(data);
		System.out.println("getJunior  data "+data);
		System.out.println("getJunior  result "+result);
		return result;
	}

	/**
	 * 删除下级
	 * @param ownerPhoneNumber
	 * @param juniorPhoneNumber
	 * @param grade
	 * @return
	 */
	public String delJunior(String ownerPhoneNumber, String juniorPhoneNumber, int grade) {
		int sign=305;
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"juniorPhoneNumber\":\""+juniorPhoneNumber+"\",\"grade\":\""+grade+"\"}";
		String result= HttpUtil.postData(data);
		System.out.println("doJunior  data "+data);
		System.out.println("doJunior  result "+result);
		return result;
	}

	/**
	 * 添加下级
	 * @param ownerPhoneNumber
	 * @param juniorPhoneNumber
	 * @param juniorName
	 * @param juniorLocation
	 * @param grade
	 * @return
	 */
	public String addJunior(String ownerPhoneNumber, String juniorPhoneNumber, String juniorName, String juniorLocation, int grade) {
		int sign=303;
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"juniorPhoneNumber\":\""+juniorPhoneNumber+"\",\"juniorName\":\""+juniorName+"\",\"juniorLocation\":\""+juniorLocation+"\",\"grade\":\""+grade+"\"}";
		String result= HttpUtil.postData(data);
		System.out.println("addJunior data" +data);
		System.out.println("addJunior result" +result);
		return result;
	}

	/**
	 * 获得设备信息
	 * @param ownerPhoneNumber
	 * @return
	 */
	public String getDevices(String ownerPhoneNumber) {
		int sign=16;
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
		String result= HttpUtil.postData(data);
		System.out.println("getdevices:"+result);
		return result;
	}

	/**
	 * 判断门锁是否已被添加
	 * @param ownerPhoneNumber
	 * @param lockCode
	 * @return
	 */
	public String judgeLock(String ownerPhoneNumber, String lockCode) {
		int sign=11;
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\"}";
		String result= HttpUtil.postData(data);
		return result;
	}

	/**
	 * 添加门锁
	 * @param ownerPhoneNumber
	 * @param gatewayCode
	 * @param lockName
	 * @param lockCode
	 * @param lockLocation
	 * @param lockComment
	 * @return
	 */
	public String addLock(String ownerPhoneNumber, String gatewayCode, String lockName, String lockCode, String lockLocation, String lockComment) {
		int sign=12;
		String timetag=getTimetag(new Date());
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockName\":\""+lockName+"\",\"lockCode\":\""+lockCode+"\",\"lockLocation\":\""+lockLocation+"\",\"lockComment\":\""+lockLocation+"\",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.postData(data);
		return result;
	}

	/**
	 * 删除门锁
	 * @param ownerPhoneNumber
	 * @param lockCode
	 * @return
	 */
	public String delLock(String ownerPhoneNumber, String lockCode) {
		int sign=14;
		String timetag=getTimetag(new Date());
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.postData(data);
		System.out.println("jsonresult"+result);
		return result;
	}

	/**
	 * 获得身份证授权信息
	 * @param ownerPhoneNumber
	 * @param gatewayCode
	 * @param lockCode
	 * @return
	 */
	public String getIDAuth(String ownerPhoneNumber, String gatewayCode, String lockCode) {
		int sign=17;
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
		String result= HttpUtil.postData(data);
		return result;
	}

	/**
	 * 获得密码授权信息
	 * @param ownerPhoneNumber
	 * @param gatewayCode
	 * @param lockCode
	 * @return
	 */
	public String getPwdAuth(String ownerPhoneNumber, String gatewayCode, String lockCode) {
		int sign=20;
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
		String result= HttpUtil.postData(data);
		return result;
	}

	/**
	 * 把Date转换成timetag
	 * @param date
	 * @return
	 */
	public String getTimetag(Date date) {
		String timetag;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		timetag=sdf.format(date);
		return timetag;
	}

	/**
	 * 删除网关
	 * @param ownerPhoneNumber
	 * @param gatewayCode
	 * @return
	 */
	public String delGateway(String ownerPhoneNumber, String gatewayCode) {
		int sign=10;
		String timetag=getTimetag(new Date());
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.postData(data);
		return result;
	}

	/**
	 * 添加网关
	 * @param ownerPhoneNumber
	 * @param gatewayCode
	 * @param gatewayName
	 * @param gatewayLocation
	 * @param opCode
	 * @return
	 */
	public String addGateway(String ownerPhoneNumber, String gatewayCode, String gatewayName, String gatewayLocation, String opCode) {
		int sign=8;
		String timetag=getTimetag(new Date());
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayName\":\""+gatewayName+"\",\"gatewayCode\":\""+gatewayCode+"\",\"gatewayLocation\":\""+gatewayLocation+"\",\"gatewayComment\":\""+gatewayLocation+"\",\"opCode\":\""+opCode+"\",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.postData(data);
		return result;
	}

	/**
	 * 删除身份证授权
	 * @param ownerPhoneNumber
	 * @param cardNumb
	 * @param serviceNumb
	 * @param lockCode
	 * @return
	 */
	public String delIDAuth(String ownerPhoneNumber, String cardNumb, String serviceNumb, String lockCode) {
		int sign=19;
		String timetag=getTimetag(new Date());
		System.out.println("ID ownerPhoneNumber:  "+ownerPhoneNumber);
		System.out.println("ID cardNumb:  "+cardNumb);
		System.out.println("ID serviceNumb:  "+serviceNumb);
		System.out.println("ID lockCode:  "+lockCode);
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"cardNumb\":\""+cardNumb+"\",\"timetag\":\""+timetag+"\"}";
		System.out.println("delIDAuthData:  "+data);
		String result= HttpUtil.postData(data);
		return result;
	}

	/**
	 * 删除密码授权
	 * @param ownerPhoneNumber
	 * @param gatewayCode
	 * @param serviceNumb
	 * @param lockCode
	 * @return
	 */
	public String delPwdAuth(String ownerPhoneNumber, String gatewayCode, String serviceNumb, String lockCode) {
		int sign=22;
		String timetag=getTimetag(new Date());
		System.out.println("Pwd ownerPhoneNumber:  "+ownerPhoneNumber);
		System.out.println("Pwd gatewayCode:  "+gatewayCode);
		System.out.println("Pwd serviceNumb:  "+serviceNumb);
		System.out.println("Pwd lockCode:  "+lockCode);
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"gatewayCode\":\""+gatewayCode+"\",\"timetag\":\""+timetag+"\"}";
		System.out.println("delPwdAuthData:  "+data);
		String result= HttpUtil.postData(data);
		return result;
	}

	public String doIDAuth(String ownerPhoneNumber, String gatewayCode, String lockCode, String name, String cardNumb, String startTime, String endTime) {
		int sign=18;
		String timetag=getTimetag(new Date());
		String serviceNumb=getServiceNumb(ownerPhoneNumber,timetag);
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"name\":\""+name+"\",\"cardNumb\":\""+cardNumb+"\",\"dnCode\":\"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.postData(data);
		return result;
	}

	public String doPwdAuth(String ownerPhoneNumber, String gatewayCode, String lockCode, String password, String startTime, String endTime) {
		int sign=21;
		String timetag=getTimetag(new Date());
		String serviceNumb=getServiceNumb(ownerPhoneNumber,timetag);
		String data=" {\"sign\":\""+sign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"password\":\""+password+"\",\"dnCode\":\"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"timetag\":\""+timetag+"\"}";
		String result= HttpUtil.postData(data);
		return result;
	}

	/**
	 * 生成ServiceNumb
	 * @param ownerPhoneNumber
	 * @param timetag
	 * @return
	 */
	public String getServiceNumb(String ownerPhoneNumber, String timetag) {
		long num=(long) Math.floor(Math.random()*10000000);
		String serviceNumb=timetag+ownerPhoneNumber+String.valueOf(num);
		return serviceNumb;
	}

	public static void main(String args[]){
		ManageServiceImpl mm=new ManageServiceImpl();
		mm.delLock("13998892002","12456");
	}
}
