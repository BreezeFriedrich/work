package com.yishu.controller;

import com.yishu.pojo.AuthOrder;
import com.yishu.pojo.BizDto;
import com.yishu.pojo.JsonDto;
import com.yishu.service.IOrderService;
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
 * @version 1.0.0.0 2018-03-12 17:53 admin
 * @since JDK1.7
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private IOrderService orderService;

    private boolean resultBoolean;
    private List resultList;
    private Map resultMap;
    private int resultInt;

    @RequestMapping("/getAuthOrder.do")
    @ResponseBody
    public JsonDto getAuthOrder(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- order/getAuthOrder.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        JsonDto jsonDto=null;
        BizDto bizDto=null;

        List<AuthOrder> authOrderList=orderService.getAuthOrder(ownerPhoneNumber,startTime,endTime);
        if(null==authOrderList || authOrderList.isEmpty()){
            bizDto=BizDto.EMPTY_RESULT;
        }else {
            bizDto=new BizDto(authOrderList);
        }
        jsonDto=new JsonDto(bizDto);
        return jsonDto;
    }

    @RequestMapping("/increaseOrder.do")
    @ResponseBody
    public boolean increaseOrder(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-->>-- order/increaseOrder.do -->>--");
        }
        HttpSession session = request.getSession(false);
        String ownerPhoneNumber = (String) session.getAttribute("ownerPhoneNumber");
        String roomType=request.getParameter("roomType");
        resultBoolean=orderService.increaseOrder(ownerPhoneNumber,roomTypeId,roomId,startTime,endTime,password,cardInfoList);
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
