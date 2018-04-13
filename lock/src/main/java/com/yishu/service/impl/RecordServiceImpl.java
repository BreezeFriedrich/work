/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.*;
import com.yishu.service.IDeviceService;
import com.yishu.service.IRecordService;
import com.yishu.util.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("RecordServiceImpl");
    @Autowired
    private IDeviceService deviceService;
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
        return respSign;
    }

    @Override
    public List<UnlockRecord> getUnlockRecord(String ownerPhoneNumber, final long startTime, final long endTime) {
        reqSign=26;
        String startTimeReqParam=DateUtil.yyyyMMddHHmm.format(new Date(startTime));
        String endTimeReqParam=DateUtil.yyyyMMddHHmm.format(new Date(endTime));
        LOG.info("{ownerPhoneNumber:"+ownerPhoneNumber+",startTime:"+startTime+";endTime:"+endTime+"}");
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTimeReqParam+"\",\"endTime\":\""+endTimeReqParam+"\"}";
        LOG.info("reqData:"+reqData);
        rawData = HttpUtil.httpsPostToGateway(reqData);
//        LOG.info("rawData:"+rawData);

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
    /*
    @Override
    public List<UnlockRecord> getUnlockRecord(String ownerPhoneNumber, final long startTime, final long endTime) {
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        List<UnlockRecord> recordList2=null;
        try {
            recordList=objectMapper.readValue(DataInject.readFile2String("classpath:recordList.json"),new TypeReference<List<UnlockRecord>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(null==recordList){
            return null;
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
                return startTime<timetagL && endTime>timetagL;
            }
        });
        Map gatewaysAndLocks=deviceService.getGatewaysAndLocks(ownerPhoneNumber);
        HashMap gatewayMap=(HashMap)gatewaysAndLocks.get("gateways");
        HashMap lockMap=(HashMap)gatewaysAndLocks.get("locks");
        Gateway gateway=null;
        Lock lock=null;
        Iterator itr=recordList2.iterator();
        while (itr.hasNext()){
            unlockRecord= (UnlockRecord) itr.next();
            if(null!=gatewayMap.get(unlockRecord.getGatewayCode())){
                gateway= (Gateway) gatewayMap.get(unlockRecord.getGatewayCode());
                unlockRecord.setGatewayName(gateway.getGatewayName());
            }
            if(null!=lockMap.get(unlockRecord.getLockCode())){
                lock= (Lock) lockMap.get(unlockRecord.getLockCode());
                unlockRecord.setLockName(lock.getLockName());
            }
        }
        return recordList2;
    }
    */

    @Override
    public List<UnlockRecord> getUnlockRecordFilter(String ownerPhoneNumber, long startTime, long endTime, final Map<String,Object> filterparamMap) {
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        recordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        if(null==recordList){
            return null;
        }
        //filter过滤开锁记录.
        List<UnlockRecord> recordList2=null;
        recordList2= FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                String property=null;
                boolean eligible=true;
                //遍历filterparamMap
                for (Map.Entry<String, Object> entry : filterparamMap.entrySet()) {
//                    System.out.println(entry.getKey() + ":" + entry.getValue());
                    if (!eligible){
                        break;//跳出对于filterparamMap的for循环遍历
                    }
                    if(eligible){
                        property=entry.getKey();
                        switch (property) {
                            case "lockCode":
                                eligible = ((String)entry.getValue()).equals(unlockRecord.getLockCode());
                                break;
                            case "gatewayCode":
                                eligible = ((String)entry.getValue()).equals(unlockRecord.getGatewayCode());
                                break;
                            case "openMode":
                                eligible = ((String)entry.getValue()).equals(unlockRecord.getOpenMode());
                                break;
                            case "period":
                                long[] period= (long[]) entry.getValue();
                                try {
                                    long time = DateUtil.yyyyMMddHHmmss.parse(unlockRecord.getTimetag()).getTime();
                                    eligible = period[0]<time && period[1]>time;
                                } catch (ParseException e) {
                                    eligible=false;
                                    e.printStackTrace();
                                }
                                break;
                            case "name":
                                eligible = ((String)entry.getValue()).equals(unlockRecord.getCardInfo().getName());
                                break;
                            case "cardNumb":
                                eligible = ((String)entry.getValue()).equals(unlockRecord.getCardInfo().getCardNumb());
                                break;
                            case "password":
                                eligible = ((String)entry.getValue()).equals(unlockRecord.getPasswordInfo().getPassword());
                                break;
                            case "serviceNumb":
                                if(null!=unlockRecord.getCardInfo()){
                                    eligible = ((String)entry.getValue()).equals(unlockRecord.getCardInfo().getServiceNumb());
                                }else{
                                    eligible = ((String)entry.getValue()).equals(unlockRecord.getPasswordInfo().getServiceNumb());
                                }
                                break;
                            default:break;
                        }
                    }
                }
                return eligible;
            }
        });
        return recordList2;
    }

    @Override
    public List<UnlockRecord> filterUnlockRecord(List<UnlockRecord> unlockRecords, final Map<String, Object> filterparamMap) {
        if(filterparamMap.isEmpty()){
            return unlockRecords;
        }
        //filter过滤开锁记录.
        List<UnlockRecord> recordList=null;
        recordList= FilterList.filter(unlockRecords, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                String property=null;
                boolean eligible=true;
                //遍历filterparamMap
                for (Map.Entry<String, Object> entry : filterparamMap.entrySet()) {
//                    System.out.println(entry.getKey() + ":" + entry.getValue());
                    if (!eligible){
                        break;//跳出对于filterparamMap的for循环遍历
                    }
                    if(eligible){
                        property=entry.getKey();
                        switch (property) {
                            case "gatewayCode":
                                eligible = ((String)entry.getValue()).equals(unlockRecord.getGatewayCode());
                                break;
                            case "gatewayName":
                                if(null==unlockRecord.getGatewayName()){
                                    eligible=false;
                                }else {
                                    eligible = ((String)entry.getValue()).equals(unlockRecord.getGatewayName());
                                }
                                break;
                            case "lockCode":
                                eligible = ((String)entry.getValue()).equals(unlockRecord.getLockCode());
                                break;
                            case "lockName":
                                if(null==unlockRecord.getLockName()){
                                    eligible=false;
                                }else {
                                    eligible = ((String)entry.getValue()).equals(unlockRecord.getLockName());
                                }
                                break;
                            case "openMode":
                                eligible = (int)entry.getValue()==unlockRecord.getOpenMode();
                                break;
                            case "period":
                                long[] period= (long[]) entry.getValue();
                                try {
                                    long time = DateUtil.yyyyMMddHHmmss.parse(unlockRecord.getTimetag()).getTime();
                                    eligible = period[0]<time && period[1]>time;
                                } catch (ParseException e) {
                                    eligible=false;
                                    e.printStackTrace();
                                }
                                break;
                            case "name":
                                eligible = ((String)entry.getValue()).equals(unlockRecord.getCardInfo().getName());
                                break;
                            case "cardNumb":
                                eligible = ((String)entry.getValue()).equals(unlockRecord.getCardInfo().getCardNumb());
                                break;
                            case "password":
                                eligible = ((String)entry.getValue()).equals(unlockRecord.getPasswordInfo().getPassword());
                                break;
                            case "serviceNumb":
                                if(null!=unlockRecord.getCardInfo()){
                                    eligible = ((String)entry.getValue()).equals(unlockRecord.getCardInfo().getServiceNumb());
                                }else{
                                    eligible = ((String)entry.getValue()).equals(unlockRecord.getPasswordInfo().getServiceNumb());
                                }
                                break;
                            default:break;
                        }
                    }
                }
                return eligible;
            }
        });
        return recordList;
    }
/*
    @Override
    public List<UnlockRecord> orderUnlockRecord(List<UnlockRecord> unlockRecords, final Order order) {
        Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
            @Override
            public int compare(UnlockRecord o1, UnlockRecord o2) {
                int compareNum=1;
                switch (order.getOrderColumn()) {
                    case "gatewayCode":
                        compareNum=o1.getGatewayCode().compareTo(o2.getGatewayCode());
                        break;
                    case "lockCode":
                        compareNum=o1.getLockCode().compareTo(o2.getLockCode());
                        break;
                    case "openMode":
                        compareNum=o1.getOpenMode()-o2.getOpenMode();
                        break;
                    case "timestamp":
                        compareNum=o1.getTimetag().compareTo(o2.getTimetag());
                        break;
                    case "credential":
                        if(o1.getOpenMode()==o2.getOpenMode()){
                            if(null!=o1.getPasswordInfo() && null!=o2.getPasswordInfo()){
                                compareNum=o1.getPasswordInfo().getPassword().compareTo(o2.getPasswordInfo().getPassword());
                            }
                            if (null!=o1.getCardInfo() && null!=o2.getCardInfo()){
                                compareNum=o1.getCardInfo().getCardNumb().compareTo(o2.getCardInfo().getCardNumb());
                            }
                        }else{
                            compareNum=1;
                        }
                        break;
                    case "name":
                        break;
                    default:
                        break;
                }
                if ("asc".equals(order.getOrderDir())) {
                    return compareNum;
                }else{
                    return -1*compareNum;
                }
            }
        });
        return unlockRecords;
    }
*/
    @Override
    public List<UnlockRecord> orderUnlockRecord(List<UnlockRecord> unlockRecords, final Order order) {
        int directionInt=1;
        if (!"asc".equals(order.getOrderDir())) {
            directionInt=-1*directionInt;
        }
        final int finalDirectionInt = directionInt;
        switch (order.getOrderColumn()) {
            case "gatewayCode":
                Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                    @Override
                    public int compare(UnlockRecord o1, UnlockRecord o2) {
                        return finalDirectionInt *(o1.getGatewayCode().compareTo(o2.getGatewayCode()));
                    }
                });
                break;
            case "lockCode":
                Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                    @Override
                    public int compare(UnlockRecord o1, UnlockRecord o2) {
                        return finalDirectionInt *(o1.getLockCode().compareTo(o2.getLockCode()));
                    }
                });
                break;
            case "openMode":
                Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                    @Override
                    public int compare(UnlockRecord o1, UnlockRecord o2) {
                        return finalDirectionInt *(o1.getOpenMode()-o2.getOpenMode());
                    }
                });
                break;
            case "timestamp":
                Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                    @Override
                    public int compare(UnlockRecord o1, UnlockRecord o2) {
                        return finalDirectionInt *(o1.getTimetag().compareTo(o2.getTimetag()));
                    }
                });
                break;
            case "credential":
                Order orderTemp=new Order();
                orderTemp.setOrderColumn("openMode");
                orderTemp.setOrderDir("asc");
                orderUnlockRecord(unlockRecords,orderTemp);
                Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                    @Override
                    public int compare(UnlockRecord o1, UnlockRecord o2) {
                        if(o1.getOpenMode()==o2.getOpenMode()){
                            if(null!=o1.getPasswordInfo() && null!=o2.getPasswordInfo()){
                                return finalDirectionInt *(o1.getPasswordInfo().getPassword().compareTo(o2.getPasswordInfo().getPassword()));
                            }
                            if (null!=o1.getCardInfo() && null!=o2.getCardInfo()){
                                return finalDirectionInt *(o1.getCardInfo().getCardNumb().compareTo(o2.getCardInfo().getCardNumb()));
                            }
                        }
                        return 1;
                    }
                });
                break;
            case "name":
                Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                    @Override
                    public int compare(UnlockRecord o1, UnlockRecord o2) {
                        String name1;
                        if(null==o1.getCardInfo()){
                            name1="";
                        }else{
                            name1=o1.getCardInfo().getName();
                        }
                        String name2;
                        if(null==o2.getCardInfo()){
                            name2="";
                        }else{
                            name2=o2.getCardInfo().getName();
                        }
                        return finalDirectionInt *(name1.compareTo(name2));
                    }
                });
                break;
            default:
                break;
        }
        return unlockRecords;
    }
/*
    @Override
    public List<UnlockRecord> orderUnlockRecord(final List<UnlockRecord> unlockRecords, final List<Order> orderList) {
        for(final Order order:orderList) {
            Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                @Override
                public int compare(UnlockRecord o1, UnlockRecord o2) {
                    int compareNum=1;
                    switch (order.getOrderColumn()) {
                        case "gatewayCode":
                            compareNum=o1.getGatewayCode().compareTo(o2.getGatewayCode());
                            break;
                        case "lockCode":
                            compareNum=o1.getLockCode().compareTo(o2.getLockCode());
                            break;
                        case "openMode":
                            compareNum=o1.getOpenMode()-o2.getOpenMode();
                            break;
                        case "timestamp":
                            compareNum=o1.getTimetag().compareTo(o2.getTimetag());
                            break;
                        case "credential":
                            if(o1.getOpenMode()==o2.getOpenMode()){
                                if(null!=o1.getPasswordInfo() && null!=o2.getPasswordInfo()){
                                    compareNum=o1.getPasswordInfo().getPassword().compareTo(o2.getPasswordInfo().getPassword());
                                }
                                if (null!=o1.getCardInfo() && null!=o2.getCardInfo()){
                                    compareNum=o1.getCardInfo().getCardNumb().compareTo(o2.getCardInfo().getCardNumb());
                                }
                            }else{
                                compareNum=1;
                            }
                            break;
                        case "name":
                            String name1;
                            if(null==o1.getCardInfo()){
                                name1="";
                            }else{
                                name1=o1.getCardInfo().getName();
                            }
                            String name2;
                            if(null==o2.getCardInfo()){
                                name2="";
                            }else{
                                name2=o1.getCardInfo().getName();
                            }

                            compareNum=name1.compareTo(name2);
                            break;
                        default:
                            break;
                    }
                    if ("asc".equals(order.getOrderDir())) {
                        return compareNum;
                    }else{
                        return -1*compareNum;
                    }
                }
            });
        }
        return unlockRecords;
    }
*/
    @Override
    public List<UnlockRecord> orderUnlockRecord(final List<UnlockRecord> unlockRecords, final List<Order> orderList) {
        int directionInt=1;
        for(final Order order:orderList) {
            if (!"asc".equals(order.getOrderDir())) {
                directionInt=-1*directionInt;
            }
            final int finalDirectionInt = directionInt;
            switch (order.getOrderColumn()) {
                case "gatewayCode":
                    Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                        @Override
                        public int compare(UnlockRecord o1, UnlockRecord o2) {
                            return finalDirectionInt *(o1.getGatewayCode().compareTo(o2.getGatewayCode()));
                        }
                    });
                    break;
                case "lockCode":
                    Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                        @Override
                        public int compare(UnlockRecord o1, UnlockRecord o2) {
                            return finalDirectionInt *(o1.getLockCode().compareTo(o2.getLockCode()));
                        }
                    });
                    break;
                case "openMode":
                    Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                        @Override
                        public int compare(UnlockRecord o1, UnlockRecord o2) {
                            return finalDirectionInt *(o1.getOpenMode()-o2.getOpenMode());
                        }
                    });
                    break;
                case "timestamp":
                    Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                        @Override
                        public int compare(UnlockRecord o1, UnlockRecord o2) {
                            return finalDirectionInt *(o1.getTimetag().compareTo(o2.getTimetag()));
                        }
                    });
                    break;
                case "credential":
                    Order orderTemp=new Order();
                    orderTemp.setOrderColumn("openMode");
                    orderTemp.setOrderDir("asc");
                    orderUnlockRecord(unlockRecords,orderTemp);
                    Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                        @Override
                        public int compare(UnlockRecord o1, UnlockRecord o2) {
                            if(o1.getOpenMode()==o2.getOpenMode()){
                                if(null!=o1.getPasswordInfo() && null!=o2.getPasswordInfo()){
                                    return finalDirectionInt *(o1.getPasswordInfo().getPassword().compareTo(o2.getPasswordInfo().getPassword()));
                                }
                                if (null!=o1.getCardInfo() && null!=o2.getCardInfo()){
                                    return finalDirectionInt *(o1.getCardInfo().getCardNumb().compareTo(o2.getCardInfo().getCardNumb()));
                                }
                            }
                            return 1;
                        }
                    });
                    break;
                case "name":
                    Collections.sort(unlockRecords, new Comparator<UnlockRecord>() {
                        @Override
                        public int compare(UnlockRecord o1, UnlockRecord o2) {
                            String name1;
                            if(null==o1.getCardInfo()){
                                name1="";
                            }else{
                                name1=o1.getCardInfo().getName();
                            }
                            String name2;
                            if(null==o2.getCardInfo()){
                                name2="";
                            }else{
                                name2=o2.getCardInfo().getName();
                            }
                            return finalDirectionInt *(name1.compareTo(name2));
                        }
                    });
                    break;
                default:
                    break;
            }
        }
        return unlockRecords;
    }

    @Override
    public List<UnlockRecordTableData> orderUnlockRecordTableData(final List<UnlockRecordTableData> recordTableDataList, List<Order> orderList) {
        Collections.reverse(orderList);
        for(final Order order:orderList) {
            orderUnlockRecordTableData(recordTableDataList,order);
//            switch (order.getOrderColumn()) {
//                case "gatewayCode":
//                    Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
//                        @Override
//                        public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
//                            int compareNum=0;
//                            compareNum=o1.getGatewayCode().compareTo(o2.getGatewayCode());
//                            if ("asc".equals(order.getOrderDir())) {
//                                return compareNum;
//                            }else{
//                                return -1*compareNum;
//                            }
//                        }
//                    });
//                    break;
//                case "lockCode":
//                    Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
//                        @Override
//                        public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
//                            int compareNum=0;
//                            compareNum=o1.getLockCode().compareTo(o2.getLockCode());
//                            if ("asc".equals(order.getOrderDir())) {
//                                return compareNum;
//                            }else{
//                                return -1*compareNum;
//                            }
//                        }
//                    });
//                    break;
//                case "openMode":
//                    Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
//                        @Override
//                        public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
//                            int compareNum=0;
//                            compareNum=o1.getOpenMode()-o2.getOpenMode();
//                            if ("asc".equals(order.getOrderDir())) {
//                                return compareNum;
//                            }else{
//                                return -1*compareNum;
//                            }
//                        }
//                    });
//                    break;
//                case "timestamp":
//                    Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
//                        @Override
//                        public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
//                            int compareNum=0;
//                            compareNum=o1.getTimestamp().compareTo(o2.getTimestamp());
//                            if ("asc".equals(order.getOrderDir())) {
//                                return compareNum;
//                            }else{
//                                return -1*compareNum;
//                            }
//                        }
//                    });
//                    break;
//                case "credential":
////                    Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
////                        @Override
////                        public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
////                            int compareNum=0;
////                            compareNum=o1.getOpenMode()-o2.getOpenMode();
////                            return compareNum;
////                        }
////                    });
//                    Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
//                        @Override
//                        public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
//                            int compareNum=0;
//                            if(o1.getOpenMode()==o2.getOpenMode()){
//                                if(null!=o1.getCredential() && null!=o2.getCredential()){
//                                    compareNum=o1.getCredential().compareTo(o2.getCredential());
//                                }
//                                if(null==o1.getCredential() && null!=o2.getCredential()){
//                                    compareNum=1;
//                                }
//                            }
//                            if ("asc".equals(order.getOrderDir())) {
//                                return compareNum;
//                            }else{
//                                return -1*compareNum;
//                            }
//                        }
//                    });
//                    break;
//                case "name":
//                    Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
//                        @Override
//                        public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
//                            if(null!=o1.getName() && null!=o2.getName()){
//                                int compareNum=0;
//                                compareNum=o1.getName().compareTo(o2.getName());
//                                if ("asc".equals(order.getOrderDir())) {
//                                    return compareNum;
//                                }else{
//                                    return -1*compareNum;
//                                }
//                            }else {
//                                return 0;
//                            }
//                        }
//                    });
//                    break;
//                default:
//                    break;
//            }
        }
        return recordTableDataList;
    }
    /*
    @Override
    public List<UnlockRecordTableData> orderUnlockRecordTableData(final List<UnlockRecordTableData> unlockRecordTableDataList, List<Order> orderList) {
        for(final Order order:orderList) {
            Collections.sort(unlockRecordTableDataList, new Comparator<UnlockRecordTableData>() {
                @Override
                public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
                    int compareNum=0;
                    switch (order.getOrderColumn()) {
                        case "gatewayCode":
                            compareNum=o1.getGatewayCode().compareTo(o2.getGatewayCode());
                            break;
                        case "lockCode":
                            compareNum=o1.getLockCode().compareTo(o2.getLockCode());
                            break;
                        case "openMode":
                            compareNum=o1.getOpenMode()-o2.getOpenMode();
                            break;
                        case "timestamp":

//                            try {
//                                compareNum=DateUtil.yyyyMMddHHmmss.parse(o1.getTimetag()).getTime()-DateUtil.yyyyMMddHHmmss.parse(o2.getTimetag()).getTime();
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }

                            compareNum=o1.getTimestamp().compareTo(o2.getTimestamp());
                            break;
                        case "credential":
                            if(o1.getOpenMode()==o2.getOpenMode()){
                                if(null!=o1.getCredential() && null!=o2.getCredential()){
                                    compareNum=o1.getCredential().compareTo(o2.getCredential());
                                }
                                if(null==o1.getCredential() && null!=o2.getCredential()){
                                    compareNum=1;
                                }
                            }
                            else{
                                Order orderTemp=new Order();
                                orderTemp.setOrderColumn("openMode");
                                orderTemp.setOrderDir("asc");
                                List<Order> listTemp=new ArrayList<>(1);
                                listTemp.add(orderTemp);
                                List<UnlockRecordTableData> unlockRecordTableDataList2=orderUnlockRecordTableData(unlockRecordTableDataList,listTemp);
                                orderTemp.setOrderColumn("credential");
                                listTemp=new ArrayList<>(1);
                                listTemp.add(orderTemp);
                                List<UnlockRecordTableData> unlockRecordTableDataList3=orderUnlockRecordTableData(unlockRecordTableDataList,listTemp);
                                return unlockRecordTableDataList3;
                            }
                            break;
                        case "name":
                            if(null!=o1.getName() && null!=o2.getName()){
                                compareNum=o1.getName().compareTo(o2.getName());
                            }
                            break;
                        default:
                            break;
                    }
                    if ("asc".equals(order.getOrderDir())) {
                        return compareNum;
                    }else{
                        return -1*compareNum;
                    }
                }
            });
        }
        return unlockRecordTableDataList;
    }
    */

    @Override
    public List<UnlockRecordTableData> orderUnlockRecordTableData(List<UnlockRecordTableData> recordTableDataList, final Order order) {
        switch (order.getOrderColumn()) {
            case "gatewayCode":
                Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
                    @Override
                    public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
                        int compareNum=0;
                        compareNum=o1.getGatewayCode().compareTo(o2.getGatewayCode());
                        if ("asc".equals(order.getOrderDir())) {
                            return compareNum;
                        }else{
                            return -1*compareNum;
                        }
                    }
                });
                break;
            case "lockCode":
                Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
                    @Override
                    public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
                        int compareNum=0;
                        compareNum=o1.getLockCode().compareTo(o2.getLockCode());
                        if ("asc".equals(order.getOrderDir())) {
                            return compareNum;
                        }else{
                            return -1*compareNum;
                        }
                    }
                });
                break;
            case "openMode":
                Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
                    @Override
                    public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
                        int compareNum=0;
                        compareNum=o1.getOpenMode()-o2.getOpenMode();
                        if ("asc".equals(order.getOrderDir())) {
                            return compareNum;
                        }else{
                            return -1*compareNum;
                        }
                    }
                });
                break;
            case "timestamp":
                Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
                    @Override
                    public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
                        int compareNum=0;
                        compareNum=o1.getTimestamp().compareTo(o2.getTimestamp());
                        if ("asc".equals(order.getOrderDir())) {
                            return compareNum;
                        }else{
                            return -1*compareNum;
                        }
                    }
                });
                break;
            case "credential":
                Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
                    @Override
                    public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
                        int compareNum=0;
                        compareNum=o1.getOpenMode()-o2.getOpenMode();
                        return compareNum;
                    }
                });
                Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
                    @Override
                    public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
                        int compareNum=0;
                        if(o1.getOpenMode()==o2.getOpenMode()){
                            if(null!=o1.getCredential() && null!=o2.getCredential()){
                                compareNum=o1.getCredential().compareTo(o2.getCredential());
                            }
                            if(null==o1.getCredential() && null!=o2.getCredential()){
                                compareNum=1;
                            }
                        }
                        if ("asc".equals(order.getOrderDir())) {
                            return compareNum;
                        }else{
                            return -1*compareNum;
                        }
                    }
                });
                break;
            case "name":
                Collections.sort(recordTableDataList, new Comparator<UnlockRecordTableData>() {
                    @Override
                    public int compare(UnlockRecordTableData o1, UnlockRecordTableData o2) {
                        if(null!=o1.getName() && null!=o2.getName()){
                            int compareNum=0;
                            compareNum=o1.getName().compareTo(o2.getName());
                            if ("asc".equals(order.getOrderDir())) {
                                return compareNum;
                            }else{
                                return -1*compareNum;
                            }
                        }else {
                            return 0;
                        }
                    }
                });
                break;
            default:
                break;
        }
        return recordTableDataList;
    }

    @Override
    public List<UnlockRecordTableData> convertUnlockRecordToTabularData(List<UnlockRecord> unlockRecords) {
        List<UnlockRecordTableData> recordTableDataList=new ArrayList<>();
        int recordSize=unlockRecords.size();
        UnlockRecord unlockRecord=new UnlockRecord();
        UnlockRecordTableData recordTableData=null;
        String timetag=null;
        for(int i=0;i<recordSize;i++){
            recordTableData=new UnlockRecordTableData();
            unlockRecord=unlockRecords.get(i);
            recordTableData.setGatewayCode(unlockRecord.getGatewayCode());
            recordTableData.setLockCode(unlockRecord.getLockCode());
            timetag=unlockRecord.getTimetag();
            try {
                timetag=DateUtil.yyyy_MM_dd0HH$mm$ss.format(DateUtil.yyyyMMddHHmmss.parse(unlockRecord.getTimetag()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            recordTableData.setTimestamp(timetag);
            recordTableData.setOpenMode(unlockRecord.getOpenMode());
            if(1==unlockRecord.getOpenMode()){
                recordTableData.setCredential(unlockRecord.getCardInfo().getCardNumb());
                recordTableData.setName(unlockRecord.getCardInfo().getName());
            }else if(2==unlockRecord.getOpenMode()){
                recordTableData.setCredential(unlockRecord.getPasswordInfo().getPassword());
            }
            recordTableDataList.add(recordTableData);
        }
        return recordTableDataList;
    }
    /*
    @Override
    public List<UnlockRecord> getUnlockRecord(String ownerPhoneNumber, long startTime, long endTime) {
        reqSign=26;
        String startTimeReqParam= DateUtil.yyyyMMddHHmm.format(new Date(startTime));
        String endTimeReqParam= DateUtil.yyyyMMddHHmm.format(new Date(endTime));
//        try {
//            startTime=DateUtil.format1tillminStringToformat2tillminString(startTime);
//            endTime=DateUtil.format1tillminStringToformat2tillminString(endTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        LOG.info("{ownerPhoneNumber:"+ownerPhoneNumber+",startTime:"+startTime+";endTime:"+endTime+"}");
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTimeReqParam+"\",\"endTime\":\""+endTimeReqParam+"\"}";
        LOG.info("reqData : "+reqData);
        rawData = HttpUtil.httpsPostToGateway(reqData);
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
            if(null!=recordList){
                Collections.reverse(recordList);
            }
            UnlockRecord unlockRecord=null;
            Map gatewaysAndLocks=deviceService.getGatewaysAndLocks(ownerPhoneNumber);
            HashMap gatewayMap=(HashMap)gatewaysAndLocks.get("gateways");
            HashMap lockMap=(HashMap)gatewaysAndLocks.get("locks");
            Gateway gateway=null;
            Lock lock=null;
            Iterator itr=recordList.iterator();
            while (itr.hasNext()){
                unlockRecord= (UnlockRecord) itr.next();
                if(null!=gatewayMap.get(unlockRecord.getGatewayCode())){
                    gateway= (Gateway) gatewayMap.get(unlockRecord.getGatewayCode());
                    unlockRecord.setGatewayName(gateway.getGatewayName());
                }
                if(null!=lockMap.get(unlockRecord.getLockCode())){
                    lock= (Lock) lockMap.get(unlockRecord.getLockCode());
                    unlockRecord.setLockName(lock.getLockName());
                }
            }
            return recordList;
        }
        return null;
    }
    */
    /*
    @Override
    public List<UnlockRecord> getUnlockRecord(String ownerPhoneNumber, Date startDate, Date endDate) {
        reqSign=26;
        long startTimeL=startDate.getTime();
        long endTimeL=endDate.getTime();
        String startTimeReqParam= DateUtil.yyyyMMddHHmm.format(new Date(startTimeL));
        String endTimeReqParam= DateUtil.yyyyMMddHHmm.format(new Date(endTimeL));
        LOG.info("{ownerPhoneNumber:"+ownerPhoneNumber+",startDate:"+startDate+";endDate:"+endDate+"}");
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"startTime\":\""+startTimeReqParam+"\",\"endTime\":\""+endTimeReqParam+"\"}";
        LOG.info("reqData : "+reqData);
        rawData = HttpUtil.httpsPostToGateway(reqData);
//        LOG.info("rawData : "+rawData);

        respSign();
        Map resultMap=new HashMap();
        resultMap.put("result",respSign);
        if (0==respSign){
            //获取开锁记录成功.
            JsonNode node_recordList=rootNode.path("recordList");
            String recordListTemp=node_recordList.asText();
            if("".equals(recordListTemp)){
                return null;
            }
//            String recordListTemp=rootNode.path("recordList").toString();//!!!toString多了引号
            List<UnlockRecord> recordList=null;
            try {
                recordList = objectMapper.readValue(recordListTemp, new TypeReference<List<UnlockRecord>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(null!=recordList){
                Collections.reverse(recordList);
            }
            return recordList;
        }
        return null;
    }
    */

    @Override
    public Records<UnlockRecord> getUnlockRecordPage(String ownerPhoneNumber, long startTime, long endTime, int pageNum, int pageSize) {
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        recordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        if(null==recordList){
            return null;
        }
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
    public Records<UnlockRecord> getGatewayUnlockRecordPage(String ownerPhoneNumber, long startTime, long endTime, final String gatewayCode, int pageNum, int pageSize) {
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        recordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        if(null==recordList){
            return null;
        }
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
    public List<UnlockRecord> getLockUnlockRecord(String ownerPhoneNumber, long startTime, long endTime, final String lockCode) {
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        recordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        if(null==recordList){
            return null;
        }
        //filter-recordList-Bytime,按时间&门锁过滤开锁记录.
        List<UnlockRecord> recordList2=null;
        recordList2= FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                return lockCode.equals(unlockRecord.getLockCode());
            }
        });
        //reverse-recordList,开锁记录的List元素顺序反转(让结果集中timetag降序).
        //Collections.reverse(recordList2);
//        Records<UnlockRecord> records =new Records<>();
//        records.setTotalSize(recordList2.size());
//        records.setRows(recordList2);
//        return records;
        return recordList2;
    }

    @Override
    public Records<UnlockRecord>[] getLockUnlockRecordDaily(String ownerPhoneNumber, Date theDate, final String lockCode) {
        int days= 16;
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(theDate);
//        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        Date endDate=calendar.getTime();
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
        recordList=getUnlockRecord(ownerPhoneNumber,startDate.getTime(),endDate.getTime());
        if(null==recordList){
            return null;
        }
        Records<UnlockRecord>[] dailyRecords=new Records[days];
        for(int i=0;i<days;i++){
            dailyRecords[i]=new Records<UnlockRecord>();
        }
        UnlockRecord unlockRecord;
        if(recordList.isEmpty()){
            for(Records records:dailyRecords){
                records.setRows(new ArrayList());
                records.setTotalSize(0);
            }
        }
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
//                System.out.println("timetag:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(time))+",startMoment:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(startMoment))+",endMoment:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(endMoment))+",index:"+index);
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
    public Records<UnlockRecord> getLockUnlockRecordPage(String ownerPhoneNumber, long startTime, long endTime, final String lockCode, int pageNum, int pageSize) {
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
    public Map getUnlockRecordDevice(String ownerPhoneNumber, long startTime, long endTime) {
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
    public Map getUnlockRecordDevicePage(String ownerPhoneNumber, long startTime, long endTime, int pageNum, int pageSize) {
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
    public Map getUnlockOperator(String ownerPhoneNumber, long startTime, long endTime) {
        List<UnlockRecord> rawUnlockRecordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        UnlockRecord unlockRecord=null;
        UnlockRecord.CardAuthInfo cardInfo=null;
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
    public Records<UnlockRecord> getOperatorUnlockRecordPage(String ownerPhoneNumber, long startTime, long endTime, final String cardNum, int pageNum, int pageSize) {
        //rawData,获取原始数据:开锁记录的List.
        List<UnlockRecord> recordList=null;
        recordList=getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        //filter-recordList-Bytime,按身份证号码cardNumb过滤开锁记录.
        List<UnlockRecord> recordList2=null;
        recordList2= FilterList.filter(recordList, new FilterListHook<UnlockRecord>() {
            @Override
            public boolean test(UnlockRecord unlockRecord) {
                UnlockRecord.CardAuthInfo cardInfo=unlockRecord.getCardInfo();
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
