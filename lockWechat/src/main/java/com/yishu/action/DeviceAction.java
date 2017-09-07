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

    public String getAllGatewayIp(){
        List allGatewayIp=deviceService.getAllGatewayIp(ownerPhoneNumber);System.err.println(allGatewayIp.size());
        Map jsonMap=new HashMap<String,Object>();
        jsonMap.put("allGatewayIp",allGatewayIp);
        return "json";
    }
}
