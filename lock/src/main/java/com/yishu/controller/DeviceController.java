package com.yishu.controller;

import com.yishu.pojo.Device;
import com.yishu.service.IDeviceService;
import com.yishu.service.IUserService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-03 11:00 admin
 * @since JDK1.7
 */
@Controller
@RequestMapping("/device")
public class DeviceController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("DeviceController");

    @Resource
    private IDeviceService deviceService;

    private List resultList;
    private int resultInt;

    @RequestMapping("/getUserGatewayIp.do")
    @ResponseBody
    public List getUserGatewayIp(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- device/getUserGatewayIp.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        resultList=deviceService.getUserGatewayIp(ownerPhoneNumber);
        return resultList;
    }

    /**
     *
     * @param request
     * @return resultList type:List<Device>
     */
    @RequestMapping("/getDeviceInfo.do")
    @ResponseBody
    public List getDeviceInfo(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- device/getDeviceInfo.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        resultList=deviceService.getDeviceInfo(ownerPhoneNumber);
        return resultList;
    }

    @RequestMapping("/getAbnormalDevice.do")
    @ResponseBody
    public List getAbnormalDevice(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- device/getAbnormalDevice.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        resultList=deviceService.getAbnormalDevice(ownerPhoneNumber);
        return resultList;
    }

    @RequestMapping("/countAbnormalDevice.do")
    @ResponseBody
    public int countAbnormalDevice(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- device/countAbnormalDevice.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        resultInt=deviceService.countAbnormalDevice(ownerPhoneNumber);
        return resultInt;
    }
}
