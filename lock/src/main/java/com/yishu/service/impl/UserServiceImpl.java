package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.User;
import com.yishu.service.IUserService;
import com.yishu.util.DateUtil;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
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
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class.getName());

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

    /*
    // 登录验证    (ownerIp)
    Client->Server
    {
      "sign": 302
      "ownerPhoneNumber":    String,
      "ownerPassword":  String
      "timetag" : String
    }

    Server->Client
    {
      "result": int ,         // 0 成功  1  失败
      "ownerName" : String
      "grade":  int           // 用户等级
    }
     */
    @Override
    public Map checkLogin(String username, String password) {
        reqSign=302;
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+username+"\",\"ownerPassword\":\""+password+"\",\"timetag\":\""+timetag+"\"}";
        rawData= HttpUtil.httpsPostToIp(HttpUtil.ownerIp,reqData);
        System.err.println(rawData);
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
        resultMap.put("ownerName",rootNode.path("ownerName").toString());
        resultMap.put("grade",rootNode.path("grade").asInt());
        return resultMap;
    }

/*
//  获取当前用户的下级用户
Client->Server
{
  "sign": 304
  "ownerPhoneNumber":    String
  "grade":  int
}

Server->Client
{
  "result": int ,         // 0 成功  1  失败
  "juniorList":[
		{
			"juniorPhoneNumber": String
			"juniorName":  String      //   下级用户姓名
			"juniorLocation"S: String    //  下级用户地址
			"juniorGrade": int    // 下级用户的等级
		}
  ]
}
 */
    @Override
    public User getUserWithSubordinate(String phoneNumber, int grade) {
        reqSign=304;
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+phoneNumber+"\",\"grade\":\""+grade+"\"}";
        rawData= HttpUtil.httpsPostToIp(HttpUtil.ownerIp,reqData);
        System.err.println(rawData);
        resultMap=new HashMap();
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        respSign=rootNode.path("result").asInt();
        LOG.info("respSign:"+String.valueOf(respSign));
        List<User> subordinateList = null;
        User subordinate = null;
        if (0==respSign){//获取下级用户成功.
            User user=new User();
            user.setPhoneNumber(phoneNumber);
            user.setGrade(grade);
            JsonNode subordinateListNode=rootNode.path("juniorList");

            Iterator<JsonNode> iterator = subordinateListNode.elements();
            JsonNode subordinateNode=null;
            while (iterator.hasNext()) {
                subordinateNode = iterator.next();
                subordinate=new User();
                subordinate.setPhoneNumber(subordinateNode.findValue("juniorPhoneNumber").toString());
                subordinate.setGrade(subordinateNode.findValue("grade").asInt());
                subordinate.setName(subordinateNode.findValue("juniorName").toString());
                subordinate.setLocation(subordinateNode.findValue("juniorLocation").toString());
                subordinateList.add(subordinate);
            }
            user.setSubordinateList(subordinateList);
            return user;
        }
        return null;
    }

    @Override
    public User getUserWithSubordinateHierarchy(String phoneNumber, int grade ,int levels) {
        User user=getUserWithSubordinate(phoneNumber,grade);
        User resultUser;
        int subordinateListSize=0;
        List subordinateList1=null;
        subordinateList1=user.getSubordinateList();

        //遍历level=1
        for (Object obj1 : subordinateList1){
            User subordinate1= (User) obj1;
            subordinate1=getUserWithSubordinate(subordinate1.getPhoneNumber(),subordinate1.getGrade());
            List subordinateList2=subordinate1.getSubordinateList();

            //遍历level=2
            for (Object obj2 : subordinateList2){
                User subordinate2= (User) obj2;
                subordinate2=getUserWithSubordinate(subordinate2.getPhoneNumber(),subordinate2.getGrade());
                List subordinateList3=subordinate2.getSubordinateList();
            }
        }
        for (int hierarchyLevel=0;hierarchyLevel<levels && subordinateListSize>0;hierarchyLevel++){
//            Iterator itr=subordinateList.iterator();
//            while (itr.hasNext()){
//                (User)itr.next();
//            }
            for (Object obj : subordinateList){
                User subordinate= (User) obj;
                user.getSubordinateList().add()
                subordinateList=subordinate.getSubordinateList();
            }

            subordinateList=user.getSubordinateList();
        }
        hierarchyLevel=1;
        User Lvl1=getUserWithSubordinate();
        return null;
    }

    private User getSubordinateHierarchy(User user){
        int grade=30;
        List subordinateList=user.getSubordinateList();
        subordinateList.clear();
        //lvl=0
        for (Object obj : subordinateList){
            //lvl=1
            User subordinate= (User) obj;
            if (subordinate.getGrade()>grade){
                break;
            }
            subordinate=getSubordinateHierarchy(subordinate);
            subordinateList.add(subordinate);
        }
    }
}
