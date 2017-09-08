package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yishu.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceAction extends ActionSupport {
    public DeviceAction() {
        System.out.println(">>>Initialization AccountAction......................................");
    }
    @Autowired
    private IDeviceService deviceService;

    private String ownerPhoneNumber;
    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }
    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    private List jsonList;
    public List getJsonList() {
        return jsonList;
    }

    public String getUserGatewayIp(){
        jsonList.clear();
        List userGatewayIp=deviceService.getUserGatewayIp(ownerPhoneNumber);System.err.println(userGatewayIp.size());
        jsonList.addAll(userGatewayIp);
        return "json";
    }

    public String getDeviceInfo(){
        jsonList.clear();
        List unlockAccountDeviceList=deviceService.getDeviceInfo(ownerPhoneNumber);System.err.println(unlockAccountDeviceList.size());
        jsonList.addAll(unlockAccountDeviceList);
        return "json";
    }
}
