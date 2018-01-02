package com.yishu.controller;

import com.yishu.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/manageController")
public class ManageController {
	@Autowired
	@Qualifier("manageService")
	private ManageService manageService;

	@RequestMapping("/getjunior.do")
	@ResponseBody
	public String getJunior(HttpServletRequest request, HttpServletResponse response){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
		int grade= (int) session.getAttribute("grade");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
//		String grade=request.getParameter("grade");
		String result=manageService.getJunior(ownerPhoneNumber,Integer.valueOf(grade));
		return result;
	}

	@RequestMapping("/deljunior.do")
	@ResponseBody
	public String delJunior(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
		int grade= (int) session.getAttribute("grade");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
//		String grade=request.getParameter("grade");
		String juniorPhoneNumber=request.getParameter("juniorPhoneNumber");
		String result=manageService.delJunior(ownerPhoneNumber,juniorPhoneNumber,Integer.valueOf(grade));
		return result;
	}

	@RequestMapping("/addjunior.do")
	@ResponseBody
	public String addJunior(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
		int grade= (int) session.getAttribute("grade");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
//		String grade=request.getParameter("grade");
		String juniorPhoneNumber=request.getParameter("juniorPhoneNumber");
		String juniorName=request.getParameter("juniorName");
		String juniorLocation=request.getParameter("juniorLocation");
		String result=manageService.addJunior(ownerPhoneNumber,juniorPhoneNumber,juniorName,juniorLocation,Integer.valueOf(grade));
		return result;
	}

	@RequestMapping("/getdevices.do")
	@ResponseBody
	public String getDevices(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String result=manageService.getDevices(ownerPhoneNumber);
		return result;
	}

	@RequestMapping("/judgeLock.do")
	@ResponseBody
	public String judgeLock(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String lockCode=request.getParameter("lockCode");
		String result=manageService.judgeLock(ownerPhoneNumber,lockCode);
		System.out.println("enter controller");
		String qu="{\"result\":0,\"alreadyPhoneNumber\":\"\"}";
		System.out.println(qu);
		return result;
	}

	@RequestMapping("/addLock.do")
	@ResponseBody
	public String addLock(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String lockName=request.getParameter("lockName");
		String lockLocation=request.getParameter("lockLocation");
		String lockCode=request.getParameter("lockCode");
		String gatewayCode=request.getParameter("gatewayCode");
		String result=manageService.addLock(ownerPhoneNumber,gatewayCode,lockName,lockCode,lockLocation,lockLocation);
		System.out.println("enter controller");
		String qu="{\"result\":0,\"alreadyPhoneNumber\":\"\"}";
		System.out.println(qu);
		return result;
	}

	@RequestMapping("/delLock.do")
	@ResponseBody
	public String delLock(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String lockCode=request.getParameter("lockCode");
		String result=manageService.delLock(ownerPhoneNumber,lockCode);

//		System.out.println("enter controller");
//		String qu="{\"result\":0,\"alreadyPhoneNumber\":\"\"}";
//		System.out.println(qu);
		return result;
	}

	@RequestMapping("/delGateway.do")
	@ResponseBody
	public String delGateway(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String gatewayCode=request.getParameter("gatewayCode");
		String result=manageService.delGateway(ownerPhoneNumber,gatewayCode);
		return result;
	}

	@RequestMapping("/addGateway.do")
	@ResponseBody
	public String addGateway(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String gatewayCode=request.getParameter("gatewayCode");
		String opCode=request.getParameter("opCode");
		String gatewayName=request.getParameter("gatewayName");
		String gatewayLocation=request.getParameter("gatewayLocation");
		String result=manageService.addGateway(ownerPhoneNumber,gatewayCode,gatewayName,gatewayLocation,opCode);
		return result;
	}

	@RequestMapping("/getIDAuth.do")
	@ResponseBody
	public String getIDAuth(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String gatewayCode=request.getParameter("gatewayCode");
		String lockCode=request.getParameter("lockCode");
		String result=manageService.getIDAuth(ownerPhoneNumber,gatewayCode,lockCode);
		return result;
	}

	@RequestMapping("/getPwdAuth.do")
	@ResponseBody
	public String getPwdAuth(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String gatewayCode=request.getParameter("gatewayCode");
		String lockCode=request.getParameter("lockCode");
		String result=manageService.getPwdAuth(ownerPhoneNumber,gatewayCode,lockCode);
		return result;
	}

	@RequestMapping("/delPwdAuth.do")
	@ResponseBody
	public String delPwdAuth(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String gatewayCode=request.getParameter("gatewayCode");
		String lockCode=request.getParameter("lockCode");
		String serviceNumb=request.getParameter("serviceNumb");
//		System.out.println("Controller Pwd ownerPhoneNumber:  "+ownerPhoneNumber);
//		System.out.println("Controller Pwd gatewayCode:  "+gatewayCode);
//		System.out.println("Controller Pwd serviceNumb:  "+serviceNumb);
//		System.out.println("Controller Pwd lockCode:  "+lockCode);
		String result=manageService.delPwdAuth(ownerPhoneNumber,gatewayCode,serviceNumb,lockCode);
		System.out.println("delPwdAuth:  "+result);
		return result;
	}

	@RequestMapping("/delIDAuth.do")
	@ResponseBody
	public String delIDAuth(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String cardNumb=request.getParameter("cardNumb");
		String lockCode=request.getParameter("lockCode");
		String serviceNumb=request.getParameter("serviceNumb");
//		System.out.println("Controller ID ownerPhoneNumber:  "+ownerPhoneNumber);
//		System.out.println("Controller ID cardNumb:  "+cardNumb);
//		System.out.println("Controller ID serviceNumb:  "+serviceNumb);
//		System.out.println("Controller ID lockCode:  "+lockCode);
		String result=manageService.delIDAuth(ownerPhoneNumber,cardNumb,serviceNumb,lockCode);
		System.out.println("delIDAuth:  "+result);
		return result;
	}


	@RequestMapping("/doPwdAuth.do")
	@ResponseBody
	public String doPwdAuth(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String gatewayCode=request.getParameter("gatewayCode");
		String lockCode=request.getParameter("lockCode");
		String password=request.getParameter("password");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String result=manageService.doPwdAuth(ownerPhoneNumber,gatewayCode,lockCode,password,startTime,endTime);
		System.out.println("doPwdAuth:  "+result);
		return result;
	}

	@RequestMapping("/doIDAuth.do")
	@ResponseBody
	public String doIDAuth(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//		String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
		String cardNumb=request.getParameter("cardNumb");
		String lockCode=request.getParameter("lockCode");
		String gatewayCode=request.getParameter("gatewayCode");
		String name=request.getParameter("name");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String result=manageService.doIDAuth(ownerPhoneNumber,gatewayCode,lockCode,name,cardNumb,startTime,endTime);
		System.out.println("delIDAuth:  "+result);
		return result;
	}

}
