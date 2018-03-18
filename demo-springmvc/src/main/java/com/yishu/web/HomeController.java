package com.yishu.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by WindSpring on 2018/3/18.
 */
//@Controller
//public class HomeController {
//    @RequestMapping(value = "/",method = GET)
//    public String home(){
//        return "home";//视图名home会解析为"/WEB-INF/views/home.jsp"路径的JSP.
//    }
//}
@Controller
//@RequestMapping("/")
@RequestMapping({"/","/homepage"})
public class HomeController {
    @RequestMapping(method = GET)
    public String home(){
        return "home";//视图名home会解析为"/WEB-INF/views/home.jsp"路径的JSP.
    }
}
