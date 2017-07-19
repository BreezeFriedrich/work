package com.yishu.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yishu.service.DeviceService;
@ContextConfiguration(locations = {"/spring.xml"})
@Controller
@RequestMapping("/deviceManage")
public class DeviceManageController {
	@Autowired
	@Qualifier("deviceService")
	private DeviceService deviceService;
	
	@RequestMapping("/getDeviceInfo.do")
	@ResponseBody
	public String getDeviceTreeNode(HttpServletRequest request){
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String data=deviceService.getDeviceInfo(ownerPhoneNumber);
		System.out.println("data:"+data);
		return data;
	}

	@RequestMapping("/getDeviceTree.do")
	@ResponseBody
	public String getDeviceTree(HttpServletRequest request){
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String data=deviceService.getDeviceTree(ownerPhoneNumber);
		System.out.println("data:"+data);
		return data;
	}
	
	@RequestMapping("/getUnlockAccountInfo.do")
	@ResponseBody
	public String getUnlockAccountInfo(HttpServletRequest request){
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String gatewayCode=request.getParameter("gatewayCode");
		String lockCode=request.getParameter("lockCode");
		String data=deviceService.getUnlockAccountInfo(ownerPhoneNumber,gatewayCode,lockCode);//("17705155208",gatewayCode,lockCode)
		System.out.println("data:"+data);
		return data;
	}
	@RequestMapping("/getCertiAuthInfo.do")
	@ResponseBody
	public String getCertiAuthInfo(HttpServletRequest request){
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String gatewayCode=request.getParameter("gatewayCode");
		String lockCode=request.getParameter("lockCode");
		String data=deviceService.getCertiAuthInfo(ownerPhoneNumber,gatewayCode,lockCode);
		System.out.println("data:"+data);
		return data;
	}
	@RequestMapping("/getPwdAuthInfo.do")
	@ResponseBody
	public String getPwdAuthInfo(HttpServletRequest request){
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String gatewayCode=request.getParameter("gatewayCode");
		String lockCode=request.getParameter("lockCode");
		String data=deviceService.getPwdAuthInfo(ownerPhoneNumber,gatewayCode,lockCode);
		System.out.println("data:"+data);
		return data;
	}
	@RequestMapping("/doCertiAuth.do")
	@ResponseBody
	public int doCertiAuth(HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String gatewayCode=request.getParameter("gatewayCode");
		String lockCode=request.getParameter("lockCode");
		String name=request.getParameter("name");
		String cardNumb=request.getParameter("cardNumb");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		System.out.println("Controller-name:"+name);
		int result=deviceService.doCertiAuth(ownerPhoneNumber, gatewayCode, lockCode, name, cardNumb, startTime, endTime);
		System.out.println("result:"+result);
		return result;
	}
	@RequestMapping("/doPwdAuth.do")
	@ResponseBody
	public int doPwdAuth(HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String gatewayCode=request.getParameter("gatewayCode");
		String lockCode=request.getParameter("lockCode");
		String password=request.getParameter("password");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		System.out.println("Controller-password:"+password);
		int result=deviceService.doPwdAuth(ownerPhoneNumber, gatewayCode, lockCode,password, startTime, endTime);
		System.out.println("result:"+result);
		return result;
	}
	@RequestMapping("/doCertiCancelAuth.do")
	@ResponseBody
	public int doCertiCancelAuth(HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String lockCode=request.getParameter("lockCode");
		String serviceNumb=request.getParameter("serviceNumb");
		String cardNumb=request.getParameter("cardNumb");
		int result=deviceService.doCertiCancelAuth(ownerPhoneNumber, lockCode, serviceNumb, cardNumb);
		System.out.println("result:"+result);
		return result;
	}
	@RequestMapping("/doPwdCancelAuth.do")
	@ResponseBody
	public int doPwdCancelAuth(HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String lockCode=request.getParameter("lockCode");
		String gatewayCode=request.getParameter("gatewayCode");
		String serviceNumb=request.getParameter("serviceNumb");		
		int result=deviceService.doPwdCancelAuth(ownerPhoneNumber,lockCode,gatewayCode,serviceNumb);
		System.out.println("result:"+result);
		return result;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

}
