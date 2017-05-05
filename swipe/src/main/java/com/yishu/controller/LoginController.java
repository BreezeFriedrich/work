package com.yishu.controller;


import com.yishu.model.NoteResult;
import com.yishu.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class LoginController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/logIn.do")
	@ResponseBody
	public NoteResult logIn(HttpServletRequest request, HttpServletResponse response){
		
		String author = request.getHeader("Authorization");
		NoteResult result =
			userService.checkLogin(author);
		return result;
	}

	@RequestMapping("/logOut.do")
	@ResponseBody
	public void logOut(HttpServletRequest request,HttpServletResponse response){
		System.out.println("user/logOut.do");
		//clear Cookie[]
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){  
		    for(Cookie cookie:cookies){
		        System.out.println("clear Cookie[]:{"+cookie.getName()+":"+cookie.getValue()+"}");
		        cookie.setMaxAge(0);
		    }
		}
		request.getSession().invalidate();
	}
}