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

//    @RequestMapping("/listByTimezoneToChart.do")
//    @ResponseBody
//    public String listByTimezoneToChart(HttpServletRequest request){
//        logger.info("#CTL      ~ listByTimezoneToChart");
//        int mode= Integer.parseInt(request.getParameter("mode"));
//        String startTime=request.getParameter("startTime");
//        String endTime=request.getParameter("endTime");
//        List<SwipeRecord> swipeRecordList=swipeRecordService.listByTimezone(startTime,endTime);
//
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
//        Date time1 = null,time2 = null;
//        try {
//            time1=sdf.parse(startTime);
//            time2=sdf.parse(endTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        int dateDiff = (int) ((time2.getTime() - time1.getTime())/86400000);
//        HashMap xAxisTime=new HashMap<String,Date>();
//        xAxisTime.put("min",time1);
//        xAxisTime.put("max",time2);
//
//        Date tempTime=null;
//        Map<String,Integer> successMap=new HashMap();
//        Map<String,Integer> failMap=new HashMap();
//        for(SwipeRecord x:swipeRecordList){
//            try {
//                tempTime=sdf.parse(x.getTimestamp());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            int i= (int) ((tempTime.getTime()-time1.getTime())/86400000);
//            if(0==x.getResult()){
//                if(successMap.containsKey(String.valueOf(i))) {
//                    successMap.put(String.valueOf(i), (int)successMap.get(String.valueOf(i)) + 1);
//                }else{
//                    successMap.put(String.valueOf(i), 1);
//                }
//            }else {
//                if(failMap.containsKey(String.valueOf(i))) {
//                    failMap.put(String.valueOf(i), (int)failMap.get(String.valueOf(i)) + 1);
//                }else{
//                    failMap.put(String.valueOf(i), 1);
//                }
//            }
//        }
//        logger.info("successMap.size:"+successMap);
//        logger.info("failMap.size:"+failMap);
//        double value=0;
//        double index[]=new double[dateDiff+1];
//        int successNum=0;
//        int failNum=0;
//        for(int j=0;j<=dateDiff;j++){
//            if(successMap.containsKey(String.valueOf(j))) {
//                successNum=successMap.get(String.valueOf(j));
//            }else{
//                successNum=0;
//            }
//            if(failMap.containsKey(String.valueOf(j))) {
//                failNum=failMap.get(String.valueOf(j));
//            }else{
//                failNum=0;
//            }
//            if(0!=successNum+failNum){
//                value=successNum*1.00/(successNum+failNum);
//                index[j]=value;
//            }else {
//                index[j]=0;
//            }
//        }
//        Map resultMap=new HashMap();
//        resultMap.put("xAxisTime",xAxisTime);
//        resultMap.put("data",index);
//        return jsonUtil.mapToJson(resultMap);
//    }

    @RequestMapping("/listByTimezoneToChart.do")
    @ResponseBody
    public String listByTimezoneToChart(HttpServletRequest request){
        logger.info("#CTL      ~ listByTimezoneToChart");
        int mode= Integer.parseInt(request.getParameter("mode"));
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        List<SwipeRecord> swipeRecordList=swipeRecordService.listByTimezone(startTime,endTime);

        //时间参数处理
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Date time1 = null,time2 = null;
        try {
            time1=sdf.parse(startTime);
            time2=sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1=Calendar.getInstance();
        Calendar calendar2=Calendar.getInstance();
        calendar1.setTime(time1);
        calendar2.setTime(time2);
        int dateDiff = (int) ((time2.getTime() - time1.getTime())/86400000);
        int monthDiff=calendar2.get(Calendar.YEAR)*12-calendar1.get(Calendar.YEAR)*12
                +calendar2.get(Calendar.MONTH)-calendar1.get(Calendar.MONTH);

        HashMap xAxisTime=new HashMap<String,Date>();
        Map resultMap=new HashMap();
        Date t1=new Date((time1.getTime()/86400000+1)*86400000);
        Date t2=new Date((time2.getTime()/86400000+1)*86400000);
        xAxisTime.put("min",t1);//xAxis的最小值
        xAxisTime.put("max",t2);
        resultMap.put("xAxisTime",xAxisTime);
        Date tempTime=null;
        double index[]=null;

        //按月
        if(2==mode){
            //获取series:data[]的值index[]
            index=new double[monthDiff+1];
            Arrays.fill(index,0.00);
            double success[]=new double[monthDiff+1];
            double fail[]=new double[monthDiff+1];
            for(SwipeRecord x:swipeRecordList){
                try {
                    tempTime=sdf.parse(x.getTimestamp());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                calendar2.setTime(tempTime);
                int trmpMonthDiff=calendar2.get(Calendar.YEAR)*12-calendar1.get(Calendar.YEAR)*12
                        +calendar2.get(Calendar.MONTH)-calendar1.get(Calendar.MONTH);
                if(0==x.getResult()){
                    success[trmpMonthDiff]++;
                }else {
                    fail[trmpMonthDiff]++;
                }
            }
            for(int i=0;i<=monthDiff;i++){
                if(success[i]+fail[i]>0){
                    index[i]=success[i]*1.00/(success[i]+fail[i]);
                }
                System.out.print(index[i]+",  ");
            }
            //获取xAxis:data[]的值xAxisData[]
            String[] xAxisData=new String[monthDiff+1];
            for(int k=0;k<monthDiff+1;k++) {
                int month=calendar1.get(Calendar.MONTH) + 1+k;
                xAxisData[k] = 0==month%12 ?
                        String.valueOf(calendar1.get(Calendar.YEAR)+month/12-1)+'-'+String.valueOf(12)
                        :String.valueOf(calendar1.get(Calendar.YEAR)+month/12)+'-'+String.valueOf(month%12);
            }
            resultMap.put("category",xAxisData);
        }

        //按周
        if(1==mode){};

        //按日
        if(0==mode){
            index=new double[dateDiff+1];
            Arrays.fill(index,0.00);
            double success[]=new double[dateDiff+1];
            double fail[]=new double[dateDiff+1];
            for(SwipeRecord x:swipeRecordList){
                try {
                    tempTime=sdf.parse(x.getTimestamp());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int tempDateDiff= (int) ((tempTime.getTime()-time1.getTime())/86400000);
                if(0==x.getResult()){
                    success[tempDateDiff]++;
                }else {
                    fail[tempDateDiff]++;
                }
            }
            System.out.println("index>>>>>>>>>");
            for(int i=0;i<=dateDiff;i++){
                if(success[i]+fail[i]>0){
                    index[i]=success[i]*1.00/(success[i]+fail[i]);
                }
                System.out.print(index[i]+",  ");
            }
            System.out.println();
            System.out.println("index<<<<<<<<<");

            String[] xAxisData=new String[dateDiff+1];
            int[] xAxisNum=new int[dateDiff+1];
            System.out.println("xAxisNum>>>>>>>>>");
            for(int k=0;k<dateDiff+1;k++){
                Date tempDate=new Date((time1.getTime()/86400000+1+k)*86400000);
                xAxisData[k]=sdf.format(tempDate);
                xAxisNum[k]=k+1;
                System.out.print(xAxisNum[k]+",  ");
            }
            System.out.println();
            System.out.println("xAxisNum<<<<<<<<<");
            resultMap.put("xAxisNum",xAxisNum);
            resultMap.put("category",xAxisData);
        }

        resultMap.put("data",index);

//        logger.info(String.valueOf(index.length));
//        logger.info(String.valueOf(xAxisTime.get("min")));
//        logger.info(String.valueOf(xAxisTime.get("max")));
        return jsonUtil.mapToJson(resultMap);
    }

    @RequestMapping("/listByTimezoneToChartPie.do")
    @ResponseBody
    public String listByTimezoneToChartPie(HttpServletRequest request) {
        logger.info("#CTL      ~ listByTimezoneToChart");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        List<SwipeRecord> swipeRecordList = swipeRecordService.listByTimezone(startTime, endTime);

        int successNum = 0;
        int failNum = 0;
        for(SwipeRecord x:swipeRecordList){
            if(0==x.getResult()){
                successNum++;
            }else {
                failNum++;
            }
        }
        List result=new ArrayList();
        HashMap resultMap1=new HashMap();
        resultMap1.put("value",successNum);
        resultMap1.put("name","成功");
        HashMap resultMap2=new HashMap();
        resultMap2.put("value",failNum);
        resultMap2.put("name","失败");
        result.add(resultMap1);
        result.add(resultMap2);
        return jsonUtil.listToJson(result);
    }

    @RequestMapping("/countByParam.do")
    @ResponseBody
    public int countByParam(HttpServletRequest request){
        logger.info("#CTL      ~ countByParam.do");
        String deviceid="";
        if(request.getParameter("deviceid")!=null){
            deviceid=request.getParameter("deviceid");
        }
        int result=-100;
        if(request.getParameter("result")!=""){
            result =Integer.parseInt(request.getParameter("result"));
        }
        String endTime=request.getParameter("endTime");
        int countNum;
        countNum=swipeRecordService.countByParam(endTime,result,deviceid);
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
        int result=-100;
        if(request.getParameter("result")!=""){
            result =Integer.parseInt(request.getParameter("result"));
        }
        String endTime=request.getParameter("endTime");
        int affectedNum=0;
        affectedNum=swipeRecordService.deleteByParam(endTime,result,deviceid);
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
