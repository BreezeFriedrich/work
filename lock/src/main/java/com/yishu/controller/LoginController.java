package com.yishu.controller;

import com.yishu.pojo.User;
import com.yishu.service.IUserService;
import com.yishu.util.CookieUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-12-21 17:35 admin
 * @since JDK1.7
 */
@Controller
@RequestMapping("/user")
public class LoginController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(LoginController.class.getName());

    @Resource
    private IUserService userService;

    @RequestMapping("/login.do")
    public String login(HttpServletRequest request, Model model, @CookieValue(value="username",required=false) String cookie_username, @CookieValue(value="password",required=false) String cookie_password){
        String viewName="house/house_city";
        HttpSession session=request.getSession(true);//也可以直接作为handler方法的HttpSession类型的入参传入.
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        if ("".equals(username)||null==username){
            username=cookie_username;
        }
        if ("".equals(password)||null==password){
            password=cookie_password;
        }
        LOG.info("username : "+username);System.out.println("username : "+username);
        LOG.info("password : "+password);System.out.println("password : "+password);
        Map loginResultMap = userService.checkLogin(username,password);
        boolean isSuccess = false;
        if (0==(int)loginResultMap.get("result")){
            isSuccess=true;
        }
        String ownerName= (String) loginResultMap.get("ownerName");
        int grade= (int) loginResultMap.get("grade");
        if (isSuccess){
            session.setMaxInactiveInterval(24*60*60);//session单位为秒,默认30分钟.
            session.setAttribute("ownerPhoneNumber",username);
            session.setAttribute("ownerName",ownerName);
            session.setAttribute("grade",grade);
            model.addAttribute("ownerPhoneNumber",username);
            model.addAttribute("ownerName",ownerName);
            model.addAttribute("grade",grade);//初始10,添加了下级用户之后变为20,30,...
        }
        User user=userService.getUserWithSubordinate(username,grade);
        /*
        User userHierarchy=userService.getSubordinateHierarchy(user,10);
        model.addAttribute("userHierarchy",userHierarchy);
        if (grade>=30){
            return "house/house_city";
        }else if (grade>=20){
            return "house/house_district";
        }else if (grade>=10){
            return "house/house_landlord";
        }
        return "redirect:/jsp/error.jsp";
        */
        return "house/house_city";
    }

    @RequestMapping("/logout.do")
    @ResponseBody
    public void logout(HttpServletRequest request,HttpServletResponse response){
        System.out.println("user/logout.do");
        //clear Cookie[]
        CookieUtil.delAllCookie(request,response);

        request.getSession().invalidate();
    }

    @RequestMapping("/addSubordinate.do")
    @ResponseBody
    public boolean addSubordinate(HttpServletRequest request,HttpServletResponse response){
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        int grade= (int) session.getAttribute("grade");
        String juniorPhoneNumber=request.getParameter("juniorPhoneNumber");
        String juniorName=request.getParameter("juniorName");
        String juniorLocation=request.getParameter("juniorLocation");
        LOG.info("ownerPhoneNumber : "+ownerPhoneNumber);
        LOG.info("grade : "+grade);
        LOG.info("juniorPhoneNumber : "+juniorPhoneNumber+" ; juniorName : "+juniorName+" ; juniorLocation : "+juniorLocation);
        Map resultMap=userService.addSubordinate(ownerPhoneNumber,juniorPhoneNumber,juniorName,juniorLocation,grade);
        int result=0;
        result= (int) resultMap.get("result");
        return 0==result;
    }

    @RequestMapping("/cancleSubordinate.do")
    @ResponseBody
    public boolean cancleSubordinate(HttpServletRequest request,HttpServletResponse response){
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        int grade= (int) session.getAttribute("grade");
        String juniorPhoneNumber=request.getParameter("juniorPhoneNumber");
        boolean resultBoolean=false;
        resultBoolean=userService.cancleSubordinate(ownerPhoneNumber,juniorPhoneNumber,grade);
        return resultBoolean;
    }
}
/*
@Controller
@SessionAttributes({"User","Y"})        //此处定义此Controller中将要创建和使用哪些session中的对象名
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("user_login")
    public String login(User user, ModelMap modelMap){
        //modelMap自动与session对应，你在往modelmap中添加对应属性便是往session中添加属性（前提是你已经在@SessionAttributes注解中定义好）
        User userTemp = this.userService.findByName(user.getName());
        if((userTemp.getState() == 1) && (userTemp.getPassword().equals(user.getPassword())){
            modelMap.addAttribute("User", userTemp);        //成功将userTemp存入session中
            modelMap.addAttribute("Y",1);                   //成功将1存入session中
            return "/user/index";
        }else {
            return "index";
        }

    @RequestMapping("user_logout")
    public String logout(@ModelAttribute("User") User user, SessionStatus sessionStatus){
        //@ModelAttribute("User")相当于将session中名为"User"的对象注入user对象中
        //sessionStatus中的setComplete方法可以将session中的内容全部清空
        sessionStatus.setComplete();
        return "index";
    }
}
 */
