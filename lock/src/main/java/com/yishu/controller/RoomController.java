package com.yishu.controller;

import com.yishu.pojo.BizDto;
import com.yishu.pojo.JsonDto;
import com.yishu.pojo.RoomType;
import com.yishu.service.IRoomService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

        List<RoomType> roomTypeList=roomService.getRoom(ownerPhoneNumber);
        if(null==roomTypeList || roomTypeList.isEmpty()){
            bizDto=BizDto.EMPTY_RESULT;
        }else {
            bizDto=new BizDto(roomTypeList);
        }
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
        String newRoomType=request.getParameter("roomType");
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