package com.yishu.web;

import com.yishu.model.User;
import com.yishu.service.IUserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-19 14:57 admin
 * @since JDK1.7
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("UserController");

    @Autowired
    private IUserService userService;

    @RequestMapping("/getUsers.do")
    @ResponseBody
    public List<User> getUsers(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- user/getUsers.do -->>--");
        }
//        HttpSession session=request.getSession(true);//也可以直接作为handler方法的HttpSession类型的入参传入.
//        String username=request.getParameter("username");
//        String password=request.getParameter("password");
//        LOG.info("username : "+username);

        List<User> userList=userService.listUser();
        return userList;
    }
}
