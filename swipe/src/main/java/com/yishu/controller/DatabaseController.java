package com.yishu.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/data_clear")
    @ResponseBody
    public String data_clear(HttpServletRequest request) {
        logger.info("#CTL      ~ database/data_clear");

        return "database/data_clear";
    }
}