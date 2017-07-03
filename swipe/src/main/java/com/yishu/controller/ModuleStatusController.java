package com.yishu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.model.DeviceStatus;
import com.yishu.service.ModuleService;
import com.yishu.util.JsonUtil;
import com.yishu.util.PageUtil;
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
import java.util.Map;

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
//        return jsonUtil.writeObject(moduleList);
//    }

    @RequestMapping("/listByStatus.do")
    @ResponseBody
    public String listByStatus(HttpServletRequest request){
//        logger.info("#CTL      ~ listByStatus");
        int status=Integer.valueOf(request.getParameter("status"));
        List<DeviceStatus> moduleList=moduleService.listByStatus(status);
        return jsonUtil.writeObject(moduleList);
    }

    @RequestMapping("/listAllWithoutDuplicate.do")
    @ResponseBody
    public String listAllWithoutDuplicate(HttpServletRequest request){
//        logger.info("#CTL      ~ listAllWithoutDuplicate");
        List<DeviceStatus> moduleList=moduleService.listAllWithoutDuplicate();
        return jsonUtil.writeObject(moduleList);
    }

    @RequestMapping("/listAll.do")
    @ResponseBody
    public String listAll(HttpServletRequest request){
//        logger.info("#CTL      ~ listAll");
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
//        return jsonUtil.writeObject(moduleList);
    }

    @RequestMapping(value = "/listAllWithStrategy")
    @ResponseBody
    public String listAllWithStrategy(HttpServletRequest request) throws Exception{
        HashMap paramMap= new HashMap(15);
        //直接返回前台
        String draw = request.getParameter("draw");
        //数据起始位置
        String startIndex = request.getParameter("startIndex");
        //每页显示的条数
        String pageSize = request.getParameter("pageSize");
        //获取排序字段
        String orderColumn = request.getParameter("orderColumn");
        if(orderColumn == null){
            orderColumn = "timestamp";
        }
        paramMap.put("orderColumn",orderColumn);
        //获取排序方式
        String orderDir = request.getParameter("orderDir");
        if(orderDir == null){
            orderDir = "desc";
        }
        paramMap.put("orderDir",orderDir);
        //查询条件
        String deviceid = request.getParameter("deviceid");
        String deviceip = request.getParameter("deviceip");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String status = request.getParameter("status");
        if(null != deviceid && !"".equals(deviceid)){
            paramMap.put("deviceid",deviceid);
        }
        if(null != deviceip && !"".equals(deviceip)){
            paramMap.put("deviceip",deviceip);
        }
        if(null != endTime && !"".equals(endTime)){
            paramMap.put("endTime",endTime);
        }
        if(null != startTime && !"".equals(startTime)){
            paramMap.put("startTime",startTime);
        }
        if(null != status && !"".equals(status)){
            paramMap.put("status",status);
        }
        List<DeviceStatus> deviceStatuses = moduleService.listAllWithStrategy(paramMap);
        Map<String, Object> info = new HashMap<String, Object>();
        if(deviceStatuses==null){
            info.put("pageData",null);
            info.put("total",0);
        }else{
            PageUtil<DeviceStatus> pageUtil=new PageUtil<DeviceStatus>(deviceStatuses);
            pageUtil.remodel((Integer.parseInt(pageSize)),Integer.parseInt(startIndex));
            info.put("pageData", pageUtil.getList());
            info.put("total", pageUtil.getTotal());
        }
        info.put("draw", Integer.parseInt(draw));//防止跨站脚本（XSS）攻击
//        return JSONObject.fromObject(info)+"";
        return jsonUtil.writeObject(info);
    }

    @RequestMapping("/listByTimezone.do")
    @ResponseBody
    public String listByTimezone(HttpServletRequest request){
//        logger.info("#CTL      ~ listByTimezone");
        int limit= Integer.parseInt(request.getParameter("limit"));
        int page= Integer.parseInt(request.getParameter("page"));
//        int start= Integer.parseInt(request.getParameter("start"));
        String startTime= request.getParameter("startTime");
        String endTime= request.getParameter("endTime");
        List<DeviceStatus> moduleStatusList=moduleService.listByTimezone(startTime,endTime);
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
//        return jsonUtil.writeObject(moduleList);
    }

    @RequestMapping("/listByParam.do")
    @ResponseBody
    public int listByParam(HttpServletRequest request){
//        logger.info("#CTL      ~ listByParam.do");
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
//            logger.info("deviceStatusList:"+deviceStatusList.toString());
            return deviceStatusList.size();
        }
        return 0;
    }

    @RequestMapping("/countByParam.do")
    @ResponseBody
    public int countByParam(HttpServletRequest request){
//        logger.info("#CTL      ~ countByParam.do");
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
//        logger.info("#CTL      ~ deleteByParam.do");
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