package com.yishu.shiro.web;

import com.yishu.shiro.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by admin on 2017/4/26.
 */
@RequestMapping(value = "/")
@Controller
public class ShiroLoginController {
    private static final Logger logger = LoggerFactory.getLogger(ShiroLoginController.class);

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(User user, Model model){
        String username = user.getUsername();
        String password = user.getPassword();
        logger.debug("username => " + username);
        logger.debug("password => " + password);
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        String msg = null;
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            msg = e.getMessage();
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            msg = "用户名和密码的组合不正确";
        } catch (LockedAccountException e){
            e.printStackTrace();
            msg = e.getMessage();
        }
        if(msg == null){
//            return "redirect:/admin/user/list";
            return "main";
        }

        model.addAttribute("msg",msg);
        return "login";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(Model model){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("msg","您已经退出登录");
        return "login";
    }

    @RequestMapping(value = "/unAuthorization")
    public String unAuthorization(){
        return "unAuthorization";
    }
}
