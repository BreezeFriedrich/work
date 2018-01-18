package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.Lock;
import com.yishu.util.*;
import com.yishu.pojo.Device;
import com.yishu.service.IDeviceService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service("deviceService")
public class DeviceServiceImpl implements IDeviceService{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("DeviceServiceImpl");

    int reqSign;
    String reqData;
    String rawData;

    ObjectMapper objectMapper=new ObjectMapper();

    /**
     * 获取当前帐户ownerPhoneNumber所有的网关数据服务器的IP (request URL: lock.qixutech.com)
     *
     * @return type=Map structure: { "result": int(0成功,1失败) , "gatewayIpList" : [ { "gatewayCode" : String , "gatewayIp" : String(网关数据所在IP) } ] }
     */
    @Override
    public List getUserGatewayIp(String ownerPhoneNumber) {
        reqSign=15;
        logger.info("sign:"+reqSign+" operation:getUserGatewayIp");
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        rawData=null;
        rawData= HttpUtil.httpsPostToQixu(reqData);
//        logger.info(rawData);

        ObjectMapper objectMapper=new ObjectMapper();
        Map gatewayIpMap;
        JsonNode rootNode= null;
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int respSign=rootNode.path("result").asInt();
        logger.info("respSign:"+String.valueOf(respSign));
        if (0!=respSign){//请求数据失败
            return null;
        }
        JsonNode gatewayIpListNode=rootNode.path("gatewayIpList");
        /*
        Iterator itr=gatewayIpListNode.elements();
        while (itr.hasNext()){
            JsonNode tempNode= (JsonNode) itr.next();
            gatewayIpMap=new HashMap();
            gatewayIpMap.put("gatewayCode",tempNode.path("gatewayCode").asText());
            gatewayIpMap.put("gatewayIp",tempNode.path("gatewayIp").asText());
            gatewayIpList.add(gatewayIpMap);
        }
        */
        List gatewayIpList=new ArrayList<Map>();
        try {
            gatewayIpList=objectMapper.readValue(gatewayIpListNode.traverse(), new TypeReference<List<Map>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gatewayIpList;
    }

    /**
     * 获取当前帐户ownerPhoneNumber所有的设备信息accountDeviceList (request URL: gatewayIp)
     *
     * @return
     */
    @Override
    public List getDeviceInfo(String ownerPhoneNumber) {
        reqSign=16;
        logger.info("sign:"+reqSign+" operation:getDeviceInfo");
//        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        rawData=null;
        rawData= HttpUtil.httpsPostToQixu(reqData);
//        logger.info(rawData);

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode rootNode= null;
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int respSign=rootNode.path("result").asInt();
        logger.info("respSign:"+String.valueOf(respSign));
        if (0!=respSign){//请求数据失败
            return null;
        }
        JsonNode deviceNode=rootNode.path("devices");
        List accountDeviceList=new ArrayList<Map>();
        try {
            accountDeviceList=objectMapper.readValue(deviceNode.traverse(), new TypeReference<List<Device>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accountDeviceList;
    }

    @Override
    public List getAbnormalDevice(String ownerPhoneNumber) {
        logger.info("sign:"+'空'+" operation:getDeviceInfo");
        //rawData,获取原始数据:开锁记录的List.
        List<Device> rawList=getDeviceInfo(ownerPhoneNumber);
        List<Device> deviceList=null;
        /*
        //注入假数据用于测试.
        if (rawList.isEmpty()){
            try {
                rawList=objectMapper.readValue(DataInject.readFile2String("classpath:device.json"),new TypeReference<List<Device>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */
        if (rawList.isEmpty()){
            return deviceList;
        }
        Device device=null;
        List<Lock> lockLists=null;
        for (Iterator itr=rawList.iterator();itr.hasNext();){
            device= (Device) itr.next();
            lockLists= device.getLockLists();
            lockLists= FilterList.filter(lockLists, new FilterListHook<Lock>() {
                @Override
                public boolean test(Lock lock) {
                    return !lock.getLockStatus().equals("1");
                }
            });
            device.setLockLists(lockLists);
        }
        deviceList= FilterList.filter(rawList, new FilterListHook<Device>() {
            @Override
            public boolean test(Device device) {
                return !device.getGatewayStatus().equals("4") || device.getLockLists().size()>0;
            }
        });
        return deviceList;
    }

    @Override
    public int countAbnormalDevice(String ownerPhoneNumber) {
        logger.info("sign:"+'空'+" operation:countAbnormalDevice");
        int num=0;
        Device device=null;
        List list=getAbnormalDevice(ownerPhoneNumber);
        for (Iterator itr=list.iterator();itr.hasNext();){
            device= (Device) itr.next();
            if (!device.getGatewayStatus().equals("4")){
                num++;
            }
            num+=device.getLockLists().size();
        }
        return num;
    }
}
