package com.yishu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by admin on 2017/7/11.
 */
@Controller
@RequestMapping("/dispatcher")
public class DispatcherController {
    private static final Logger logger= LoggerFactory.getLogger(DispatcherController.class);

    @RequestMapping("/**")
    public String dispatcherAll(HttpServletRequest request){
        String page=request.getRequestURI();
        page=page.substring(page.lastIndexOf("/dispatcher/")+12);//index为11或12都可以: 11包含'/',12不含
        return page;
    }
}
