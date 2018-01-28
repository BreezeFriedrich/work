/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.*;
import com.yishu.service.IGatewayService;
import com.yishu.service.ILockService;
import com.yishu.service.IUnlockService;
import com.yishu.util.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-08 15:43 admin
 * @since JDK1.7
 */
@Service("unlockService")
public class UnlockServiceImpl implements IUnlockService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UnlockServiceImpl.class);

    @Autowired
    private IGatewayService gatewayService;
    @Autowired
    private ILockService lockService;

    String gatewayIp=null;

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

    private String getServiceNumb(String ownerPhoneNumber,String timetag){
        StringBuffer stringBuffer=new StringBuffer();
        int randomNum= (int) (Math.random()*10000000);
        stringBuffer.append(timetag)
                    .append(ownerPhoneNumber)
                    .append(randomNum);
        return new String(stringBuffer);
    }

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

    /**
     * sign=17,获取门锁授权开锁身份证信息
     *
     * @return List<IdentityCard>
     */
    @Override
    public List<IdentityCard> getUnlockId(String ownerPhoneNumber, String gatewayCode, String lockCode) {
        gatewayIp = gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return null;
        }

        reqSign=17;
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        LOG.info("rawData : "+rawData);

        if (respFail()){//请求数据失败
            return null;
        }
        JsonNode unlockIdsNode=rootNode.path("userList");
        List unlockIdListFirst=new ArrayList<IdentityCard>();
        try {
            unlockIdListFirst=objectMapper.readValue(unlockIdsNode.traverse(), new TypeReference<List<IdentityCard>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        //filter-recordList-Bytime,过滤掉过时的开锁授权.
        List<IdentityCard> unlockIdListLast=null;
        unlockIdListLast= FilterList.filter(unlockIdListFirst, new FilterListHook<IdentityCard>() {
            @Override
            public boolean test(IdentityCard identityCard) {
                try {
                    long endTime=DateUtil.yyyyMMddHHmmss.parse(identityCard.getEndTime()).getTime();
                    long now=GetNetworkTime.getWebsiteDate().getTime();
                    return now < endTime;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        return unlockIdListLast;
    }

    /**
     * 添加身份证开锁授权
     *
     * @param ownerPhoneNumber 开锁帐号
     * @param name 开锁者姓名
     * @param dnCode 开锁者身份证dn码 ,如果非nfc方式添加身份证则为空
     * @return 操作结果
     */
    @Override
    public boolean authUnlockById(String ownerPhoneNumber, String gatewayCode, String lockCode, String name, String cardNumb, String dnCode, String startTime, String endTime) {
        gatewayIp = gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return false;
        }

        reqSign=18;
        timetag= DateUtil.getFormat2TimetagStr();
        serviceNumb=getServiceNumb(ownerPhoneNumber,timetag);
        LOG.info("startTime-1 : "+startTime);
        LOG.info("endTime-1   : "+endTime);
        try {
            startTime= DateUtil.format1tillminStringToformat2tillminString(startTime);
            endTime= DateUtil.format1tillminStringToformat2tillminString(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LOG.info("startTime-2 : "+startTime);
        LOG.info("endTime-2   : "+endTime);
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"name\":\""+name+"\",\"cardNumb\":\""+cardNumb+"\",\"dnCode\":\""+dnCode+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        LOG.info("rawData : "+rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    /**
     * 取消身份证开锁授权
     *
     * @return 操作结果
     */
    @Override
    public boolean prohibitUnlockById(String ownerPhoneNumber, String lockCode, String cardNumb,String serviceNumb) {
        gatewayIp = lockService.getLockIp(ownerPhoneNumber,lockCode);
        if (null == gatewayIp) {
            return false;
        }

        reqSign=19;
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"cardNumb\":\""+cardNumb+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        LOG.info("rawData : "+rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    /**
     * 获取智能锁开锁密码
     *
     * @return 开锁密码对象UnlockPwds
     */
    @Override
    public UnlockPwds getUnlockPwd(String ownerPhoneNumber, String gatewayCode, String lockCode) {
        gatewayIp = gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return null;
        }

        reqSign=20;
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        LOG.info("rawData : "+rawData);

        if (respFail()){//请求数据失败
            return null;
        }
        String defaultPassword1 = rootNode.path("defaultPassword1").asText();
        String defaultPassword2 = rootNode.path("defaultPassword2").asText();
        JsonNode unlockPwdNode=rootNode.path("passwordList");
        List unlockPwdListFirst=new ArrayList<UnlockPwd>();
        try {
            unlockPwdListFirst=objectMapper.readValue(unlockPwdNode.traverse(), new TypeReference<List<UnlockPwd>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        //filter-recordList-Bytime,过滤掉过时的开锁授权.
        List<UnlockPwd> unlockPwdListLast=null;
        unlockPwdListLast= FilterList.filter(unlockPwdListFirst, new FilterListHook<UnlockPwd>() {
            @Override
            public boolean test(UnlockPwd unlockPwd) {
                try {
                    long endTime=DateUtil.yyyyMMddHHmmss.parse(unlockPwd.getEndTime()).getTime();
                    long now=GetNetworkTime.getWebsiteDate().getTime();
                    return now < endTime;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        UnlockPwds unlockPwds = new UnlockPwds();
        unlockPwds.setDefaultPassword1(defaultPassword1);
        unlockPwds.setDefaultPassword2(defaultPassword2);
        unlockPwds.setPasswordList(unlockPwdListLast);
        return unlockPwds;
    }

    /**
     * 添加智能锁密码
     *
     * @return
     */
    @Override
    public boolean authUnlockByPwd(String ownerPhoneNumber, String gatewayCode, String lockCode, String password, String startTime, String endTime) {
        gatewayIp = gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return false;
        }

        reqSign=21;
        timetag= DateUtil.getFormat2TimetagStr();
        serviceNumb=getServiceNumb(ownerPhoneNumber,timetag);
        LOG.info("startTime-1 : "+startTime);
        LOG.info("endTime-1   : "+endTime);
        try {
            startTime= DateUtil.format1tillminStringToformat2tillminString(startTime);
            endTime= DateUtil.format1tillminStringToformat2tillminString(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LOG.info("startTime-2 : "+startTime);
        LOG.info("endTime-2   : "+endTime);
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"password\":\""+password+"\",\"startTime\":\""+startTime+"\",\"endTime\":\""+endTime+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        LOG.info("rawData : "+rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    /**
     * 取消智能锁密码
     *
     * @param gatewayCode 这是https json协议中多余的一项
     * @return
     */
    @Override
    public boolean prohibitUnlockByPwd(String ownerPhoneNumber, String gatewayCode, String lockCode,String serviceNumb) {
        gatewayIp = gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        if (null == gatewayIp) {
            return false;
        }

        reqSign=22;
        timetag= DateUtil.getFormat2TimetagStr();
//        serviceNumb=getServiceNumb(ownerPhoneNumber,timetag);
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"lockCode\":\""+lockCode+"\",\"gatewayCode\":\""+gatewayCode+"\",\"serviceNumb\":\""+serviceNumb+"\",\"timetag\":\""+timetag+"\"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        LOG.info("rawData : "+rawData);

        if (respFail()){
            return false;
        }

        return true;
    }

    /*
    @Override
    public UnlockAuthorization getUnlockAuthorization(String ownerPhoneNumber, String gatewayCode, String lockCode) {
        List<IdentityCard> unlockIds=getUnlockId(ownerPhoneNumber,gatewayCode,lockCode);
        UnlockPwds unlockPwds=getUnlockPwd(ownerPhoneNumber,gatewayCode,lockCode);
        UnlockAuthorization unlockAuthorization=new UnlockAuthorization();
        return unlockAuthorization.getUnlockAuthorization(unlockIds,unlockPwds);
    }
    */

    @Override
    public UnlockAuthorization getUnlockAuthorization(String ownerPhoneNumber, String gatewayCode, String lockCode) {
//        gatewayIp = gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
//        if (null == gatewayIp) {
//            return null;
//        }
        //获取身份证开锁授权
        Date webDate=null;
        webDate=GetNetworkTime.getWebsiteDate();
        if (null==webDate){
            LOG.warn("GetNetworkTime.getWebsiteDate() return:null");
            webDate=new Date();
        }
        LOG.info("webDate:"+webDate);
        final long now=webDate.getTime();
        timetag= DateUtil.getFormat2TimetagStr();
        reqSign=17;
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
        LOG.info("reqData : "+reqData);
//        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        rawData= HttpUtil.httpsPostToQixu(reqData);
        LOG.info("rawData : "+rawData);


        if (respFail()){//0=result请求数据失败
            return null;
        }
        JsonNode unlockIdsNode=rootNode.path("userList");
//        List unlockIdListFirst=new ArrayList<IdentityCard>();
        List unlockIdListFirst=null;
        try {
            unlockIdListFirst=objectMapper.readValue(unlockIdsNode.traverse(), new TypeReference<List<IdentityCard>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        //filter-recordList-Bytime,过滤掉过时的开锁授权.
        List<IdentityCard> unlockIdListLast=null;
        unlockIdListLast= FilterList.filter(unlockIdListFirst, new FilterListHook<IdentityCard>() {
            @Override
            public boolean test(IdentityCard identityCard) {
                try {
                    long endTime=DateUtil.yyyyMMddHHmm.parse(identityCard.getEndTime()).getTime();
                    return now < endTime;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        //获得密码开锁授权
        reqSign=20;
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\"}";
        LOG.info("reqData : "+reqData);
//        rawData= HttpUtil.httpsPostToIp(gatewayIp,reqData);
        rawData= HttpUtil.httpsPostToQixu(reqData);
        LOG.info("rawData : "+rawData);

        if (respFail()){//请求数据失败
            return null;
        }
        String defaultPassword1 = rootNode.path("defaultPassword1").asText();
        String defaultPassword2 = rootNode.path("defaultPassword2").asText();
        JsonNode unlockPwdNode=rootNode.path("passwordList");
//        List unlockPwdListFirst=new ArrayList<UnlockPwd>();
        List unlockPwdListFirst=null;
        try {
            unlockPwdListFirst=objectMapper.readValue(unlockPwdNode.traverse(), new TypeReference<List<UnlockPwd>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        //filter-recordList-Bytime,过滤掉过时的开锁授权.
        List<UnlockPwd> unlockPwdListLast=null;
        unlockPwdListLast= FilterList.filter(unlockPwdListFirst, new FilterListHook<UnlockPwd>() {
            @Override
            public boolean test(UnlockPwd unlockPwd) {
                try {
                    long endTime=DateUtil.yyyyMMddHHmm.parse(unlockPwd.getEndTime()).getTime();
                    long now=GetNetworkTime.getWebsiteDate().getTime();
                    return now < endTime;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        if(unlockIdListLast.isEmpty()&&unlockPwdListLast.isEmpty()){
            return null;
        }
        UnlockPwds unlockPwds = new UnlockPwds();
        unlockPwds.setDefaultPassword1(defaultPassword1);
        unlockPwds.setDefaultPassword2(defaultPassword2);
        unlockPwds.setPasswordList(unlockPwdListLast);

        UnlockAuthorization unlockAuthorization=new UnlockAuthorization();
        return unlockAuthorization.getUnlockAuthorization(unlockIdListLast,unlockPwds);
    }

    @Override
    public Authinfo getUnlockAuthorizationDailyArr(UnlockAuthorization unlockAuthorization, long startTimeL, long endTimeL) throws ParseException {
        final long startMoment;
        final long endMoment;
        long moments[];

//        long startTimeL=DateUtil.yyyyMMddHHmmss.parse(startTime).getTime();
//        long endTimeL= DateUtil.yyyyMMddHHmmss.parse(endTime).getTime();

        System.out.println("startTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(startTimeL))+",endTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(endTimeL)));
        moments=DateUtil.resetPeriod(startTimeL,endTimeL);
        startMoment=moments[0];
        endMoment=moments[1];
        System.out.println("startMoment:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(startMoment))+",endMoment:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(endMoment)));
        int periodSize= (int) ((endMoment-startMoment)/86400000);
        //
        int unlockIdSize=unlockAuthorization.getUnlockIdSize();
        int unlockPwdSize=unlockAuthorization.getUnlockPwdSize();
        if (!(unlockIdSize+unlockPwdSize>0)){
            return null;
        }
        List<IdentityCard> unlockIds=unlockAuthorization.getUnlockIds();
        List<UnlockPwd> unlockPwds=unlockAuthorization.getUnlockPwds().getPasswordList();

        //filter-unlockIds-Bytime,过滤身份证开锁授权.
        List<IdentityCard> unlockIdListNext=null;
        unlockIdListNext= FilterList.filter(unlockIds, new FilterListHook<IdentityCard>() {
            @Override
            public boolean test(IdentityCard identityCard) {
                try {
                    long startTime=DateUtil.yyyyMMddHHmm.parse(identityCard.getStartTime()).getTime();
                    long endTime=DateUtil.yyyyMMddHHmm.parse(identityCard.getEndTime()).getTime();
                    System.out.println("filter-unlockIds-Bytime startTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(startTime))+",endTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(endTime)));
                    if (startTime>endTime){
                        return false;
                    }
                    if (endTime<startMoment){
                        return false;
                    }
                    if (startTime>endMoment){
                        return false;
                    }
                    return true;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        //filter-unlockPwds-Bytime,过滤密码开锁授权.
        List<UnlockPwd> unlockPwdListNext=null;
        unlockPwdListNext= FilterList.filter(unlockPwds, new FilterListHook<UnlockPwd>() {
            @Override
            public boolean test(UnlockPwd unlockPwd) {
                try {
                    long startTime=DateUtil.yyyyMMddHHmm.parse(unlockPwd.getStartTime()).getTime();
                    long endTime=DateUtil.yyyyMMddHHmm.parse(unlockPwd.getEndTime()).getTime();
                    if (startTime>endTime){
                        return false;
                    }
                    if (endTime<startMoment){
                        return false;
                    }
                    if (startTime>endMoment){
                        return false;
                    }
                    return true;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        Authinfo authinfo=new Authinfo();
        AuthinfoDaily[] authinfoPeriod=new AuthinfoDaily[periodSize];
        long time;
        for(int i=0;i<periodSize;i++){
            time=86400000*1L*i+startMoment;
            authinfoPeriod[i].setTime(time);
            authinfoPeriod[i].setDate(DateUtil.yyyy_MM_dd.format(new Date(time)));
        }
        int idListNextSize=unlockIdListNext.size();
        int pwdListNextSize=unlockPwdListNext.size();
        IdentityCard[] ids=new IdentityCard[idListNextSize];
        unlockIdListNext.toArray(ids);
        UnlockPwd[] pwds=new UnlockPwd[pwdListNextSize];
        unlockPwdListNext.toArray(pwds);
        authinfo.setIds(ids);
        authinfo.setPwds(pwds);
        IdentityCard id=null;
        UnlockPwd pwd=null;
        /*
        for(long timeTemp=startMoment;timeTemp<endMoment;timeTemp=timeTemp+86400000){
            authinfoDaily.setTime(timeTemp);
            authinfoDaily.setDate(DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(timeTemp)));
            for(int i=0;i<idListNextSize;i++){
                id=ids[i];
            }
        }
        */
        long startTimeTemp;
        long endTimeTemp;
        int startIndex;
        int endIndex;
        for(int i=0;i<idListNextSize;i++){
            id=unlockIdListNext.get(i);
            ids[i]=id;
            startTimeTemp=DateUtil.yyyyMMddHHmm.parse(id.getStartTime()).getTime();
            endTimeTemp=DateUtil.yyyyMMddHHmm.parse(id.getEndTime()).getTime();
            moments=DateUtil.resetPeriod(startTimeTemp,endTimeTemp);
            startTimeTemp=moments[0];
            endTimeTemp=moments[1];
            startTimeTemp=startTimeTemp>startMoment?startTimeTemp:startMoment;//取大
            endTimeTemp=endTimeTemp<endMoment?endTimeTemp:endMoment;//取小
            startIndex= (int) ((startTimeTemp-startMoment)/86400000);
            endIndex= (int) ((endTimeTemp-startMoment)/86400000);
            for (int j=startIndex;j<=endIndex;j++){
                authinfoPeriod[j].getIdIndexes().add(i);
            }
        }
        for(int i=0;i<pwdListNextSize;i++){
            pwd=unlockPwdListNext.get(i);
            pwds[i]=pwd;
            startTimeTemp=DateUtil.yyyyMMddHHmm.parse(pwd.getStartTime()).getTime();
            endTimeTemp=DateUtil.yyyyMMddHHmm.parse(pwd.getEndTime()).getTime();
            moments=DateUtil.resetPeriod(startTimeTemp,endTimeTemp);
            startTimeTemp=moments[0];
            endTimeTemp=moments[1];
            startTimeTemp=startTimeTemp>startMoment?startTimeTemp:startMoment;//取大
            endTimeTemp=endTimeTemp<endMoment?endTimeTemp:endMoment;//取小
            startIndex= (int) ((startTimeTemp-startMoment)/86400000);
            endIndex= (int) ((endTimeTemp-startMoment)/86400000);
            for (int j=startIndex;j<=endIndex;j++){
                authinfoPeriod[j].getPwdIndexes().add(i);
            }
        }
        authinfo.setAuthinfoDaily(authinfoPeriod);

        return authinfo;
    }
}
