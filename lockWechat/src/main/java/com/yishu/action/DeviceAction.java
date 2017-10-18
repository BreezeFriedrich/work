package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yishu.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DeviceAction extends ActionSupport {
    public DeviceAction() {
        System.out.println(">>>Initialization DeviceAction......................................");
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

    private List jsonList=new ArrayList();
    public List getJsonList() {
        return jsonList;
    }

    /**
     * 获取(当前账户ownerPhoneNumb下的)所有网关所在服务器的IP
     *
     * @return
     */
    public String getUserGatewayIp(){
        jsonList.clear();
        List userGatewayIp=deviceService.getUserGatewayIp(ownerPhoneNumber);System.err.println(userGatewayIp.size());
        jsonList.addAll(userGatewayIp);
        return "json";
    }

    /**
     * 获取设备信息(包含网关、门锁)
     *
     * @return
     */
    public String getDeviceInfo(){
        jsonList.clear();
        List unlockAccountDeviceList=deviceService.getDeviceInfo(ownerPhoneNumber);System.err.println(unlockAccountDeviceList.size());
        jsonList.addAll(unlockAccountDeviceList);
        return "json";
    }
}
