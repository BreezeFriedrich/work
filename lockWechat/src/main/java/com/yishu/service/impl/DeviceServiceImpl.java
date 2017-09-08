package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.Device;
import com.yishu.service.IDeviceService;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service("deviceService")
public class DeviceServiceImpl implements IDeviceService{
    org.slf4j.Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public List getUserGatewayIp(String ownerPhoneNumber) {
        int reqSign=15;
        String reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        String rawData= HttpUtil.doPostToQixu(reqData);
        System.err.println(rawData);

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

    @Override
    public List getDeviceInfo(String ownerPhoneNumber) {
        int reqSign=16;
        String reqData="{\"sign\":\""+reqSign+"\",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
        String rawData= HttpUtil.doPostToQixu(reqData);
        System.err.println(rawData);

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
}