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
        long startTimeL=Long.parseLong(startTime);
        long endTimeL=Long.parseLong(endTime);
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

    /*
    @Override
    public List<UnlockRecord> getUnlockRecord(String ownerPhoneNumber, String startTime, String endTime) {
        final long startTimeL=Long.parseLong(startTime);
        final long endTimeL=Long.parseLong(endTime);
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
//                return Long.parseLong(startTime)<timetag.getTime() && Long.parseLong(endTime)>timetag.getTime();
                return startTimeL<timetagL && endTimeL>timetagL;
            }
        });
        //reverse-recordList,开锁记录的List元素顺序反转(让结果集中timetag降序).
        Collections.reverse(recordList2);
        return recordList2;
    }
    */

    @Override
    public Records<UnlockRecord> getUnlockRecordPage(String ownerPhoneNumber, String startTime, String endTime, int pageNum, int pageSize) {
        final long startTimeL=Long.parseLong(startTime);
        final long endTimeL=Long.parseLong(endTime);
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
            if (Long.parseLong(startTime)<timetag.getTime() && Long.parseLong(endTime)>timetag.getTime()){
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
//                return Long.parseLong(startTime)<timetag.getTime() && Long.parseLong(endTime)>timetag.getTime();
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
    public Records<UnlockRecord> getGatewayUnlockRecordPage(String ownerPhoneNumber, String startTime, String endTime, final String gatewayCode, int pageNum, int pageSize) {
        final long startTimeL=Long.parseLong(startTime);
        final long endTimeL=Long.parseLong(endTime);
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        List<UnlockRecord> recordList2=new ArrayList<>();
        try {
            recordList=objectMapper.readValue(DataInject.readFile2String("classpath:recordList.json"),new TypeReference<List<UnlockRecord>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        //filter-recordList-Bytime,按时间&网关过滤开锁记录.
        UnlockRecord unlockRecord=null;
        recordList2=FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                if ( !gatewayCode.equals(unlockRecord.getGatewayCode()) ){
                    return false;
                }
                Date timetag= null;
                long timetagL=0;
                try {
                    timetag = DateUtil.format2.parse(unlockRecord.getTimetag());
                    timetagL=timetag.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
    public Records<UnlockRecord> getLockUnlockRecordPage(String ownerPhoneNumber, String startTime, String endTime, final String lockCode, int pageNum, int pageSize) {
        final long startTimeL=Long.parseLong(startTime);
        final long endTimeL=Long.parseLong(endTime);
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        List<UnlockRecord> recordList2=new ArrayList<>();
        try {
            recordList=objectMapper.readValue(DataInject.readFile2String("classpath:recordList.json"),new TypeReference<List<UnlockRecord>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        //filter-recordList-Bytime,按时间&网关过滤开锁记录.
        UnlockRecord unlockRecord=null;
        recordList2=FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                if ( !lockCode.equals(unlockRecord.getLockCode()) ){
                    return false;
                }
                Date timetag= null;
                long timetagL=0;
                try {
                    timetag = DateUtil.format2.parse(unlockRecord.getTimetag());
                    timetagL=timetag.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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

    /**
     * 重新封装开锁记录,按设备(网关/门锁)拆分.
     *
     * @return
     */
    @Override
    /*
    public Map getUnlockRecordDevice(String ownerPhoneNumber, String startTime, String endTime) {
        List<UnlockRecord> rawUnlockRecordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        UnlockRecord unlockRecord=null;
        String gatewayCode=null;
        String lockCode=null;
        List<UnlockRecord> unlockRecordList=null;
        Map<String,Map> deviceRecordsMap=null;//deviceRecordsMap:{String:gatewayCode,Map:lockRecordsMap}
        Map<String,List> lockRecordsMap=null;//lockRecordsMap:{String:lockCode,List:unlockRecordList}

        deviceRecordsMap= new HashMap<>();
        for (int i=0;i<rawUnlockRecordList.size();i++){
            unlockRecord=rawUnlockRecordList.get(i);
            gatewayCode=unlockRecord.getGatewayCode();
            lockCode=unlockRecord.getLockCode();
            if (!deviceRecordsMap.containsKey(gatewayCode)){
                unlockRecordList=new ArrayList<UnlockRecord>();
                unlockRecordList.add(unlockRecord);
                lockRecordsMap=new HashMap<String,List>();
                lockRecordsMap.put(lockCode,unlockRecordList);
                deviceRecordsMap.put(gatewayCode,lockRecordsMap);
            }else {
                lockRecordsMap=deviceRecordsMap.get(gatewayCode);
                if (!lockRecordsMap.containsKey(lockCode)){
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
    */
    public Map getUnlockRecordDevice(String ownerPhoneNumber, String startTime, String endTime) {
        List<UnlockRecord> rawUnlockRecordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        UnlockRecord unlockRecord=null;
        String gatewayCode=null;
        String lockCode=null;
        List<UnlockRecord> unlockRecordList=null;
        Map<String,DataWithSize> deviceRecordsMap=null;//deviceRecordsMap:{key-String:gatewayCode,value-DataWithSize:dataWithSizeGatewayRecords}
        DataWithSize<Map> dataWithSizeGatewayRecords=null;//dataWithSize:(key:gatewayCode,size:,data<X-Map>:lockRecordsMap)
        Map<String,DataWithSize> lockRecordsMap=null;//lockRecordsMap:{key-String:lockCode,value-DataWithSize:dataWithSizeLockRecords}
        DataWithSize<List> dataWithSizeLockRecords=null;//dataWithSize:(key:lockCode,size:,data<X-List>:unlockRecordList)

        deviceRecordsMap= new HashMap<>();
        for (int i=0;i<rawUnlockRecordList.size();i++){
            unlockRecord=rawUnlockRecordList.get(i);
            gatewayCode=unlockRecord.getGatewayCode();
            lockCode=unlockRecord.getLockCode();
            if (!deviceRecordsMap.containsKey(gatewayCode)){

                unlockRecordList=new ArrayList<UnlockRecord>();
                unlockRecordList.add(unlockRecord);

                dataWithSizeLockRecords=new DataWithSize();
                dataWithSizeLockRecords.setKey(lockCode);
                dataWithSizeLockRecords.setData(unlockRecordList);

                lockRecordsMap=new HashMap<String,DataWithSize>();
                lockRecordsMap.put(lockCode,dataWithSizeLockRecords);

                dataWithSizeGatewayRecords=new DataWithSize();
                dataWithSizeGatewayRecords.setKey(gatewayCode);
                dataWithSizeGatewayRecords.setData(lockRecordsMap);

                deviceRecordsMap.put(gatewayCode,dataWithSizeGatewayRecords);
            }else {
                dataWithSizeGatewayRecords=deviceRecordsMap.get(gatewayCode);
                lockRecordsMap=dataWithSizeGatewayRecords.getData();
                if (!lockRecordsMap.containsKey(lockCode)){
                    unlockRecordList=new ArrayList<UnlockRecord>();
                    unlockRecordList.add(unlockRecord);

                    dataWithSizeLockRecords=new DataWithSize();
                    dataWithSizeLockRecords.setKey(lockCode);
                    dataWithSizeLockRecords.setData(unlockRecordList);

                    lockRecordsMap.put(lockCode,dataWithSizeLockRecords);
                }else {
                    dataWithSizeLockRecords=lockRecordsMap.get(lockCode);
                    (dataWithSizeLockRecords.getData()).add(unlockRecord);
                }
            }
        }
        int lockRecordsSize=0;
        int gatewayRecordsSize=0;
        for (Map.Entry<String, DataWithSize> entryX : deviceRecordsMap.entrySet()) {
//            System.out.println("key= " + entryX.getKey() + " and value= " + entryX.getValue());
            dataWithSizeGatewayRecords=entryX.getValue();
            lockRecordsMap=dataWithSizeGatewayRecords.getData();
            gatewayRecordsSize=0;
            for (Map.Entry<String, DataWithSize> entryY : lockRecordsMap.entrySet()) {
                dataWithSizeLockRecords=entryY.getValue();
                lockRecordsSize=dataWithSizeLockRecords.getData().size();
                dataWithSizeLockRecords.setSize(lockRecordsSize);
                gatewayRecordsSize+=lockRecordsSize;
            }
            dataWithSizeGatewayRecords.setSize(gatewayRecordsSize);
        }
        return deviceRecordsMap;
    }

    //未使用.引用的方法getUnlockRecordDevice()已被注释.
    @Override
    public Map getUnlockRecordDevicePage(String ownerPhoneNumber, String startTime, String endTime, int pageNum, int pageSize) {
        Map<String,Map> deviceRecordsMap=null;
        deviceRecordsMap=getUnlockRecordDevice(ownerPhoneNumber,startTime,endTime);
        Map<String,Map> subMap=new HashMap<>();
        List<Map> mapList=new ArrayList<>();
        int i = 0;
        int mapSize=deviceRecordsMap.size();
        for (String key : deviceRecordsMap.keySet()) {
            subMap.put(key, deviceRecordsMap.get(key));
            if ((i+1)%pageSize==0 || i==mapSize){
                mapList.add(subMap);
                subMap=new HashMap<>();
            }
            i++;
        }
        return mapList.get(pageNum-1);
    }

    /**
     * 重新封装开锁记录,按开锁人unlockRecord.cardInfo.cardNum拆分,丢弃密码开锁记录.
     * 目标数据结构: operatorRecordsMap:{key-String:operatorId,value-DataWithSize:dataWithSize} dataWithSize:(key:lockCode,size:,data<X-List>:unlockRecordList).
     *
     * @return
     */
    @Override
    /*
    public Map getUnlockOperator(String ownerPhoneNumber, String startTime, String endTime) {
        List<UnlockRecord> rawUnlockRecordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        UnlockRecord unlockRecord=null;
        UnlockRecord.CardInfo cardInfo=null;
        String cardNumb=null;
        List<UnlockRecord> unlockRecordList=null;
        Map<String,DataWithSize> operatorRecordsMap=null;//operatorRecordsMap:{key-String:operatorId,value-DataWithSize:dataWithSize}
        DataWithSize<List> dataWithSize=null;//dataWithSize:(key:lockCode,size:,data<X-List>:unlockRecordList)

        //数据处理step1: 遍历原数据集合rawList,封装成目标数据结构.
        operatorRecordsMap= new HashMap<>();
        for (int i=0;i<rawUnlockRecordList.size();i++){
            unlockRecord=rawUnlockRecordList.get(i);
            cardInfo=unlockRecord.getCardInfo();
            if (null != cardInfo){
                cardNumb=cardInfo.getCardNumb();
                if (!operatorRecordsMap.containsKey(cardNumb)){
                    unlockRecordList=new ArrayList<UnlockRecord>();
                    unlockRecordList.add(unlockRecord);

                    dataWithSize=new DataWithSize();
                    dataWithSize.setKey(cardNumb);
                    dataWithSize.setData(unlockRecordList);

                    operatorRecordsMap.put(cardNumb,dataWithSize);
                }else {
                    dataWithSize=operatorRecordsMap.get(cardNumb);

//                    unlockRecordList=dataWithSize.getData();
//                    unlockRecordList.add(unlockRecord);
//                    dataWithSize.setData(unlockRecordList);
                    dataWithSize.getData().add(unlockRecord);
                }
            }
        }
        //数据处理step2: 设置dataWithSize.size值.
        int recordSize=0;
        for (Map.Entry<String, DataWithSize> entryX : operatorRecordsMap.entrySet()) {
//            System.out.println("key= " + entryX.getKey() + " and value= " + entryX.getValue());
            dataWithSize=entryX.getValue();
            recordSize=dataWithSize.getData().size();
            dataWithSize.setSize(recordSize);
        }
        return operatorRecordsMap;
    }
     */
    public Map getUnlockOperator(String ownerPhoneNumber, String startTime, String endTime) {
        List<UnlockRecord> rawUnlockRecordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        UnlockRecord unlockRecord=null;
        UnlockRecord.CardInfo cardInfo=null;
        String cardNumb=null;
        String name=null;
        List<UnlockRecord> unlockRecordList=null;
        Map<String,DataWithNote> operatorRecordsMap=null;//operatorRecordsMap:{key-String:operatorId,value-DataWithNote:dataWithNote}
        DataWithNote<List,String> dataWithNote=null;//dataWithNote:(key:lockCode,size:,data<X-List>:unlockRecordList,note<Y-String>:(name))

        //数据处理step1: 遍历原数据集合rawList,封装成目标数据结构.
        operatorRecordsMap= new HashMap<>();
        for (int i=0;i<rawUnlockRecordList.size();i++){
            unlockRecord=rawUnlockRecordList.get(i);
            cardInfo=unlockRecord.getCardInfo();
            if (null != cardInfo){
                cardNumb=cardInfo.getCardNumb();
                name=cardInfo.getName();
                if (!operatorRecordsMap.containsKey(cardNumb)){
                    unlockRecordList=new ArrayList<UnlockRecord>();
                    unlockRecordList.add(unlockRecord);

                    dataWithNote=new DataWithNote<List,String>();
                    dataWithNote.setKey(cardNumb);
                    dataWithNote.setData(unlockRecordList);
                    dataWithNote.setNote(name);

                    operatorRecordsMap.put(cardNumb,dataWithNote);
                }else {
                    dataWithNote=operatorRecordsMap.get(cardNumb);

//                    unlockRecordList=dataWithSize.getData();
//                    unlockRecordList.add(unlockRecord);
//                    dataWithSize.setData(unlockRecordList);
                    dataWithNote.getData().add(unlockRecord);
                }
            }
        }
        //数据处理step2: dataWithNote.size值.
        int recordSize=0;
        for (Map.Entry<String, DataWithNote> entryX : operatorRecordsMap.entrySet()) {
//            System.out.println("key= " + entryX.getKey() + " and value= " + entryX.getValue());
            dataWithNote=entryX.getValue();
            recordSize=dataWithNote.getData().size();
            dataWithNote.setSize(recordSize);
        }
        return operatorRecordsMap;
    }

    @Override
    public Records<UnlockRecord> getOperatorUnlockRecordPage(String ownerPhoneNumber, String startTime, String endTime, final String cardNum, int pageNum, int pageSize) {
        final long startTimeL=Long.parseLong(startTime);
        final long endTimeL=Long.parseLong(endTime);
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        List<UnlockRecord> recordList2=new ArrayList<>();
        try {
            recordList=objectMapper.readValue(DataInject.readFile2String("classpath:recordList.json"),new TypeReference<List<UnlockRecord>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        //filter-recordList-Bytime,按时间&身份证号码cardNumb过滤开锁记录.
        UnlockRecord unlockRecord=null;
        recordList2=FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                UnlockRecord.CardInfo cardInfo=unlockRecord.getCardInfo();
                if ( null==cardInfo || !cardNum.equals(cardInfo.getCardNumb()) ){
                    return false;
                }
                Date timetag= null;
                long timetagL=0;
                try {
                    timetag = DateUtil.format2.parse(unlockRecord.getTimetag());
                    timetagL=timetag.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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

}
