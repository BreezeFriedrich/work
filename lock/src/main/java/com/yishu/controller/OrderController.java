package com.yishu.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.AuthOrder;
import com.yishu.pojo.BizDto;
import com.yishu.pojo.CardInfo;
import com.yishu.pojo.JsonDto;
import com.yishu.service.IOrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("OrderController");

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
        String roomTypeId=request.getParameter("roomTypeId");
        String roomId=request.getParameter("roomId");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        String password=request.getParameter("password");
        String cardInfoListStr=request.getParameter("cardInfoList");
        ObjectMapper objectMapper=new ObjectMapper();
        List<CardInfo> cardInfoList=null;
        try {
            cardInfoList=objectMapper.readValue(cardInfoListStr, new TypeReference<List<CardInfo>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultBoolean=orderService.increaseOrder(ownerPhoneNumber,roomTypeId,roomId,startTime,endTime,password,cardInfoList);
        return resultBoolean;
    }

    @RequestMapping("/modifyAuthOrder.do")
    @ResponseBody
    public boolean modifyAuthOrder(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-->>-- order/modifyAuthOrder.do -->>--");
        }
        HttpSession session = request.getSession(false);
        String ownerPhoneNumber = (String) session.getAttribute("ownerPhoneNumber");
        String orderNumber=request.getParameter("orderNumber");
        String password=request.getParameter("password");
        String roomId=request.getParameter("roomId");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        String cardInfoListStr=request.getParameter("cardInfoList");
        ObjectMapper objectMapper=new ObjectMapper();
        List<CardInfo> cardInfoList=null;
        try {
            cardInfoList=objectMapper.readValue(cardInfoListStr, new TypeReference<List<CardInfo>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultBoolean=orderService.modifyAuthOrder(ownerPhoneNumber,orderNumber,password,roomId,startTime,endTime,cardInfoList);
        return resultBoolean;
    }

    @RequestMapping("/cancleAuthOrder.do")
    @ResponseBody
    public boolean cancleAuthOrder(HttpServletRequest request) {
        if (LOG.isInfoEnabled()) {
            LOG.info("-->>-- order/cancleAuthOrder.do -->>--");
        }
        HttpSession session = request.getSession(false);
        String ownerPhoneNumber = (String) session.getAttribute("ownerPhoneNumber");
        String orderNumber=request.getParameter("orderNumber");
        resultBoolean=orderService.cancleAuthOrder(ownerPhoneNumber,orderNumber);
        return resultBoolean;
    }
}
