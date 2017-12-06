/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yishu.pojo.Device;
import com.yishu.pojo.Lock;
import com.yishu.pojo.Records;
import com.yishu.pojo.UnlockRecord;
import com.yishu.service.IRecordService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-14 15:30 admin
 * @since JDK1.7
 */
public class RecordAction extends ActionSupport{
    public RecordAction() {System.out.println(">>>Initialization RecordAction......................................");}
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("RecordAction");
    @Autowired
    private IRecordService recordService;
    /**
     * Object jsonResult——返回的JSON格式的Model
     */
    private Object jsonResult;
    public Object getJsonResult() {
        return jsonResult;
    }

    private String ownerPhoneNumber;
    private String startTime;//String类型的毫秒数(距离1970-01-01 08:00:00的时间差毫秒表示),需要先转换为long.
    private String endTime;
    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }
    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int pageNum;
    public int pageSize;
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取用户ownerPhoneNumber的开锁记录
     *
     */
    public String getUnlockRecord() throws ParseException {
        List<UnlockRecord> recordList=recordService.getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        jsonResult=recordList;
        return "json";
    }

    /**
     * 获取用户ownerPhoneNumber的开锁记录,再将数据分页.
     *
     * @param pageNum 第几页的页码数字.
     * @param pageSize 每页可以展示多少条记录的数目.
     *
     */
    public String getUnlockRecordPage() throws ParseException {
//        logger.info("{ownerPhoneNumber:"+ownerPhoneNumber+",startTime:"+startTime+";endTime:"+endTime+",pageNum:"+pageNum+",pageSize:"+pageSize+"}");
        Records<UnlockRecord> records=recordService.getUnlockRecordPage(ownerPhoneNumber,startTime,endTime,pageNum,pageSize);
        jsonResult=records;
//        logger.info("records: { totalSize: "+records.getTotalSize()+"rowsSize:"+records.getRows().size()+", rows: "+records.getRows()+"}");
        return "json";
    }

    public String getUnlockRecordDevice() {
        Map recordMap=recordService.getUnlockRecordDevice(ownerPhoneNumber,startTime,endTime);
        jsonResult=recordMap;
        return "json";
    }
}