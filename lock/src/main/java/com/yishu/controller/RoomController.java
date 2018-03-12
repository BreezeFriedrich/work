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
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(RoomController.class);

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

    /////////////////////////////////////////////////
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
}
