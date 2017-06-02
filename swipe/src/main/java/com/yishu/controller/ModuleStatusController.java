package com.yishu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.HashMap;
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
//        return jsonUtil.listToJson(moduleList);
//    }

    @RequestMapping("/listByStatus.do")
    @ResponseBody
    public String listByStatus(HttpServletRequest request){
        logger.info("#CTL      ~ listByStatus");
        int status=Integer.valueOf(request.getParameter("status"));
        List<DeviceStatus> moduleList=moduleService.listByStatus(status);
        return jsonUtil.listToJson(moduleList);
    }

    @RequestMapping("/listAllWithoutDuplicate.do")
    @ResponseBody
    public String listAllWithoutDuplicate(HttpServletRequest request){
        logger.info("#CTL      ~ listAllWithoutDuplicate");
        List<DeviceStatus> moduleList=moduleService.listAllWithoutDuplicate();
        return jsonUtil.listToJson(moduleList);
    }

    @RequestMapping("/listAll.do")
    @ResponseBody
    public String listAll(HttpServletRequest request){
        logger.info("#CTL      ~ listAll");
        int limit= Integer.parseInt(request.getParameter("limit"));
        int page= Integer.parseInt(request.getParameter("page"));
        int start= Integer.parseInt(request.getParameter("start"));
        List<DeviceStatus> moduleStatusList=moduleService.listAll();
        List<DeviceStatus> newModuleStatusList=new ArrayList<>();
        DeviceStatus deviceStatus=null;
        int total=moduleStatusList.size();
        for(int i=((page-1)*limit);i<(page-1+1)*limit&&i<total;i++){
            deviceStatus=moduleStatusList.get(i);
            newModuleStatusList.add(deviceStatus);
        }
        if (!newModuleStatusList.isEmpty()){
            return listToObj(newModuleStatusList,total);
        }
        return null;
        //{total:23,(page:2,limit:10,)data:[...]}

//        logger.info("#CTL      ~ listAll");
//        List<DeviceStatus> moduleList=moduleService.listAll();
//        return jsonUtil.listToJson(moduleList);
    }

    @RequestMapping("/listByParam.do")
    @ResponseBody
    public int listByParam(HttpServletRequest request){
        logger.info("#CTL      ~ listByParam.do");
        String deviceid="";
        if(request.getParameter("deviceid")!=null){
            deviceid=request.getParameter("deviceid");
        }
        int status=-100;
        if(request.getParameter("status")!=""){
            status =Integer.parseInt(request.getParameter("status"));
        }
        String endTime=request.getParameter("endTime");
        List<DeviceStatus> deviceStatusList=moduleService.listByParam(endTime,status,deviceid);
        if (deviceStatusList!=null&&!deviceStatusList.isEmpty()){
            logger.info("deviceStatusList:"+deviceStatusList.toString());
            return deviceStatusList.size();
        }
        return 0;
    }

    @RequestMapping("/countByParam.do")
    @ResponseBody
    public int countByParam(HttpServletRequest request){
        logger.info("#CTL      ~ countByParam.do");
        String deviceid="";
        if(request.getParameter("deviceid")!=null){
            deviceid=request.getParameter("deviceid");
        }
        int status=-100;
        if(request.getParameter("status")!=""){
            status =Integer.parseInt(request.getParameter("status"));
        }
        String endTime=request.getParameter("endTime");
        int countNum;
        countNum=moduleService.countByParam(endTime,status,deviceid);
        if(countNum>=0){
            return countNum;
        }
        return -100;
    }

    @RequestMapping("/deleteByParam.do")
    @ResponseBody
    public int deleteByParam(HttpServletRequest request){
        logger.info("#CTL      ~ deleteByParam.do");
        String deviceid="";
        if(request.getParameter("deviceid")!=null){
            deviceid=request.getParameter("deviceid");
        }
        int status=-100;
        if(request.getParameter("status")!=""){
            status =Integer.parseInt(request.getParameter("status"));
        }
        String endTime=request.getParameter("endTime");
        int affectedNum=0;
        affectedNum=moduleService.deleteByParam(endTime,status,deviceid);
        return affectedNum;
    }

    public String listToObj(List list,int total){
        HashMap<String,Object> map=new HashMap<>();
        map.put("draw",1);
        map.put("data",list);
        map.put("recordsTotal",total);
        map.put("recordsFiltered",total);
        ObjectMapper objectMapper = new ObjectMapper();
        String data=null;
        try {
            data=objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return data;
    }
}