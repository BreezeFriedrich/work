package com.yishu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 2017/5/3.
 */
@Controller
public class DispatcherController {

    @RequestMapping("/dispatcher")
    @ResponseBody
    public String dispatcher(HttpServletRequest request, HttpServletResponse response){
        Logger logger= LoggerFactory.getLogger(DispatcherController.class);
        String str=request.getServletPath();
//        System.out.println(str);
        logger.info(str);
        String str2=(str.split("/"))[1];
        String str3=str2.split(".jsp")[0];
        return str3;
    }
}
