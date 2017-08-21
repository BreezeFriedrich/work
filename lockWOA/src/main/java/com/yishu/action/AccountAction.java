package com.yishu.action;

import com.yishu.pojo.User;
import com.yishu.pojo.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountAction extends BaseAction {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private User user;

    public String login(){
        String username = user.getUsername();
        String password = user.getPassword();
        logger.debug("username => " + username);
        logger.debug("password => " + password);
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        return "login";
    }
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
}