/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.DataWithNote;
import com.yishu.pojo.DataWithSize;
import com.yishu.pojo.Records;
import com.yishu.pojo.UnlockRecord;
import com.yishu.service.IRecordService;
import com.yishu.util.DateUtil;
import com.yishu.util.FilterList;
import com.yishu.util.FilterListHook;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-14 16:20 admin
 * @since JDK1.7
 */
@Service("recordService")
public class RecordServiceImpl implements IRecordService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(RecordServiceImpl.class);
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
//        LOG.info("respSign:"+String.valueOf(respSign));
        return respSign;
    }

    @Override
    public List<UnlockRecord> getUnlockRecord(String ownerPhoneNumber, String startTime, String endTime) {
        reqSign=26;
        long startTimeL=Long.parseLong(startTime);
        long endTimeL=Long.parseLong(endTime);
        String startTimeReqParam= DateUtil.yyyyMMddHHmm.format(new Date(startTimeL));
        String endTimeReqParam= DateUtil.yyyyMMddHHmm.format(new Date(endTimeL));
//        try {
//            startTime=DateUtil.format1tillminStringToformat2tillminString(startTime);
//            endTime=DateUtil.format1tillminStringToformat2tillminString(endTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        LOG.info("{ownerPhoneNumber:"+ownerPhoneNumber+",startTime:"+startTime+";endTime:"+endTime+"}");
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTimeReqParam+"\",\"endTime\":\""+endTimeReqParam+"\"}";
        LOG.info("reqData : "+reqData);
        rawData = HttpUtil.httpsPostToQixu(reqData);
//        LOG.info("rawData : "+rawData);

        respSign();
        Map resultMap=new HashMap();
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
    public List<UnlockRecord> getUnlockRecord(String ownerPhoneNumber, Date startDate, Date endDate) {
        reqSign=26;
        long startTimeL=startDate.getTime();
        long endTimeL=endDate.getTime();
        String startTimeReqParam= DateUtil.yyyyMMddHHmm.format(new Date(startTimeL));
        String endTimeReqParam= DateUtil.yyyyMMddHHmm.format(new Date(endTimeL));
        LOG.info("{ownerPhoneNumber:"+ownerPhoneNumber+",startDate:"+startDate+";endDate:"+endDate+"}");
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTimeReqParam+"\",\"endTime\":\""+endTimeReqParam+"\"}";
        LOG.info("reqData : "+reqData);
        rawData = HttpUtil.httpsPostToQixu(reqData);
//        LOG.info("rawData : "+rawData);

        respSign();
        Map resultMap=new HashMap();
        resultMap.put("result",respSign);
        if (0==respSign){
            //获取开锁记录成功.
            String recordListTemp=rootNode.path("recordList").asText();
//            String recordListTemp=rootNode.path("recordList").toString();//!!!toString多了引号
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
                    timetag = DateUtil.yyyyMMddHHmmss.parse(unlockRecord.getTimetag());
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
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        recordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        //reverse-recordList,开锁记录的List元素顺序反转(让结果集中timetag降序).
//        Collections.reverse(recordList);
        //page-recordList-By_pageNum&pageSize,为达到分页效果而截取总结果集中片段.
        List<UnlockRecord> newRecordList=null;
        int recordListSize=recordList.size();
        if (recordListSize > pageNum*pageSize){
            newRecordList=recordList.subList((pageNum-1)*pageSize,pageNum*pageSize);
        }else if (recordListSize > (pageNum-1)*pageSize && recordListSize <= pageNum*pageSize){
            newRecordList=recordList.subList((pageNum-1)*pageSize,recordListSize);
        }
        if (null==newRecordList){
            newRecordList=new ArrayList<>();
        }
        Records<UnlockRecord> records =new Records<>();
        records.setTotalSize(recordListSize);
        records.setRows(newRecordList);

        return records;
    }

    @Override
    public Records<UnlockRecord> getGatewayUnlockRecordPage(String ownerPhoneNumber, String startTime, String endTime, final String gatewayCode, int pageNum, int pageSize) {
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        recordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        List<UnlockRecord> recordList2=null;
        //filter-recordList-Bytime,按网关过滤开锁记录.
        recordList2= FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                return gatewayCode.equals(unlockRecord.getGatewayCode());
            }
        });
        //reverse-recordList,开锁记录的List元素顺序反转(让结果集中timetag降序).
//        Collections.reverse(recordList2);
        //page-recordList-By_pageNum&pageSize,为达到分页效果而截取总结果集中片段.
        List<UnlockRecord> recordList3=null;
        int recordList2Size=recordList2.size();
        if (recordList2Size > pageNum*pageSize){
            recordList3=recordList2.subList((pageNum-1)*pageSize,pageNum*pageSize);
        }else if (recordList2Size > (pageNum-1)*pageSize && recordList2Size <= pageNum*pageSize){
            recordList3=recordList2.subList((pageNum-1)*pageSize,recordList2Size);
        }
        if (null==recordList3){
            recordList3=new ArrayList<>();
        }
        Records<UnlockRecord> records =new Records<>();
        records.setTotalSize(recordList2Size);
        records.setRows(recordList3);

        return records;
    }

    @Override
    public Records<UnlockRecord> getLockUnlockRecord(String ownerPhoneNumber, String startTime, String endTime, final String lockCode) {
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        recordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        //filter-recordList-Bytime,按时间&门锁过滤开锁记录.
        List<UnlockRecord> recordList2=null;
        recordList2= FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                return lockCode.equals(unlockRecord.getLockCode());
            }
        });
        //reverse-recordList,开锁记录的List元素顺序反转(让结果集中timetag降序).
//        Collections.reverse(recordList2);
        Records<UnlockRecord> records =new Records<>();
        records.setTotalSize(recordList2.size());
        records.setRows(recordList2);

        return records;
    }

    @Override
    public Records<UnlockRecord> getLockUnlockRecord(String ownerPhoneNumber, Date startDate, Date endDate, final String lockCode) {
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        recordList=getUnlockRecord(ownerPhoneNumber,startDate,endDate);
        //filter-recordList-Bytime,按时间&门锁过滤开锁记录.
        List<UnlockRecord> recordList2=null;
        recordList2= FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                return lockCode.equals(unlockRecord.getLockCode());
            }
        });
        //reverse-recordList,开锁记录的List元素顺序反转(让结果集中timetag降序).
//        Collections.reverse(recordList2);
        Records<UnlockRecord> records =new Records<>();
        records.setTotalSize(recordList2.size());
        records.setRows(recordList2);
        return records;
    }

    @Override
    public Records<UnlockRecord>[] getLockUnlockRecordDaily(String ownerPhoneNumber, Date theDate, final String lockCode) {
        int days= 16;
        Calendar calendar=Calendar.getInstance();
        Date endDate=theDate;
        final long endMoment=endDate.getTime();
        calendar.setTime(theDate);
        calendar.add(Calendar.DAY_OF_MONTH,-15);
        Date startDate=calendar.getTime();
        final long startMoment=startDate.getTime();

        Date[] dateArr=new Date[days];
        for(int i=days-1;i>=0;i--){
            calendar.setTime(theDate);
            calendar.add(Calendar.DAY_OF_MONTH,-i);
            dateArr[i]=calendar.getTime();
        }
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        recordList=getUnlockRecord(ownerPhoneNumber,startDate,endDate);
        //filter-recordList-Bytime,按时间&门锁过滤开锁记录.
        List<UnlockRecord> recordList2=null;
        recordList2= FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                return lockCode.equals(unlockRecord.getLockCode());
            }
        });
        //reverse-recordList,开锁记录的List元素顺序反转(让结果集中timetag降序).
//        Collections.reverse(recordList2);
        List<UnlockRecord> dailyRecordList=null;
//        dailyRecordList= FilterList.filter(recordList2, new FilterListHook<UnlockRecord>() {
//            @Override
//            public boolean test(UnlockRecord unlockRecord) {
//                String dateStr=unlockRecord.getTimetag();
//                try {
//                    long time=DateUtil.yyyyMMddHHmmss.parse(dateStr).getTime();
//                    if(time>=startMoment && time<=endMoment){
//                        return true;
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//        });
        Records<UnlockRecord>[] dailyRecords=new Records[days];
        for(int i=0;i<days;i++){
            dailyRecords[days]=new Records<UnlockRecord>();
        }
        UnlockRecord unlockRecord;
        long startTimeTemp;
        long endTimeTemp;
        int index = 0;
        for(int i=0;i<recordList2.size();i++){
            unlockRecord=recordList2.get(i);
            String dateStr=unlockRecord.getTimetag();
            long time= 0;
            try {
                time = DateUtil.yyyyMMddHHmmss.parse(dateStr).getTime();
                if(time>=startMoment && time<=endMoment){
                    index= (int) ((time-startMoment)/86400000);
                }
                dailyRecords[index].getRows().add(unlockRecord);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<days;i++){
            dailyRecords[i].setTotalSize(dailyRecords[i].getRows().size());
        }
        return dailyRecords;
    }

    @Override
    public Records<UnlockRecord> getLockUnlockRecordPage(String ownerPhoneNumber, String startTime, String endTime, final String lockCode, int pageNum, int pageSize) {
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        recordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        //filter-recordList-Bytime,按时间&门锁过滤开锁记录.
        List<UnlockRecord> recordList2=null;
        recordList2= FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                return lockCode.equals(unlockRecord.getLockCode());
            }
        });
        //reverse-recordList,开锁记录的List元素顺序反转(让结果集中timetag降序).
//        Collections.reverse(recordList2);
        //page-recordList-By_pageNum&pageSize,为达到分页效果而截取总结果集中片段.
        List<UnlockRecord> recordList3=null;
        int recordList2Size=recordList2.size();
        if (recordList2Size > pageNum*pageSize){
            recordList3=recordList2.subList((pageNum-1)*pageSize,pageNum*pageSize);
        }else if (recordList2Size > (pageNum-1)*pageSize && recordList2Size <= pageNum*pageSize){
            recordList3=recordList2.subList((pageNum-1)*pageSize,recordList2Size);
        }
        if (null==recordList3){
            recordList3=new ArrayList<>();
        }
        Records<UnlockRecord> records =new Records<>();
        records.setTotalSize(recordList2Size);
        records.setRows(recordList3);

        return records;
    }

    /**
     * 重新封装开锁记录,按设备(网关/门锁)拆分.
     *
     * @return
     */
    @Override
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
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        recordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        //filter-recordList-Bytime,按身份证号码cardNumb过滤开锁记录.
        List<UnlockRecord> recordList2=null;
        recordList2= FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                UnlockRecord.CardInfo cardInfo=unlockRecord.getCardInfo();
                if (null!=cardInfo){
                    return cardNum.equals(cardInfo.getCardNumb());
                }
                return false;
            }
        });
        //reverse-recordList,开锁记录的List元素顺序反转(让结果集中timetag降序).
//        Collections.reverse(recordList2);
        //page-recordList-By_pageNum&pageSize,为达到分页效果而截取总结果集中片段.
        List<UnlockRecord> recordList3=null;
        int recordList2Size=recordList2.size();
        if (recordList2Size > pageNum*pageSize){
            recordList3=recordList2.subList((pageNum-1)*pageSize,pageNum*pageSize);
        }else if (recordList2Size > (pageNum-1)*pageSize && recordList2Size <= pageNum*pageSize){
            recordList3=recordList2.subList((pageNum-1)*pageSize,recordList2Size);
        }
        if (null==recordList3){
            recordList3=new ArrayList<>();
        }
        Records<UnlockRecord> records =new Records<>();
        records.setTotalSize(recordList2Size);
        records.setRows(recordList3);

        return records;
    }

}
