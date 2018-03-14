package com.yishu.controller;

import com.yishu.pojo.*;
import com.yishu.service.IRoomService;
import com.yishu.util.PageUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-12 11:16 admin
 * @since JDK1.7
 */
@Controller
@RequestMapping("/room")
public class RoomController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("RoomController");

    @Autowired
    private IRoomService roomService;

    private boolean resultBoolean;
    private List resultList;
    private Map resultMap;
    private int resultInt;

    @RequestMapping("/getRoom.do")
    @ResponseBody
    public JsonDto getRoom(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- room/getRoom.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        JsonDto jsonDto=null;
        BizDto bizDto=null;

        List<RoomTypeContainRoom> roomTypeCRList =roomService.getRoom(ownerPhoneNumber);
        if(null== roomTypeCRList || roomTypeCRList.isEmpty()){
            bizDto=BizDto.EMPTY_RESULT;
        }else {
            bizDto=new BizDto(roomTypeCRList);
        }
        jsonDto=new JsonDto(bizDto);
        return jsonDto;
    }

    @RequestMapping("/getRoomTableData.do")
    @ResponseBody
    public JsonDto getRoomTableData(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- room/getRoomTableData.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String draw = request.getParameter("draw");
        String startIndex = request.getParameter("startIndex");
        String pageSize = request.getParameter("pageSize");
        JsonDto jsonDto=null;
        BizDto bizDto=null;

        List<RoomTypeContainRoom> roomTypeCRList =roomService.getRoom(ownerPhoneNumber);

        Map<String, Object> info = new HashMap<String, Object>(3);
        if(null== roomTypeCRList){//可能是与后台通信异常
            bizDto=BizDto.EMPTY_RESULT;
            jsonDto=new JsonDto(bizDto);
            return jsonDto;
        }
        if (roomTypeCRList.isEmpty()){
            info.put("pageData",null);
            info.put("total",0);
        }else {
            List<RoomTableData> tableDataList=roomService.convertRoomTypeCRToRoomTableData(roomTypeCRList);
            if (null==tableDataList||tableDataList.isEmpty()){
                info.put("pageData",null);
                info.put("total",0);
            }else{
                List<RoomTableData> tableDataList1 =PageUtil.page(tableDataList,Integer.parseInt(pageSize),Integer.parseInt(startIndex));
                info.put("pageData", tableDataList1);
                info.put("total", tableDataList1.size());
            }
        }
        info.put("draw", Integer.parseInt(draw));//防止跨站脚本（XSS）攻击
        bizDto=new BizDto(info);
        jsonDto=new JsonDto(bizDto);
        return jsonDto;
    }

    @RequestMapping(value="/getRoomTypeTableData.do", method= RequestMethod.POST)
    @ResponseBody
    public JsonDto getRoomTypeTableData(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- room/getRoomTypeTableData.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String draw = request.getParameter("draw");
        String startIndex = request.getParameter("startIndex");
        String pageSize = request.getParameter("pageSize");
        JsonDto jsonDto=null;
        BizDto bizDto=null;

        List<RoomTypeContainRoom> roomTypeCRList =roomService.getRoom(ownerPhoneNumber);
        Map<String, Object> info = new HashMap<String, Object>(3);
        if(null== roomTypeCRList){//可能是与后台通信异常
            bizDto=BizDto.EMPTY_RESULT;
            jsonDto=new JsonDto(bizDto);
            return jsonDto;
        }
        if (roomTypeCRList.isEmpty()){
            info.put("pageData",null);
            info.put("total",0);
        }else {
            List<RoomTypeContainRoom> roomTypeCRList1 =PageUtil.page(roomTypeCRList,Integer.parseInt(pageSize),Integer.parseInt(startIndex));
            List<RoomTypeTableData> tableDataList=roomService.convertRoomTypeToTabularData(roomTypeCRList1);
            info.put("pageData", tableDataList);
            info.put("total", tableDataList.size());
        }
        info.put("draw", Integer.parseInt(draw));//防止跨站脚本（XSS）攻击
        bizDto=new BizDto(info);
        jsonDto=new JsonDto(bizDto);
        return jsonDto;
    }

    @RequestMapping("/addRoomType.do")
    @ResponseBody
    public boolean addRoomType(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-->>-- room/addRoomType.do -->>--");
        }
        HttpSession session = request.getSession(false);
        String ownerPhoneNumber = (String) session.getAttribute("ownerPhoneNumber");
        String roomType=request.getParameter("roomType");
        resultBoolean=roomService.addRoomType(ownerPhoneNumber,roomType);
        return resultBoolean;
    }

    @RequestMapping("/editRoomType.do")
    @ResponseBody
    public boolean editRoomType(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-->>-- room/editRoomType.do -->>--");
        }
        HttpSession session = request.getSession(false);
        String ownerPhoneNumber = (String) session.getAttribute("ownerPhoneNumber");
        String roomTypeId=request.getParameter("roomTypeId");
        String newRoomType=request.getParameter("newRoomType");
        resultBoolean=roomService.editRoomType(ownerPhoneNumber,roomTypeId,newRoomType);
        return resultBoolean;
    }

    @RequestMapping("/deleteRoomType.do")
    @ResponseBody
    public boolean deleteRoomType(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-->>-- room/deleteRoomType.do -->>--");
        }
        HttpSession session = request.getSession(false);
        String ownerPhoneNumber = (String) session.getAttribute("ownerPhoneNumber");
        String roomTypeId=request.getParameter("roomTypeId");
        resultBoolean=roomService.deleteRoomType(ownerPhoneNumber,roomTypeId);
        return resultBoolean;
    }

    @RequestMapping("/addRoom.do")
    @ResponseBody
    public boolean addRoom(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-->>-- room/addRoom.do -->>--");
        }
        HttpSession session = request.getSession(false);
        String ownerPhoneNumber = (String) session.getAttribute("ownerPhoneNumber");
        String roomTypeId=request.getParameter("roomTypeId");
        String roomName=request.getParameter("roomName");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        resultBoolean=roomService.addRoom(ownerPhoneNumber,roomTypeId,roomName,gatewayCode,lockCode);
        return resultBoolean;
    }

    @RequestMapping("/editRoom.do")
    @ResponseBody
    public boolean editRoom(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-->>-- room/editRoom.do -->>--");
        }
        HttpSession session = request.getSession(false);
        String ownerPhoneNumber = (String) session.getAttribute("ownerPhoneNumber");
        String roomTypeId=request.getParameter("roomTypeId");
        String roomId=request.getParameter("roomId");
        String newRoomName=request.getParameter("newRoomName");
        String newLockCode=request.getParameter("newLockCode");
        resultBoolean=roomService.editRoom(ownerPhoneNumber,roomTypeId,roomId,newRoomName,newLockCode);
        return resultBoolean;
    }

    @RequestMapping("/deleteRoom.do")
    @ResponseBody
    public boolean deleteRoom(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-->>-- room/deleteRoom.do -->>--");
        }
        HttpSession session = request.getSession(false);
        String ownerPhoneNumber = (String) session.getAttribute("ownerPhoneNumber");
        String roomId=request.getParameter("roomId");
        resultBoolean=roomService.deleteRoom(ownerPhoneNumber,roomId);
        return resultBoolean;
    }

    @RequestMapping("/getUnusedDeviceList.do")
    @ResponseBody
    public JsonDto getUnusedDeviceList(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- room/getUnusedDeviceList.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        JsonDto jsonDto=null;
        BizDto bizDto=null;

        List<Map> unusedDeviceList=roomService.getUnusedDeviceList(ownerPhoneNumber);
        if(null==unusedDeviceList || unusedDeviceList.isEmpty()){
            bizDto=BizDto.EMPTY_RESULT;
        }else {
            bizDto=new BizDto(unusedDeviceList);
        }
        jsonDto=new JsonDto(bizDto);
        return jsonDto;
    }
}
