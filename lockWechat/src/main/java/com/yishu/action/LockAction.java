/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yishu.pojo.Device;
import com.yishu.pojo.Lock;
import com.yishu.service.IDeviceService;
import com.yishu.service.ILockService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-12 10:15 admin
 * @since JDK1.7
 */
public class LockAction extends ActionSupport {
    public LockAction() {
        LOG.info(">>>Initialization LockAction......................................");
    }
    private org.slf4j.Logger LOG = LoggerFactory.getLogger("LockAction");

    @Autowired
    private ILockService lockService;
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
    private String lockCode;
    private String lockName;
    private String lockLocation;
    private String lockComment;

    private Lock lock;
    public Lock getLock() {
        return lock;
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

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public String getLockLocation() {
        return lockLocation;
    }

    public void setLockLocation(String lockLocation) {
        this.lockLocation = lockLocation;
    }

    public String getLockComment() {
        return lockComment;
    }

    public void setLockComment(String lockComment) {
        this.lockComment = lockComment;
    }

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
//    Map<String,Object> sessionMap=ActionContext.getContext().getSession();

    public String hasLockAdded(){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        Map resultMap=lockService.hasLockAdded(ownerPhoneNumber,lockCode);
        jsonResult=resultMap;
        return "json";
    }

    public String addLock(){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        boolean resultBoolean=lockService.addLock(ownerPhoneNumber,gatewayCode,lockCode,lockName,lockLocation,lockComment);
        jsonResult=resultBoolean;
        return "json";
    }

    public String modifyLockInfo(){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        boolean resultBoolean=lockService.modifyLockInfo(ownerPhoneNumber,lockCode,lockName,lockLocation,lockComment);
        jsonResult=resultBoolean;
        return "json";
    }

    public String deleteLock(){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        boolean resultBoolean=lockService.deleteLock(ownerPhoneNumber,lockCode);
        jsonResult=resultBoolean;
        return "json";
    }

    public String getSpecificLock(){
        if ("".equals(ownerPhoneNumber)||null==ownerPhoneNumber){
            ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        }
        List jsonList=deviceService.getDeviceInfo(ownerPhoneNumber);
        Device device=null;
        Iterator iter;
        //遍历网关,找到对应gatewayCode的网关信息
        iter=jsonList.iterator();
        while (iter.hasNext()) {
            device=(Device)(iter.next());
            String specificGatewayCode = device.getGatewayCode();
            if (gatewayCode.equals(specificGatewayCode)) {//gatewayCode来自于struts2 拦截器栈 参数拦截器传递类request.getParameter()
                break;
            }
        }
        //遍历门锁,找到对应lockCode的门锁信息
        if(null==device || null==device.getLockLists()){
            jsonResult=null;
        }else {
            iter=device.getLockLists().iterator();
            while (iter.hasNext()) {
                lock=(Lock)(iter.next());
                String specificLockCode = lock.getLockCode();
                if (lockCode.equals(specificLockCode)) {//gatewayCode来自于struts2 拦截器栈 参数拦截器传递类request.getParameter()
                    break;
                }
            }
            jsonResult=lock;
        }
        return "json";
    }
}
