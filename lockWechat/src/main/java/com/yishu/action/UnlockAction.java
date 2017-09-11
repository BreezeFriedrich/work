/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yishu.service.IUnlockService;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-08 15:34 admin
 * @since JDK1.7
 */
public class UnlockAction extends ActionSupport {
    public UnlockAction() {
        System.out.println(">>>Initialization UnlockAction......................................");
    }
    @Autowired
    private IUnlockService unlockService;

    private Object jsonObject;
    public Object getJsonObject() {
        return jsonObject;
    }

    private String ownerPhoneNumber;
    private String gatewayCode;
    private String lockCode;
    private String name;
    private String cardNumb;
    private String dnCode;
    private String password;
    private String startTime;
    private String endTime;
    private String serviceNumb;

    public IUnlockService getUnlockService() {
        return unlockService;
    }

    public void setUnlockService(IUnlockService unlockService) {
        this.unlockService = unlockService;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public String getGatewayCode() {
        return gatewayCode;
    }

    public void setGatewayCode(String gatewayCode) {
        this.gatewayCode = gatewayCode;
    }

    public String getLockCode() {
        return lockCode;
    }

    public void setLockCode(String lockCode) {
        this.lockCode = lockCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumb() {
        return cardNumb;
    }

    public void setCardNumb(String cardNumb) {
        this.cardNumb = cardNumb;
    }

    public String getDnCode() {
        return dnCode;
    }

    public void setDnCode(String dnCode) {
        this.dnCode = dnCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getServiceNumb() {
        return serviceNumb;
    }

    public void setServiceNumb(String serviceNumb) {
        this.serviceNumb = serviceNumb;
    }

    /**
     * 获取(当期帐户、当前网关、当前门锁)已授权的开锁身份证信息
     *
     * @return
     */
    public String getUnlockId (){
//        jsonList.clear();
        List unlockIdList=unlockService.getUnlockId(ownerPhoneNumber,gatewayCode,lockCode);
//        jsonList.addAll(unlockIdList);
        jsonObject=unlockIdList;
//        System.err.println("jsonObject :"+jsonObject);
        return "json";
    }

    /**
     * 添加身份证开锁授权
     *
     * @return
     */
    public String authUnlockById (){
        boolean isSuccess=unlockService.authUnlockById(ownerPhoneNumber,gatewayCode,lockCode,name,cardNumb,dnCode,startTime,endTime);
        jsonObject=isSuccess;
        return "json";
    }

    /**
     * 删除开锁身份证
     * serviceNumb为授权时产生的操作序列号
     *
     * @return
     */
    public String prohibitUnlockById (){
        boolean isSuccess=unlockService.prohibitUnlockById(ownerPhoneNumber,lockCode,cardNumb,serviceNumb);
        jsonObject=isSuccess;
        return "json";
    }

    /**
     * 获取(当期帐户、当前网关、当前门锁)已授权的开锁密码信息
     *
     * @return
     */
    public String getUnlockPwd (){
        jsonObject=unlockService.getUnlockPwd(ownerPhoneNumber,gatewayCode,lockCode);
        return "json";
    }

    /**
     * 开锁密码授权
     *
     * @return
     */
    public String authUnlockByPwd (){
        boolean isSuccess=unlockService.authUnlockByPwd(ownerPhoneNumber,gatewayCode,lockCode,password,startTime,endTime);
        jsonObject=isSuccess;
        return "json";
    }

    /**
     * 删除开锁密码
     * serviceNumb为授权时产生的操作序列号
     *
     * @return
     */
    public String prohibitUnlockByPwd (){
        boolean isSuccess=unlockService.prohibitUnlockByPwd(ownerPhoneNumber,gatewayCode,lockCode,serviceNumb);
        jsonObject=isSuccess;
        return "json";
    }

    /*另一种方式返回Model(实测,成功).有无<result>都能成功.
    public void prohibitUnlockByPwd () {
        boolean isSuccess=unlockService.prohibitUnlockByPwd(ownerPhoneNumber,gatewayCode,lockCode,serviceNumb);
        PrintWriter out= ServletActionContext.getResponse().getWriter();
        try {
            out = getResp();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(isSuccess);
        out.flush();
        out.close();
    }
    */
}
