package com.yishu.controller;

import com.yishu.model.DeviceStatus;
import com.yishu.service.ModuleService;
import com.yishu.util.JsonUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by admin on 2017/5/11.
 */
@Controller
@RequestMapping("/moduleStatus")
public class ModuleStatusController {
    private static final org.slf4j.Logger logger= LoggerFactory.getLogger(ModuleStatusController.class);
    JsonUtil jsonUtil=new JsonUtil();

    @Autowired
    @Qualifier("moduleService")
    private ModuleService moduleService;

//    @RequestMapping("/listByStatus.do")
//    @ResponseBody
//    public String listByStatus(HttpServletRequest request){
//        logger.info("Controller:listByStatus");
//        int status=Integer.valueOf(request.getParameter("status"));
//        List<DeviceStatus> moduleList=moduleService.listByStatus(status);
//        JsonUtil jsonUtil=new JsonUtil();
//        return jsonUtil.listToJsonArray(moduleList);
//    }

    @RequestMapping("/listByStatus.do")
    @ResponseBody
    public String listByStatus(HttpServletRequest request){
        logger.info("#CTL      ~ listByStatus");
        int status=Integer.valueOf(request.getParameter("status"));
        List<DeviceStatus> moduleList=moduleService.listByStatus(status);
        return jsonUtil.listToJsonArray(moduleList);
    }

    @RequestMapping("/listAllWithoutDuplicate.do")
    @ResponseBody
    public String listAllWithoutDuplicate(HttpServletRequest request){
        logger.info("#CTL      ~ listAllWithoutDuplicate");
        List<DeviceStatus> moduleList=moduleService.listAllWithoutDuplicate();
        return jsonUtil.listToJsonArray(moduleList);
    }

    @RequestMapping("/hello.do")
    @ResponseBody
    public int hello(){
        logger.info("Controller:hello.do");
        return 9;
    }
}