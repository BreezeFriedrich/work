package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.*;
import com.yishu.service.IDeviceService;
import com.yishu.service.IRoomService;
import com.yishu.service.IUserService;
import com.yishu.util.DateUtil;
import com.yishu.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-12-21 17:43 admin
 * @since JDK1.7
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IRoomService roomService;

    String timetag;
    /**
     * 开锁信息序列号 32位 timetag 14位+使用者手机号11位+随机整数7位
     */
    String serviceNumb;

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
    Map resultMap;

    private boolean respFail(){
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respSign=rootNode.path("result").asInt();
        LOG.info("respSign:"+String.valueOf(respSign));
        if(0 == respSign){
            return false;
        }
        return true;
    }

    @Override
    public Map checkLogin(String username, String password) {
        reqSign=302;
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+username+"\",\"ownerPassword\":\""+password+"\",\"timetag\":\""+timetag+"\"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData : "+rawData);
        resultMap=new HashMap();
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respSign=rootNode.path("result").asInt();
        LOG.info("respSign:"+String.valueOf(respSign));
        resultMap.put("ownerPhoneNumber",username);
        resultMap.put("ownerPassword",password);
        resultMap.put("result",rootNode.path("result").asInt());
        resultMap.put("ownerName",rootNode.path("ownerName").asText());
        resultMap.put("grade",rootNode.path("grade").asInt());
        return resultMap;
    }

    @Override
    public User getUserWithSubordinate(String phoneNumber, int grade) {
        reqSign=304;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+phoneNumber+"\",\"grade\":"+grade+"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData : "+rawData);
        resultMap=new HashMap();
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respSign=rootNode.path("result").asInt();
        LOG.info("respSign:"+String.valueOf(respSign));
//        List<User> subordinateList = new ArrayList<>();
        List<User> subordinateList = null;
        User subordinate = null;
        if (0==respSign){//获取下级用户成功.
            User user=new User();
            user.setPhoneNumber(phoneNumber);
            user.setGrade(grade);
            JsonNode subordinateListNode=rootNode.path("juniorList");

            Iterator<JsonNode> iterator = subordinateListNode.elements();
            JsonNode subordinateNode=null;
            if(iterator.hasNext()){
                subordinateList = new ArrayList<>();
            }
            while (iterator.hasNext()) {
                subordinateNode = iterator.next();
                subordinate=new User();
//                LOG.info("juniorPhoneNumber toString : "+subordinateNode.path("juniorPhoneNumber").toString());
//                LOG.info("juniorPhoneNumber asText : "+subordinateNode.path("juniorPhoneNumber").asText());
//                LOG.info("juniorPhoneNumber textValue : "+subordinateNode.path("juniorPhoneNumber").textValue());
                LOG.info("juniorGrade : "+subordinateNode.path("juniorGrade").asInt());
                subordinate.setPhoneNumber(subordinateNode.path("juniorPhoneNumber").asText());
                subordinate.setGrade(subordinateNode.path("juniorGrade").asInt());
                subordinate.setName(subordinateNode.path("juniorName").asText());
                subordinate.setLocation(subordinateNode.path("juniorLocation").asText());
                subordinateList.add(subordinate);
            }
            user.setSubordinateList(subordinateList);
            return user;
        }
        return null;
    }

    @Override
    public User getUserWithSubordinate(User user) {
        String phoneNumber=user.getPhoneNumber();
        int grade=user.getGrade();
        reqSign=304;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+phoneNumber+"\",\"grade\":"+grade+"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData : "+rawData);
        resultMap=new HashMap();
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respSign=rootNode.path("result").asInt();
        LOG.info("respSign:"+String.valueOf(respSign));
//        List<User> subordinateList = new ArrayList<>();
        List<User> subordinateList=null;
        User subordinate = null;
        if (0==respSign){//获取下级用户成功.
            JsonNode subordinateListNode=rootNode.path("juniorList");

            Iterator<JsonNode> iterator = subordinateListNode.elements();
            JsonNode subordinateNode=null;
            if(iterator.hasNext()){
                subordinateList = new ArrayList<>();
            }
            while (iterator.hasNext()) {
                subordinateNode = iterator.next();
                subordinate=new User();
                subordinate.setPhoneNumber(subordinateNode.path("juniorPhoneNumber").asText());
                subordinate.setGrade(subordinateNode.path("juniorGrade").asInt());
                subordinate.setName(subordinateNode.path("juniorName").asText());
                subordinate.setLocation(subordinateNode.path("juniorLocation").asText());
                subordinateList.add(subordinate);
            }
            user.setSubordinateList(subordinateList);
            return user;
        }
        return null;
    }

    public User getSubordinateHierarchy(User user,int minGrade) {
        String phoneNumber;
        int grade;
        List subordinateList = user.getSubordinateList();
        if (null==subordinateList||subordinateList.isEmpty()){
            return user;
        }
        List newSubordinateList = new ArrayList();
        User subordinate = null;
        for (Object obj : subordinateList) {
            subordinate = (User) obj;
            phoneNumber = subordinate.getPhoneNumber();
            grade = subordinate.getGrade();
            if (grade<minGrade){
                break;
            }
            subordinate = getUserWithSubordinate(subordinate);
            subordinate = getSubordinateHierarchy(subordinate,minGrade);
            newSubordinateList.add(subordinate);
        }
        user.setSubordinateList(newSubordinateList);
        return user;
    }

    /*
    @Override
    public User getSubordinateHierarchyTillLock(User user) {
        String phoneNumber;
        int grade;
        phoneNumber=user.getPhoneNumber();
        grade=user.getGrade();
        User userHierarchy=getSubordinateHierarchy(user,10);
        List subordinates=userHierarchy.getSubordinateList();
//        User subordinate=null;
        User subordinate_20=null;
        User subordinate_10=null;

        List<Lock> lockList;
        Device device;
//        Map landLordMapLock=new HashMap();
        if (30==grade){
            for (Object z : subordinates) {
                subordinate_20 = (User) z;//subordinate.grade==20
                List subordinateList_10=subordinate_20.getSubordinateList();
                for (Object y : subordinateList_10) {
                    subordinate_10 = (User) y;
                    phoneNumber = subordinate_10.getPhoneNumber();
                    List deviceList=deviceService.getDeviceInfo(phoneNumber);
                    lockList=new ArrayList<>();
                    for (Object x : deviceList){
                        device= (Device) x;
                        lockList.addAll(device.getLockLists());
                    }
                    subordinate_10.setSubordinateList(lockList);
                }
            }
        }
        if (20==grade){
            for (Object y : subordinates) {
                subordinate_10 = (User) y;
                phoneNumber = subordinate_10.getPhoneNumber();
                List deviceList=deviceService.getDeviceInfo(phoneNumber);
                lockList=new ArrayList<>();
                for (Object x : deviceList){
                    device= (Device) x;
                    lockList.addAll(device.getLockLists());
                }
                subordinate_10.setSubordinateList(lockList);
            }
        }
        if (10==grade){
            List deviceList=deviceService.getDeviceInfo(phoneNumber);
            lockList=new ArrayList<>();
            for (Object x : deviceList){
                device= (Device) x;
                lockList.addAll(device.getLockLists());
            }
            user.setSubordinateList(lockList);
        }
        return user;
    }
    */
    @Override
    public User getSubordinateHierarchyTillLock(User user) {
        String phoneNumber;
        int grade;
        phoneNumber=user.getPhoneNumber();
        grade=user.getGrade();
        User userHierarchy=getSubordinateHierarchy(user,10);
        List subordinates=userHierarchy.getSubordinateList();
//        User subordinate=null;
        User subordinate_20=null;
        User subordinate_10=null;

        List<GatewayLock> lockList;
        Device device;
//        Map landLordMapLock=new HashMap();
        if (30==grade){
            for (Object z : subordinates) {
                subordinate_20 = (User) z;//subordinate.grade==20
                List subordinateList_10=subordinate_20.getSubordinateList();
                for (Object y : subordinateList_10) {
                    subordinate_10 = (User) y;
                    phoneNumber = subordinate_10.getPhoneNumber();
                    //设置subordinateList,对于等级10用户,代表的是网关和门锁的设备信息.
                    List deviceList=deviceService.getDeviceInfo(phoneNumber);
                    lockList=new ArrayList<>();
                    for (Object x : deviceList){
                        device= (Device) x;
                        lockList.addAll(deviceService.convertDeviceToGatewayLock(device));
                    }
                    subordinate_10.setSubordinateList(lockList);
                    //设置roomTypeContainRoomList
                    List<RoomTypeContainRoom> roomTypeCRList =roomService.getRoom(phoneNumber);
                    if(null!=roomTypeCRList){
                        subordinate_10.setRoomTypeContainRoomList(roomTypeCRList);
                    }
                }
            }
        }
        if (20==grade){
            for (Object y : subordinates) {
                subordinate_10 = (User) y;
                phoneNumber = subordinate_10.getPhoneNumber();
                List deviceList=deviceService.getDeviceInfo(phoneNumber);
                lockList=new ArrayList<>();
                for (Object x : deviceList){
                    device= (Device) x;
                    lockList.addAll(deviceService.convertDeviceToGatewayLock(device));
                }
                subordinate_10.setSubordinateList(lockList);
                //设置roomTypeContainRoomList
                List<RoomTypeContainRoom> roomTypeCRList =roomService.getRoom(phoneNumber);
                if(null!=roomTypeCRList){
                    subordinate_10.setRoomTypeContainRoomList(roomTypeCRList);
                }
            }
        }
        if (10==grade){
            List deviceList=deviceService.getDeviceInfo(phoneNumber);
            lockList=new ArrayList<>();
            for (Object x : deviceList){
                device= (Device) x;
                lockList.addAll(deviceService.convertDeviceToGatewayLock(device));
            }
            user.setSubordinateList(lockList);
            //设置roomTypeContainRoomList
            List<RoomTypeContainRoom> roomTypeCRList =roomService.getRoom(phoneNumber);
            if(null!=roomTypeCRList){
                user.setRoomTypeContainRoomList(roomTypeCRList);
            }
        }
        return user;
    }

    @Override
    public Map addSubordinate(String ownerPhoneNumber, String juniorPhoneNumber, String juniorName, String juniorLocation, int grade) {
        reqSign=303;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"juniorPhoneNumber\":\""+juniorPhoneNumber+"\",\"juniorName\":\""+juniorName+"\",\"juniorLocation\":\""+juniorLocation+"\",\"grade\":"+grade+"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData : "+rawData);
        resultMap=new HashMap();
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respSign=rootNode.path("result").asInt();
        LOG.info("respSign:"+String.valueOf(respSign));
        int result=rootNode.path("result").asInt();
        resultMap.put("result",result);
        if (2==result){
            String superiorPhoneNumber=rootNode.path("superiorPhoneNumber").toString();
            resultMap.put("superiorPhoneNumber",superiorPhoneNumber);
        }
        return resultMap;
    }

    @Override
    public boolean cancleSubordinate(String ownerPhoneNumber, String juniorPhoneNumber, int grade) {
        reqSign=305;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"juniorPhoneNumber\":\""+juniorPhoneNumber+"\",\"grade\":"+grade+"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToOwner(reqData);
        LOG.info("rawData : "+rawData);
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int result=rootNode.path("result").asInt();
        LOG.info("result:"+String.valueOf(result));
        return (0==result);
    }
}
