/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.action;

import com.yishu.service.IUnlockService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-08 15:34 admin
 * @since JDK1.7
 */
public class UnlockAction {
    public UnlockAction() {
        System.out.println(">>>Initialization UnlockAction......................................");
    }
    @Autowired
    private IUnlockService unlockService;

    private String ownerPhoneNumber;
    private String gatewayCode;
    private String lockCode;
    private String name;
    private String cardNumb;
    private String dnCode;
    private String password;
    private String startTime;
    private String endTime;

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

    private List jsonList;
    public List getJsonList() {
        return jsonList;
    }
    private Object jsonObject;
    public Object getJsonObject() {
        return jsonObject;
    }

    public String getUnlockId (){
        jsonList.clear();
        List unlockIdList=unlockService.getUnlockId(ownerPhoneNumber,gatewayCode,lockCode);
        jsonList.addAll(unlockIdList);
        return "json";
    }

    public String authUnlockById (){
        boolean isSuccess=unlockService.authUnlockById(ownerPhoneNumber,gatewayCode,lockCode,name,cardNumb,dnCode,startTime,endTime);
        jsonObject=isSuccess;
        return "json";
    }

    public String prohibitUnlockById (){
    }

    public String getUnlockPwd (){
    }

    public String authUnlockByPwd (){
    }

    public String prohibitUnlockByPwd (){
    }
}
