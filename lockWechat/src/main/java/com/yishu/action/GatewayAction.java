/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yishu.service.IGatewayService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-11 16:23 admin
 * @since JDK1.7
 */
public class GatewayAction extends ActionSupport {
    public GatewayAction() {
        System.out.println(">>>Initialization GatewayAction......................................");
    }
    @Autowired
    private IGatewayService gatewayService;

    /**
     * Object jsonResult——返回的JSON格式的Model
     */
    private Object jsonResult;
    public Object getJsonResult() {
        return jsonResult;
    }

    private String ownerPhoneNumber;
    private String gatewayCode;
    private String gatewayName;
    private String gatewayLocation;
    private String gatewayComment;
    private String opCode;

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

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public String getGatewayLocation() {
        return gatewayLocation;
    }

    public void setGatewayLocation(String gatewayLocation) {
        this.gatewayLocation = gatewayLocation;
    }

    public String getGatewayComment() {
        return gatewayComment;
    }

    public void setGatewayComment(String gatewayComment) {
        this.gatewayComment = gatewayComment;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getGatewayIp(){
        jsonResult=gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        return "json";
    }

    public String hasGatewayAdded(){
        Map resultMap=gatewayService.hasGatewayAdded(ownerPhoneNumber,gatewayCode);
        jsonResult=resultMap;
        return "json";
    }

    public String isCorrectGatewayVerificationCode(){
        int resultInt=gatewayService.isCorrectGatewayVerificationCode(ownerPhoneNumber,gatewayCode,opCode);
        jsonResult=resultInt;
        return "json";
    }

    public String addGateway(){
        boolean resultBoolean=gatewayService.addGateway(ownerPhoneNumber,gatewayCode,gatewayName,gatewayLocation,gatewayComment,opCode);
        jsonResult=resultBoolean;
        return "json";
    }

    public String modifyGatewayInfo(){
        boolean resultBoolean=gatewayService.modifyGatewayInfo(ownerPhoneNumber,gatewayCode,gatewayName,gatewayLocation,gatewayComment);
        jsonResult=resultBoolean;
        return "json";
    }

    public String deleteGateway(){
        boolean resultBoolean=gatewayService.deleteGateway(ownerPhoneNumber,gatewayCode);
        jsonResult=resultBoolean;
        return "json";
    }
}
