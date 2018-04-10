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
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("LoginController");

    @Resource
    private IUserService userService;

    @RequestMapping("/login.do")
    public String login(HttpServletRequest request, Model model, @CookieValue(value="username",required=false) String cookie_username, @CookieValue(value="password",required=false) String cookie_password){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- user/login.do -->>--");
        }
        HttpSession session=request.getSession(true);//也可以直接作为handler方法的HttpSession类型的入参传入.
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        if ("".equals(username)||null==username){
            username=cookie_username;
        }
        if ("".equals(password)||null==password){
            password=cookie_password;
        }
        LOG.info("username : "+username);
        LOG.info("password : "+password);
        Map loginResultMap = userService.checkLogin(username,password);
        boolean isSuccess = false;
        if (0==(int)loginResultMap.get("result")){
            isSuccess=true;
        }
        if (isSuccess){
            String ownerName= (String) loginResultMap.get("ownerName");
            int grade= (int) loginResultMap.get("grade");
            session.setMaxInactiveInterval(1*60*60);//session单位为秒,默认30分钟.
            session.setAttribute("ownerPhoneNumber",username);
            session.setAttribute("ownerName",ownerName);
            session.setAttribute("grade",grade);
            model.addAttribute("ownerPhoneNumber",username);
            model.addAttribute("ownerName",ownerName);
            model.addAttribute("grade",grade);//初始10,添加了下级用户之后变为20,30,...
        }else{
            return "login";
        }
        /*
        User user=userService.getUserWithSubordinate(username,grade);
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
        return "redirect:/user/dispatcherHouseStatus.do";
    }

    /*
    @RequestMapping("/dispatcherDeviceManage.do")
    public String dispatcherDeviceManage(HttpServletRequest request, Model model){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- user/dispatcherDeviceManage.do -->>--");
        }
        return "deviceManage";
    }
    */

    @RequestMapping("/dispatcherHouseStatus.do")
    public String dispatcherHouseStatus(HttpServletRequest request, Model model){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- user/dispatcherHouseStatus.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        int grade= (int) session.getAttribute("grade");
        User user=userService.getUserWithSubordinate(ownerPhoneNumber,grade);
        user.setName((String) session.getAttribute("ownerName"));
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
    }

    @RequestMapping("/dispatcherJuniorSetting.do")
    public String dispatcherJuniorSetting(HttpServletRequest request, Model model){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- user/dispatcherJuniorSetting.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        int grade= (int) session.getAttribute("grade");
        LOG.info("session中 ownerPhoneNumber:"+ownerPhoneNumber+" ,grade:"+grade+",maxInactiveInterval:"+session.getMaxInactiveInterval());
        User user=userService.getUserWithSubordinate(ownerPhoneNumber,grade);
        user.setName((String) session.getAttribute("ownerName"));
        User userHierarchy=userService.getSubordinateHierarchy(user,10);
        model.addAttribute("userHierarchy",userHierarchy);
        if (grade>=30){
            return "set/set_citySetDistrict";
        }else if (grade>=20){
            return "set/set_districtSetLandlord";
        }else if (grade>=10){
            return "set/set_landLordSetHouse";
        }
        return "redirect:/jsp/error.jsp";
    }

    @RequestMapping("/getUserFromSession.do")
    @ResponseBody
    public User getUserFromSession(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- user/getUserFromSession.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        int grade= (int) session.getAttribute("grade");
        User user=new User();
        user.setPhoneNumber(ownerPhoneNumber);
        user.setGrade(grade);
        user.setName((String) session.getAttribute("ownerName"));
        return user;
    }

    @RequestMapping("/getUserHierarchy.do")
    @ResponseBody
    public User getUserHierarchy(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- user/getUserHierarchy.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        int grade= (int) session.getAttribute("grade");
        User user=userService.getUserWithSubordinate(ownerPhoneNumber,grade);
        user.setName((String) session.getAttribute("ownerName"));
        User userHierarchy=userService.getSubordinateHierarchy(user,10);
        return userHierarchy;
    }

    @RequestMapping("/getSubordinateHierarchyTillLock.do")
    @ResponseBody
    public User getSubordinateHierarchyTillLock(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- user/getSubordinateHierarchyTillLock.do -->>--");
        }
        HttpSession session=request.getSession(false);
//        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//        int grade= (int) session.getAttribute("grade");
        String ownerPhoneNumber="13905169824";
        int grade=10;
        User user=userService.getUserWithSubordinate(ownerPhoneNumber,grade);
        user.setName((String) session.getAttribute("ownerName"));
        User userHierarchyTillLock=userService.getSubordinateHierarchyTillLock(user);
        return userHierarchyTillLock;
    }

    @RequestMapping("/logout.do")
    @ResponseBody
    public void logout(HttpServletRequest request,HttpServletResponse response){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- user/logout.do -->>--");
        }
        //clear Cookie[]
        CookieUtil.delAllCookie(request,response);

        request.getSession().invalidate();
    }

    /**
     * 前台form提交,必然要刷新页面,handler return视图.
     *
     * @param request
     * @return
     */
    @RequestMapping("/addSubordinateReturnView.do")
    public String addSubordinateReturnView(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- user/addSubordinateReturnView.do -->>--");
        }
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
        if (0==result){
            LOG.info("操作成功");
        }
        return "userManage";
    }

    /**
     * 前台不用form提交,用ajax提交,handler return数据.
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addSubordinate.do")
    @ResponseBody
    public boolean addSubordinate(HttpServletRequest request,HttpServletResponse response){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- user/addSubordinate.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        int grade= (int) session.getAttribute("grade");
        String juniorPhoneNumber=request.getParameter("juniorPhoneNumber");
        String juniorName=request.getParameter("juniorName");
        String juniorLocation=request.getParameter("juniorLocation");
        LOG.info("ownerPhoneNumber : "+ownerPhoneNumber+", grade : "+grade);
        LOG.info("juniorPhoneNumber : "+juniorPhoneNumber+" ; juniorName : "+juniorName+" ; juniorLocation : "+juniorLocation);
        Map resultMap=userService.addSubordinate(ownerPhoneNumber,juniorPhoneNumber,juniorName,juniorLocation,grade);
        int result=0;
        result= (int) resultMap.get("result");
        return 0==result;
    }

    @RequestMapping("/cancleSubordinate.do")
    @ResponseBody
    public boolean cancleSubordinate(HttpServletRequest request,HttpServletResponse response){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- user/cancleSubordinate.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        int grade= (int) session.getAttribute("grade");
        String juniorPhoneNumber=request.getParameter("juniorPhoneNumber");
        boolean resultBoolean=false;
        resultBoolean=userService.cancleSubordinate(ownerPhoneNumber,juniorPhoneNumber,grade);
        return resultBoolean;
    }
}