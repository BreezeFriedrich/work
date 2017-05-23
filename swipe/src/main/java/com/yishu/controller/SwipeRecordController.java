package com.yishu.controller;

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
        List<SwipeRecord> moduleList=swipeRecordService.listAll();
        return jsonUtil.listToJsonArray(moduleList);
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

}
