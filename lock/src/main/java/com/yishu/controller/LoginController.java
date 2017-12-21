package com.yishu.controller;

import com.yishu.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-12-21 17:35 admin
 * @since JDK1.7
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Resource
    private IUserService userService;

    @RequestMapping("/logIn.do")
    @ResponseBody
    public boolean logIn(HttpServletRequest request, HttpServletResponse response,@CookieValue(value="username",required=false) String cookie_username,@CookieValue(value="password",required=false) String cookie_password){
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        if ("".equals(username)||null==username){
            username=cookie_username;
        }
        if ("".equals(password)||null==password){
            password=cookie_password;
        }
        boolean result = userService.checkLogin(username,password);

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
