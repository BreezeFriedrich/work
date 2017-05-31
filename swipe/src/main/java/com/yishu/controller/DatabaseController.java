package com.yishu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.model.DeviceStatus;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WindSpring on 2017/5/31.
 */
@Controller
@RequestMapping("/database")
public class DatabaseController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DatabaseController.class);

//    @Autowired
//    @Qualifier("databaseService")
//    private DatabaseService databaseService;

    @RequestMapping(value = "/clear",method = RequestMethod.GET)
//    @ResponseBody
    public String data_clear() {
        logger.info("#CTL      ~ database/clear");
        return "database/data_clear";
    }

    @RequestMapping("/clearSwipeRecord")
    @ResponseBody
    public String clearSwipeRecord(HttpServletRequest request) {
        logger.info("#CTL      ~ database/clearSwipeRecord");
        String str=request.getParameter("rows");
        List<DeviceStatus> deviceStatusList = null;
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            deviceStatusList=objectMapper.readValue(str,List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (DeviceStatus deviceStatus:deviceStatusList){
            System.out.print(deviceStatus);
        }
        return null;
    }
}