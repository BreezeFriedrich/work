package com.yishu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.model.SwipeRecord;
import com.yishu.service.SwipeRecordService;
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
import java.util.Iterator;
import java.util.List;

/**
 * Created by admin on 2017/5/22.
 */
@Controller
@RequestMapping("/swipeRecord")
public class SwipeRecordController {
    private static final org.slf4j.Logger logger= LoggerFactory.getLogger(SwipeRecordController.class);
    JsonUtil jsonUtil=new JsonUtil();

    @Autowired
    @Qualifier("swipeRecordService")
    private SwipeRecordService swipeRecordService;

    @RequestMapping("/listAll.do")
    @ResponseBody
    public String listAll(HttpServletRequest request){
        logger.info("#CTL      ~ listAll");
        int limit= Integer.parseInt(request.getParameter("limit"));
        int page= Integer.parseInt(request.getParameter("page"));
        int start= Integer.parseInt(request.getParameter("start"));
        List<SwipeRecord> swipeRecordList=swipeRecordService.listAll();
        List<SwipeRecord> newSwipeRecordList=new ArrayList<>();
        SwipeRecord swipeRecord=null;
        int total=swipeRecordList.size();
        for(int i=((page-1)*limit);i<(page-1+1)*limit&&i<total;i++){
            swipeRecord=swipeRecordList.get(i);
            newSwipeRecordList.add(swipeRecord);
        }
        if (!newSwipeRecordList.isEmpty()){
            return listToObj(newSwipeRecordList,total);
        }
        return null;
        //{total:23,(page:2,limit:10,)data:[...]}
    }

    @RequestMapping("/listByTime.do")
    @ResponseBody
    public String listByStatus(HttpServletRequest request){
        logger.info("#CTL      ~ listByTime");
        String beginTime=request.getParameter("beginTime");
        String endTime=request.getParameter("endTime");
        List<SwipeRecord> moduleList=swipeRecordService.listByTime(beginTime,endTime);
        return jsonUtil.listToJsonArray(moduleList);
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
