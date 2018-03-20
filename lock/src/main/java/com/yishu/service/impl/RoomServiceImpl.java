package com.yishu.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.*;
import com.yishu.service.IRoomService;
import com.yishu.util.DateUtil;
import com.yishu.util.HttpUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-12 11:20 admin
 * @since JDK1.7
 */
@Service("roomService")
public class RoomServiceImpl implements IRoomService {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("RoomServiceImpl");

    int reqSign;
    String reqData;
    String rawData;
    String timetag;

    @Override
    public List<RoomTypeContainRoom> getRoom(String ownerPhoneNumber) {
        reqSign=2005;
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
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
        if (0!=respSign){//请求数据失败
            return null;
        }
        JsonNode roomListNode=rootNode.path("roomList");
        List<RoomTypeContainRoom> roomTypeCRList = null;
        try {
            roomTypeCRList =objectMapper.readValue(roomListNode.traverse(), new TypeReference<List<RoomTypeContainRoom>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return roomTypeCRList;
    }

    @Override
    public boolean addRoomType(String ownerPhoneNumber, String roomType) {
        reqSign=2006;
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"roomType\":\""+roomType+"\",\"timetag\":\""+timetag+"\"}";
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
        if(0==respSign){
            return true;
        }
        return false;
    }

    @Override
    public boolean editRoomType(String ownerPhoneNumber, String roomTypeId, String newRoomType) {
        reqSign=2007;
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"roomTypeId\":\""+roomTypeId+"\",\"newRoomType\":\""+newRoomType+"\",\"timetag\":\""+timetag+"\"}";
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
        if(0==respSign){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteRoomType(String ownerPhoneNumber, String roomTypeId) {
        reqSign=2008;
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"roomTypeId\":\""+roomTypeId+"\"}";
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
        if(0==respSign){
            return true;
        }
        return false;
    }

    @Override
    public boolean addRoom(String ownerPhoneNumber, String roomTypeId, String roomName, String gatewayCode, String lockCode) {
        reqSign=2009;
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"roomTypeId\":\""+roomTypeId+"\",\"roomName\":\""+roomName+"\",\"gatewayCode\":\""+gatewayCode+"\",\"lockCode\":\""+lockCode+"\",\"timetag\":\""+timetag+"\"}";
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
        if(0==respSign){
            return true;
        }
        return false;
    }

    @Override
    public boolean editRoom(String ownerPhoneNumber, String roomTypeId, String roomId, String newRoomName, String newLockCode) {
        reqSign=2010;
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"roomTypeId\":\""+roomTypeId+"\",\"roomId\":\""+roomId+"\",\"newLockCode\":\""+newLockCode+"\",\"newRoomName\":\""+newRoomName+"\",\"timetag\":\""+timetag+"\"}";
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
        if(0==respSign){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteRoom(String ownerPhoneNumber, String roomId) {
        reqSign=2011;
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\",\"roomId\":\""+roomId+"\"}";
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
        if(0==respSign){
            return true;
        }
        return false;
    }

    @Override
    public List getUnusedDeviceList(String ownerPhoneNumber) {
        reqSign=2012;
        timetag= DateUtil.getFormat2TimetagStr();
        reqData="{\"sign\":"+reqSign+",\"ownerPhoneNumber\":\""+ownerPhoneNumber+"\"}";
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
        if(0==respSign){
//            List<Map<String, Object>> list = null;
//            try {
//                list = objectMapper.readValue(rootNode.path("lockList").traverse(), List.class);
            List<GatewayinfoAndLockinfo> list = null;
            try {
                list = objectMapper.readValue(rootNode.path("lockList").traverse(), new TypeReference<List<GatewayinfoAndLockinfo>>() {});
            /*
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                Set<String> set = map.keySet();
                for (Iterator it = set.iterator(); it.hasNext();) {
                    String key = (String) it.next();
                    System.out.println(key + ":" + map.get(key));
                }
            }
            for (int i = 0; i < list.size(); i++) {
                    list.get(i).get("gatewayCode");
                    list.get(i).get("lockCode");
            }
            */
            return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将roomType数据转变为适合datatables的结构.
     *
     * @param roomTypeCRList
     * @return
     */
    @Override
    public List<RoomTypeTableData> convertRoomTypeToTabularData(List<RoomTypeContainRoom> roomTypeCRList) {
        List<RoomTypeTableData> tableDataList=new ArrayList<>();
        int size= roomTypeCRList.size();
        RoomTypeContainRoom roomTypeCR=null;
        RoomTypeTableData tableData=null;
        Map rowAttrMap=null;
        for(int i=0;i<size;i++){
            roomTypeCR = roomTypeCRList.get(i);
            tableData=new RoomTypeTableData();
            tableData.setRoomTypeId(roomTypeCR.getRoomTypeId());
            tableData.setRoomType(roomTypeCR.getRoomType());
            rowAttrMap=new HashMap(1);
            rowAttrMap.put("rowTypeId", roomTypeCR.getRoomTypeId());
            tableData.setDT_RowAttr(rowAttrMap);
            tableDataList.add(tableData);
        }
        return tableDataList;
    }

    /**
     * 将room数据转变为适合datatables的结构.
     *
     * @param roomTypeCRList
     * @return
     */
    @Override
    public List<RoomTableData> convertRoomTypeCRToRoomTableData(List<RoomTypeContainRoom> roomTypeCRList) {
        List<RoomTableData> tableDataList=new ArrayList<>();
        int rtcrlSize= roomTypeCRList.size();
        RoomTypeContainRoom roomTypeCR=null;
        List<Room> roomList=null;
        Room room=null;
        RoomTableData tableData=null;
        Map rowAttrMap=null;
        for(int i=0;i<rtcrlSize;i++){
            roomTypeCR = roomTypeCRList.get(i);
            roomList=roomTypeCR.getRoomInfoList();
            int roomListSize=roomList.size();
            for(int j=0;j<roomListSize;j++){
                room=roomList.get(j);
                tableData=new RoomTableData();
                tableData.setRoomTypeId(roomTypeCR.getRoomTypeId());
                tableData.setRoomType(roomTypeCR.getRoomType());
                tableData.setRoomId(room.getRoomId());
                tableData.setRoomName(room.getRoomName());
                tableData.setGatewayCode(room.getGatewayCode());
                tableData.setLockCode(room.getLockCode());
                rowAttrMap=new HashMap(2);
                rowAttrMap.put("rowTypeId", roomTypeCR.getRoomTypeId());
                rowAttrMap.put("rowId",room.getRoomId());
                tableData.setDT_RowAttr(rowAttrMap);
                tableDataList.add(tableData);
            }
        }
        return tableDataList;
    }
}
