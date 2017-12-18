package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yishu.pojo.Device;
import com.yishu.service.IDeviceService;
import com.yishu.util.FilterList;
import com.yishu.util.FilterListHook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeviceAction extends ActionSupport {
    public DeviceAction() {System.out.println(">>>Initialization DeviceAction......................................");}
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("DeviceAction");

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
    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }
    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    private String gatewayCode;
    public String getGatewayCode() {
        return gatewayCode;
    }
    public void setGatewayCode(String gatewayCode) {
        this.gatewayCode = gatewayCode;
    }
    /*
    private List jsonList=new ArrayList();
    public List getJsonList() {
        return jsonList;
    }
    */

    /**
     * 获取(当前账户ownerPhoneNumb下的)所有网关所在服务器的IP
     *
     * @return
     */
    public String getUserGatewayIp(){
        /*
        jsonList.clear();
        List userGatewayIp=deviceService.getUserGatewayIp(ownerPhoneNumber);
        jsonList.addAll(userGatewayIp);
        return "json";
        */
        List userGatewayIp=deviceService.getUserGatewayIp(ownerPhoneNumber);
        jsonResult=userGatewayIp;
        return "json";
    }

    /**
     * 获取设备信息(包含网关、门锁)
     *
     * @return
     */
    public String getDeviceInfo(){
        List unlockAccountDeviceList=deviceService.getDeviceInfo(ownerPhoneNumber);
        jsonResult=unlockAccountDeviceList;
        return "json";
    }

    /*
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
    */

    /**
     * 获取某个用户(ownerPhoneNumber)管理的某个网关(gatewayCode)下的设备信息(Device类).
     * @return
     */
    public String getSpecificGateway(){
        List jsonList=deviceService.getDeviceInfo(ownerPhoneNumber);
        List<Device> deviceList=null;
        deviceList= FilterList.filter(jsonList, new FilterListHook<Device>() {
            @Override
            public boolean test(Device device) {
                return gatewayCode.equals(device.getGatewayCode());
            }
        });
        //通常不会有编码相同的多个网关.
        if (deviceList.size()>1){
            logger.warn(ownerPhoneNumber+"名下,编号为"+gatewayCode+"的网关不只一个.");
        }
        jsonResult=deviceList.get(0);
        return "json";
    }

    public String getAbnormalDevice(){
        System.out.println("Action-DeviceAction-getAbnormalDevice");
        List jsonList=deviceService.getAbnormalDevice(ownerPhoneNumber);
        jsonResult=jsonList;
        return "json";
    }

    public String countAbnormalDevice(){
        System.out.println("Action-DeviceAction-countAbnormalDevice");
        int num=deviceService.countAbnormalDevice(ownerPhoneNumber);
        jsonResult=num;
        return "json";
    }
}
