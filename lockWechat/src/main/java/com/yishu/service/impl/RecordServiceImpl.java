/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.*;
import com.yishu.service.IRecordService;
import com.yishu.util.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-14 16:20 admin
 * @since JDK1.7
 */
@Service("recordService")
public class RecordServiceImpl implements IRecordService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("RecordServiceImpl");
    int reqSign;
    String reqData;
    /**
     * https数据请求,获取的原数据
     */
    String rawData;
    ObjectMapper objectMapper=new ObjectMapper();
    JsonNode rootNode= null;
    /**
     * https数据请求,成功(0)与失败(1)的标志
     */
    int respSign;

    private int respSign(){
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respSign=rootNode.path("result").asInt();
//        logger.info("respSign:"+String.valueOf(respSign));
        return respSign;
    }
    @Override
    public List<UnlockRecord> getUnlockRecord(String ownerPhoneNumber, String startTime, String endTime) {
        reqSign=26;
        long startTimeL=Long.valueOf(startTime);
        long endTimeL=Long.valueOf(endTime);
        String startTimeReqParam=DateUtil.format2TillMin.format(new Date(startTimeL));
        String endTimeReqParam=DateUtil.format2TillMin.format(new Date(endTimeL));
//        try {
//            startTime=DateUtil.format1tillminStringToformat2tillminString(startTime);
//            endTime=DateUtil.format1tillminStringToformat2tillminString(endTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        logger.info("{ownerPhoneNumber:"+ownerPhoneNumber+",startTime:"+startTime+";endTime:"+endTime+"}");
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTimeReqParam+"\",\"endTime\":\""+endTimeReqParam+"\"}";
        Map resultMap=new HashMap();
        rawData = HttpUtil.httpsPostToQixu(reqData);
//        System.err.println(rawData);
//        logger.info("获取unlock record信息,HTTP结果: "+rawData);

        respSign();
        resultMap.put("result",respSign);
        if (0==respSign){
            //获取开锁记录成功.
//            String recordListTemp=rootNode.path("recordList").asText();
            String recordListTemp=rootNode.path("recordList").toString();//!!!toString多了引号
            List<UnlockRecord> recordList=null;
            try {
                recordList = objectMapper.readValue(recordListTemp, new TypeReference<List<UnlockRecord>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
            Collections.reverse(recordList);
            return recordList;
        }
        return null;
    }

    @Override
    public Records<UnlockRecord> getUnlockRecordPage(String ownerPhoneNumber, String startTime, String endTime, int pageNum, int pageSize) throws ParseException {
        final long startTimeL=Long.valueOf(startTime);
        final long endTimeL=Long.valueOf(endTime);
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        List<UnlockRecord> recordList2=new ArrayList<>();
        try {
            recordList=objectMapper.readValue(DataInject.readFile2String("classpath:recordList.json"),new TypeReference<List<UnlockRecord>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        //filter-recordList-Bytime,按时间过滤开锁记录.
        UnlockRecord unlockRecord=null;
        /*
        Date timetag=null;
        for(int i=0;i<recordList.size();i++){
            unlockRecord=recordList.get(i);
            timetag=DateUtil.format2.parse(unlockRecord.getTimetag());
            if (Long.valueOf(startTime)<timetag.getTime() && Long.valueOf(endTime)>timetag.getTime()){
                recordList2.add(unlockRecord);
            }
        }
        */
        recordList2=FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                Date timetag= null;
                long timetagL=0;
                try {
                    timetag = DateUtil.format2.parse(unlockRecord.getTimetag());
                    timetagL=timetag.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                return Long.valueOf(startTime)<timetag.getTime() && Long.valueOf(endTime)>timetag.getTime();
                return startTimeL<timetagL && endTimeL>timetagL;
            }
        });
        //reverse-recordList,开锁记录的List元素顺序反转(让结果集中timetag降序).
        Collections.reverse(recordList2);
        //page-recordList-By_pageNum&pageSize,为达到分页效果而截取总结果集中片段.
        List<UnlockRecord> newRecordList=null;
        int recordList2Size=recordList2.size();
        if (recordList2Size > pageNum*pageSize){
            newRecordList=recordList2.subList((pageNum-1)*pageSize,pageNum*pageSize);
        }else if (recordList2Size > (pageNum-1)*pageSize && recordList2Size <= pageNum*pageSize){
            newRecordList=recordList2.subList((pageNum-1)*pageSize,recordList2Size);
        }
        if (null==newRecordList){
            newRecordList=new ArrayList<>();
        }
        Records<UnlockRecord> records =new Records<>();
        records.setTotalSize(recordList2Size);
        records.setRows(newRecordList);

        return records;
    }

    @Override
    public Map<String,UnlockRecord> getUnlockRecordLock(String ownerPhoneNumber, String startTime, String endTime, String lockCodeParam) {
        List<UnlockRecord> rawUnlockRecordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        UnlockRecord unlockRecord=null;
        Device device=null;
        Lock lock=null;
        List allDeviceRecords=new ArrayList();
        Map<String,Map> deviceRecordsMap=null;
        Map<String,List> lockRecordsMap=null;
//        Map<String,Map> deviceRecords=null;
//        List deviceRecords=null;
        List lockRecords=null;
        UnlockRecordInDevice unlockRecordInDevice=null;
        UnlockRecordInLock unlockRecordInLock=null;
        String gatewayCode=null;
        String lockCode=null;
        for (int i=0;i<rawUnlockRecordList.size();i++){
        }
//        unlockRecordInLock.setLock();
        return null;
    }
    @Override
    public Map getUnlockRecordDevice(String ownerPhoneNumber, String startTime, String endTime) {
        List<UnlockRecord> rawUnlockRecordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        UnlockRecord unlockRecord=null;
        String gatewayCode=null;
        String lockCode=null;
        List<UnlockRecord> unlockRecordList=null;
        Map<String,Map> deviceRecordsMap=null;//deviceRecordsMap:{String:gatewayCode,Map:lockRecordsMap}
        Map<String,List> lockRecordsMap=null;//lockRecordsMap:{String:lockCode,List:unlockRecordList}

        for (int i=0;i<rawUnlockRecordList.size();i++){
            unlockRecord=rawUnlockRecordList.get(i);
            gatewayCode=unlockRecord.getGatewayCode();
            lockCode=unlockRecord.getLockCode();
            if (null==deviceRecordsMap.get(gatewayCode)){
                unlockRecordList=new ArrayList<UnlockRecord>();
                unlockRecordList.add(unlockRecord);
                lockRecordsMap=new HashMap<String,List>();
                lockRecordsMap.put(lockCode,unlockRecordList);
                deviceRecordsMap.put(gatewayCode,lockRecordsMap);
            }else {
                lockRecordsMap=deviceRecordsMap.get(gatewayCode);
                if (null==lockRecordsMap.get(lockCode)){
                    unlockRecordList=new ArrayList<UnlockRecord>();
                    unlockRecordList.add(unlockRecord);
                    lockRecordsMap.put(lockCode,unlockRecordList);
                }else {
                    lockRecordsMap.get(lockCode).add(unlockRecord);
                }
            }
        }
        return deviceRecordsMap;
    }

}
