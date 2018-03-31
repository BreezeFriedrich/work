/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yishu.service.IAccountService;
import com.yishu.service.IUnlockService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-08 15:34 admin
 * @since JDK1.7
 */
public class UnlockAction extends ActionSupport {
    public UnlockAction() {
        LOG.info(">>>Initialization UnlockAction......................................");
    }
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("UnlockAction");

    @Autowired
    private IUnlockService unlockService;
    @Autowired
    private IAccountService accountService;

    private Object jsonResult;
    public Object getJsonResult() {
        return jsonResult;
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
    private String authPassword;

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

    public String getAuthPassword() {
        return authPassword;
    }

    public void setAuthPassword(String authPassword) {
        this.authPassword = authPassword;
    }

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
//    Map<String,Object> sessionMap=ActionContext.getContext().getSession();

    /**
     * 获取(当期帐户、当前网关、当前门锁)已授权的开锁身份证信息
     *
     * @return
     */
    public String getUnlockId (){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        List unlockIdList=unlockService.getUnlockId(ownerPhoneNumber,gatewayCode,lockCode);
        jsonResult=unlockIdList;
        return "json";
    }

    /**
     * 添加身份证开锁授权
     *
     * @return
     */
    /*
    public String authUnlockById (){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        boolean isValidAuthPassword = accountService.validAuthPassword(ownerPhoneNumber,authPassword);
        boolean isSuccess;
        if (isValidAuthPassword){
            isSuccess = unlockService.authUnlockById(ownerPhoneNumber,gatewayCode,lockCode,name,cardNumb,dnCode,startTime,endTime);
        }else {
            isSuccess=false;
        }
        jsonResult=isSuccess;
        return "json";
    }
    */
    public String authUnlockById (){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        Map resultMap=new HashMap();
        Map validateMap = accountService.validAuthPassword(ownerPhoneNumber,authPassword);
        if(0 == (int) validateMap.get("result")){
            boolean authBoolean=unlockService.authUnlockById(ownerPhoneNumber,gatewayCode,lockCode,name,cardNumb,dnCode,startTime,endTime);
            if (authBoolean){
                resultMap.put("result",0);
                resultMap.put("msg","修改授权码成功");
            }else {
                resultMap.put("result",1);
                resultMap.put("msg","修改授权码失败");
            }
        }else {
            resultMap=validateMap;
        }
        jsonResult=resultMap;
        return "json";
    }

    /**
     * 删除开锁身份证
     * serviceNumb为授权时产生的操作序列号
     *
     * @return
     */
    public String prohibitUnlockById (){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        boolean isSuccess=unlockService.prohibitUnlockById(ownerPhoneNumber,lockCode,cardNumb,serviceNumb);
        jsonResult=isSuccess;
        return "json";
    }

    /**
     * 获取(当期帐户、当前网关、当前门锁)已授权的开锁密码信息
     *
     * @return
     */
    public String getUnlockPwd (){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        jsonResult=unlockService.getUnlockPwd(ownerPhoneNumber,gatewayCode,lockCode);
        return "json";
    }

    /**
     * 开锁密码授权
     *
     * @return
     */
    /*
    public String authUnlockByPwd (){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        boolean isValidAuthPassword = accountService.validAuthPassword(ownerPhoneNumber,authPassword);
        LOG.info("isValidAuthPassword:"+isValidAuthPassword);
        boolean isSuccess;
        if (isValidAuthPassword){
            isSuccess=unlockService.authUnlockByPwd(ownerPhoneNumber,gatewayCode,lockCode,password,startTime,endTime);
        }else {
            isSuccess=false;
        }
        LOG.info("isSuccess:"+isSuccess);
        jsonResult=isSuccess;
        return "json";
    }
    */
    public String authUnlockByPwd (){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        Map resultMap=new HashMap();
        Map validateMap = accountService.validAuthPassword(ownerPhoneNumber,authPassword);
        if(0 == (int) validateMap.get("result")){
            boolean authBoolean=unlockService.authUnlockByPwd(ownerPhoneNumber,gatewayCode,lockCode,password,startTime,endTime);
            if (authBoolean){
                resultMap.put("result",0);
                resultMap.put("msg","修改授权码成功");
            }else {
                resultMap.put("result",1);
                resultMap.put("msg","修改授权码失败");
            }
        }else {
            resultMap=validateMap;
        }
        jsonResult=resultMap;
        return "json";
    }

    /**
     * 删除开锁密码
     * serviceNumb为授权时产生的操作序列号
     *
     * @return
     */
    public String prohibitUnlockByPwd (){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        boolean isSuccess=unlockService.prohibitUnlockByPwd(ownerPhoneNumber,gatewayCode,lockCode,serviceNumb);
        jsonResult=isSuccess;
        return "json";
    }

    /*另一种方式返回Model(实测,成功).有无<result>都能成功.
    public void prohibitUnlockByPwd () {
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
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
