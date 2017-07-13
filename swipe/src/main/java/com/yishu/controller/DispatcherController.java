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
//    private static final Logger logger= LoggerFactory.getLogger(DispatcherController.class);

    @RequestMapping("/**")
    public String dipatcherAll(HttpServletRequest request){
        String page=request.getRequestURI();
        page=page.substring(page.lastIndexOf("/dispatcher/")+12);//index为11或12都可以: 11包含'/',12不含

//        logger.info("page:"+page);
//        logger.info("getServletPath():"+request.getServletPath());
//        logger.info("getContextPath():"+request.getContextPath());
//        logger.info("getRequestURI():"+request.getRequestURI());
//        logger.info("getRequestURL():"+request.getRequestURL().toString());
//        logger.info("getQueryString():"+request.getQueryString());

        return page;
    }
}
