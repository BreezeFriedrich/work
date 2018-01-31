package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.Device;
import com.yishu.pojo.GatewayLock;
import com.yishu.pojo.Lock;
import com.yishu.service.IDeviceService;
import com.yishu.util.DataInject;
import com.yishu.util.FilterList;
import com.yishu.util.FilterListHook;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-03 11:01 admin
 * @since JDK1.7
 */
@Service("deviceService")
public class DeviceServiceImpl implements IDeviceService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DeviceServiceImpl.class);

    int reqSign;
    String reqData;
    String rawData;

    @Override
    public List getUserGatewayIp(String ownerPhoneNumber) {
        reqSign=15;
        LOG.info("sign:"+reqSign+" operation:getUserGatewayIp");
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToQixu(reqData);
        LOG.info("rawData : "+rawData);

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode rootNode= null;
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int respSign=rootNode.path("result").asInt();
        LOG.info("respSign:"+String.valueOf(respSign));
        if (0!=respSign){//请求数据失败
            return null;
        }
        JsonNode gatewayIpListNode=rootNode.path("gatewayIpList");
        List gatewayIpList=new ArrayList<Map>();
        try {
            gatewayIpList=objectMapper.readValue(gatewayIpListNode.traverse(), new TypeReference<List<Map>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gatewayIpList;
    }

    @Override
    public List getDeviceInfo(String ownerPhoneNumber) {
        reqSign=16;
        LOG.info("sign:"+reqSign+" operation:getDeviceInfo");
        reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        LOG.info("reqData : "+reqData);
        rawData= HttpUtil.httpsPostToQixu(reqData);
        LOG.info("rawData : "+rawData);

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode rootNode= null;
        try {
            rootNode = objectMapper.readTree(rawData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int respSign=rootNode.path("result").asInt();
        LOG.info("respSign:"+String.valueOf(respSign));
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
        LOG.info("sign:"+'空'+" operation:getDeviceInfo");
        //rawData,获取原始数据:开锁记录的List.
        /*
        List<Device> rawList=null;
        try {
            rawList=objectMapper.readValue(DataInject.readFile2String("classpath:device.json"),new TypeReference<List<Device>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
        List<Device> rawList=getDeviceInfo(ownerPhoneNumber);

        List<Device> deviceList=null;
        Device device=null;
        List<Lock> lockLists=null;
        for (Iterator itr = rawList.iterator(); itr.hasNext();){
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

    @Override
    public List<GatewayLock> convertDeviceToGatewayLock(Device device) {
        List gatewayLocks=new ArrayList<GatewayLock>();
        Iterator itr=device.getLockLists().iterator();
        GatewayLock gatewayLock=null;
        Lock lock=null;
        while (itr.hasNext()){
            gatewayLock=new GatewayLock();
            lock= (Lock) itr.next();
            gatewayLock.setLockCode(lock.getLockCode());
            gatewayLock.setLockName(lock.getLockName());
            gatewayLock.setLockLocation(lock.getLockLocation());
            gatewayLock.setLockComment(lock.getLockComment());
            gatewayLock.setLockPower(lock.getLockPower());
            gatewayLock.setLockStatus(lock.getLockStatus());
            gatewayLock.setGatewayCode(device.getGatewayCode());
            gatewayLock.setGatewayName(device.getGatewayName());
            gatewayLock.setGatewayLocation(device.getGatewayLocation());
            gatewayLock.setGatewayComment(device.getGatewayComment());
            gatewayLock.setGatewayStatus(device.getGatewayStatus());
            gatewayLocks.add(gatewayLock);
        }
        return gatewayLocks;
    }
}
