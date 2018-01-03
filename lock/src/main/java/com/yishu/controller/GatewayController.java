package com.yishu.controller;

import com.yishu.service.IGatewayService;
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
 * @version 1.0.0.0 2018-01-03 13:27 admin
 * @since JDK1.7
 */
@Controller
@RequestMapping("/gateway")
public class GatewayController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("GatewayController");

    @Autowired
    private IGatewayService gatewayService;

    private boolean resultBoolean;
    private List resultList;
    private Map resultMap;
    private int resultInt;

    @RequestMapping("/getGatewayIp.do")
    @ResponseBody
    public String getGatewayIp(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- gateway/getGatewayIp.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String gatewayIp=gatewayService.getGatewayIp(ownerPhoneNumber,gatewayCode);
        return gatewayIp;
    }

    @RequestMapping("/hasGatewayAdded.do")
    @ResponseBody
    public Map getUnlockId(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- gateway/hasGatewayAdded.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        resultMap=gatewayService.hasGatewayAdded(ownerPhoneNumber,gatewayCode);
        return resultMap;
    }

    @RequestMapping("/getGatewayLANIp.do")
    @ResponseBody
    public Map getGatewayLANIp(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- gateway/getGatewayLANIp.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        resultMap=gatewayService.getGatewayLANIp(ownerPhoneNumber,gatewayCode);
        return resultMap;
    }

    @RequestMapping("/isCorrectGatewayVerificationCode.do")
    @ResponseBody
    public int isCorrectGatewayVerificationCode(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- gateway/isCorrectGatewayVerificationCode.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String opCode=request.getParameter("opCode");
        resultInt=gatewayService.isCorrectGatewayVerificationCode(ownerPhoneNumber,gatewayCode,opCode);
        return resultInt;
    }

    @RequestMapping("/registerGatewayInfo.do")
    @ResponseBody
    public boolean registerGatewayInfo(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- gateway/registerGatewayInfo.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String gatewayName=request.getParameter("gatewayName");
        String gatewayLocation=request.getParameter("gatewayLocation");
        String gatewayComment=request.getParameter("gatewayComment");
        String opCode=request.getParameter("opCode");
        resultBoolean=gatewayService.registerGatewayInfo(ownerPhoneNumber,gatewayCode,gatewayName,gatewayLocation,gatewayComment,opCode);
        return resultBoolean;
    }

    @RequestMapping("/modifyGatewayInfo.do")
    @ResponseBody
    public boolean modifyGatewayInfo(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- gateway/modifyGatewayInfo.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String gatewayName=request.getParameter("gatewayName");
        String gatewayLocation=request.getParameter("gatewayLocation");
        String gatewayComment=request.getParameter("gatewayComment");
        resultBoolean=gatewayService.modifyGatewayInfo(ownerPhoneNumber,gatewayCode,gatewayName,gatewayLocation,gatewayComment);
        return resultBoolean;
    }

    @RequestMapping("/deleteGateway.do")
    @ResponseBody
    public boolean deleteGateway(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- gateway/deleteGateway.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        resultBoolean=gatewayService.deleteGateway(ownerPhoneNumber,gatewayCode);
        return resultBoolean;
    }

    @RequestMapping("/getVerificationCode.do")
    @ResponseBody
    public String getVerificationCode(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- gateway/getVerificationCode.do -->>--");
        }
        return null;
    }
}
