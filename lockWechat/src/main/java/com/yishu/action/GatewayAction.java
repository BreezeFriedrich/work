/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yishu.pojo.Device;
import com.yishu.service.IDeviceService;
import com.yishu.service.IGatewayService;
import com.yishu.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;
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
    @Autowired
    private IDeviceService deviceService;

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
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getGatewayLANIp(){
        Map resultMap=gatewayService.getGatewayLANIp(ownerPhoneNumber,gatewayCode);
        jsonResult=resultMap;
        return "json";
    }

    public String isCorrectGatewayVerificationCode(){
        int resultInt=gatewayService.isCorrectGatewayVerificationCode(ownerPhoneNumber,gatewayCode,opCode);
        jsonResult=resultInt;
        return "json";
    }

    public String registerGatewayInfo(){
        boolean resultBoolean=gatewayService.registerGatewayInfo(ownerPhoneNumber,gatewayCode,gatewayName,gatewayLocation,gatewayComment,opCode);
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

    public String getSpecificGateway(){
        List jsonList=deviceService.getDeviceInfo(ownerPhoneNumber);
        Device device=null;
        Iterator iter=jsonList.iterator();
        while (iter.hasNext()) {
            device=(Device)(iter.next());
            String specificGatewayCode = device.getGatewayCode();
            if (gatewayCode.equals(specificGatewayCode)) {//gatewayCode来自于struts2 拦截器栈 参数拦截器传递类request.getParameter()
                break;
            }
        }
//        for (Object x :jsonList ) {
//            gatewayCode=((Device)x).getGatewayCode();
//        }
        jsonResult=device;
        return "json";
    }

    public String getVerificationCode(){
        String result=HttpUtil.httpToGateway(url);
        jsonResult=result.substring(result.indexOf("<h1>")+4,result.indexOf("</h1>"));
        return "json";
    }
}
