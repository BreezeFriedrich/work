/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yishu.service.IGatewayService;
import com.yishu.util.HttpUtil;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-11 16:23 admin
 * @since JDK1.7
 */
public class GatewayAction extends ActionSupport {
    public GatewayAction() {
        LOG.info(">>>Initialization GatewayAction......................................");
    }
    private org.slf4j.Logger LOG = LoggerFactory.getLogger("GatewayAction");

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

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
//    Map<String,Object> sessionMap=ActionContext.getContext().getSession();

    public String getGatewayIp(){
        LOG.info("-->>-- gateway/getGatewayIp.do -->>--");
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
//        jsonResult=gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        jsonResult= HttpUtil.getGatewayIp(ownerPhoneNumber,gatewayCode);
        return "json";
    }

    public String hasGatewayAdded(){
        LOG.info("-->>-- gateway/hasGatewayAdded.do -->>--");
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        Map resultMap=gatewayService.hasGatewayAdded(ownerPhoneNumber,gatewayCode);
        jsonResult=resultMap;
        return "json";
    }

    public String getGatewayLANIp(){
        LOG.info("-->>-- gateway/getGatewayLANIp.do -->>--");
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        Map resultMap=gatewayService.getGatewayLANIp(ownerPhoneNumber,gatewayCode);
        jsonResult=resultMap;
        return "json";
    }

    public String isCorrectGatewayVerificationCode(){
        LOG.info("-->>-- gateway/isCorrectGatewayVerificationCode.do -->>--");
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        int resultInt=gatewayService.isCorrectGatewayVerificationCode(ownerPhoneNumber,gatewayCode,opCode);
        jsonResult=resultInt;
        return "json";
    }

    public String registerGatewayInfo(){
        LOG.info("-->>-- gateway/registerGatewayInfo.do -->>--");
        LOG.info("ownerPhoneNumber-before:"+ownerPhoneNumber);
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        LOG.info("ownerPhoneNumber-after:"+ownerPhoneNumber);
        boolean resultBoolean=gatewayService.registerGatewayInfo(ownerPhoneNumber,gatewayCode,gatewayName,gatewayLocation,gatewayComment,opCode);
        jsonResult=resultBoolean;
        return "json";
    }

    public String modifyGatewayInfo(){
        LOG.info("-->>-- gateway/modifyGatewayInfo.do -->>--");
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        boolean resultBoolean=gatewayService.modifyGatewayInfo(ownerPhoneNumber,gatewayCode,gatewayName,gatewayLocation,gatewayComment);
        jsonResult=resultBoolean;
        return "json";
    }

    public String deleteGateway(){
        LOG.info("-->>-- gateway/deleteGateway.do -->>--");
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        boolean resultBoolean=gatewayService.deleteGateway(ownerPhoneNumber,gatewayCode);
        jsonResult=resultBoolean;
        return "json";
    }

    /*访问局域网内,跨域无效
    public String getVerificationCode(){
        LOG.info("-->>-- gateway/getVerificationCode.do -->>--");
        String result=HttpUtil.httpToGateway(url);
        LOG.info("getVerificationCode,httpToGateway("+url+")返回结果为："+result);
        jsonResult=result.substring(result.indexOf("<h1>")+4,result.indexOf("</h1>"));
        return "json";
    }
    */
}
