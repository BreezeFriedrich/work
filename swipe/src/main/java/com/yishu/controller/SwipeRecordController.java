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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @RequestMapping("/listByTimezone.do")
    @ResponseBody
    public String listByTimezone(HttpServletRequest request){
        logger.info("#CTL      ~ listByTimezone");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        List<SwipeRecord> swipeRecordList=swipeRecordService.listByTimezone(startTime,endTime);
        return jsonUtil.listToJson(swipeRecordList);
    }

    @RequestMapping("/listByTimezoneToChart.do")
    @ResponseBody
    public String listByTimezoneToChart(HttpServletRequest request){
        logger.info("#CTL      ~ listByTimezoneToChart");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        List<SwipeRecord> swipeRecordList=swipeRecordService.listByTimezone(startTime,endTime);

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Date time1 = null,time2 = null;
        try {
            time1=sdf.parse(startTime);
            time2=sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int days = (int) ((time2.getTime() - time1.getTime())/86400000);
        Date[] xAxisArr=new Date[days+1];
        while (days>=0) {
            xAxisArr[days] = new Date(days*86400000+time1.getTime());
            days--;
        }
        SwipeRecord swipeRecord=null;
        Date tempTime=null;
        Map<String,Integer> successMap=new HashMap();
        Map<String,Integer> failMap=new HashMap();
        for(SwipeRecord x:swipeRecordList){
            try {
                tempTime=sdf.parse(x.getTimestamp());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int i= (int) ((tempTime.getTime()-time1.getTime())/86400000);
            if(0==x.getResult()){
                if(successMap.containsKey(String.valueOf(i))) {
                    successMap.put(String.valueOf(i), successMap.get(String.valueOf(i)) + 1);
                }else{
                    successMap.put(String.valueOf(i), 1);
                }
            }else {
                if(failMap.containsKey(String.valueOf(i))) {
                    failMap.put(String.valueOf(i), failMap.get(String.valueOf(i)) + 1);
                }else{
                    failMap.put(String.valueOf(i), 1);
                }
            }
        }
        double value=0;
        double index[]=new double[days+1];
        for(int j=0;j<=days;j++){
            value=successMap.get(String.valueOf(j))*1.0/successMap.get(String.valueOf(j))+failMap.get(String.valueOf(j));
            index[j]=value;
        }
        Map resultMap=new HashMap();
        resultMap.put("xAxisArr",xAxisArr);
        resultMap.put("data",index);
        return jsonUtil.mapToJson(resultMap);
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
