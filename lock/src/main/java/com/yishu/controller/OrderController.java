package com.yishu.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.AuthOrder;
import com.yishu.pojo.BizDto;
import com.yishu.pojo.CardInfo;
import com.yishu.pojo.JsonDto;
import com.yishu.service.IOrderService;
import com.yishu.util.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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

    @RequestMapping("/getAuthOrderFromDate.do")
    @ResponseBody
    public JsonDto getAuthOrderFromDate(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- order/getAuthOrderFromDate.do -->>--");
        }
        HttpSession session=request.getSession(false);
//        session.setAttribute("ownerPhoneNumber","18255683932");
//        String ownerPhoneNumber="18255683932";
        String ownerPhoneNumber="13905169824";
//        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String theDateStr=request.getParameter("theDate");
        Date theDate= null;
        JsonDto jsonDto=null;
        try {
            theDate = DateUtil.yyyy_MM_dd.parse(theDateStr);
            if(null==theDate){
                jsonDto= JsonDto.EXCEPTION;
                return jsonDto;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BizDto bizDto=null;
        List<AuthOrder> authOrderList=orderService.getAuthOrderFromDate(ownerPhoneNumber,theDate);
        if(null==authOrderList || authOrderList.isEmpty()){
            bizDto=BizDto.EMPTY_RESULT;
        }else {
            bizDto=new BizDto(authOrderList);
        }
        jsonDto=new JsonDto(bizDto);
        return jsonDto;
    }

    @RequestMapping("/getAuthOrder.do")
    @ResponseBody
    public JsonDto getAuthOrder(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- order/getAuthOrder.do -->>--");
        }
        HttpSession session=request.getSession(false);
//        session.setAttribute("ownerPhoneNumber","13905169824");
//        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
        if(null!=ownerPhoneNumber){
            ownerPhoneNumber = (String) session.getAttribute("ownerPhoneNumber");
        }
        long startTime=Long.parseLong(request.getParameter("startTime"));
        long endTime=Long.parseLong(request.getParameter("endTime"));
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
        String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
        if(null!=ownerPhoneNumber){
            ownerPhoneNumber = (String) session.getAttribute("ownerPhoneNumber");
        }
        String roomTypeId=request.getParameter("roomTypeId");
        String roomId=request.getParameter("roomId");
        long startTime= 0;
        long endTime=0;
        try {
            startTime=DateUtil.yyyy_MM_dd0HH$mm.parse(request.getParameter("startTime")).getTime();
            endTime=DateUtil.yyyy_MM_dd0HH$mm.parse(request.getParameter("endTime")).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        long startTime=Long.parseLong(request.getParameter("startTime"));
        long endTime=Long.parseLong(request.getParameter("endTime"));
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
