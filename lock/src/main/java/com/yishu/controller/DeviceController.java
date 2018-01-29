package com.yishu.controller;

import com.yishu.pojo.Device;
import com.yishu.service.IDeviceService;
import com.yishu.service.IUserService;
import com.yishu.util.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
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

    @Autowired
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

//    @RequestMapping("/getDateArr.do")
//    @ResponseBody
//    public long[] getDateArr(HttpServletRequest request){
//        if (LOG.isInfoEnabled()){
//            LOG.info("-->>-- device/getDateArr.do -->>--");
//        }
//        HttpSession session=request.getSession(false);
//        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//        Date today=new Date();
//        Calendar calendar=Calendar.getInstance();
//        Date theDate;
////        calendar.setTime(today);
////        calendar.set(Calendar.HOUR_OF_DAY,0);
////        calendar.set(Calendar.MINUTE,0);
////        calendar.set(Calendar.SECOND,0);
////        calendar.set(Calendar.MILLISECOND,0);
////        theDate=calendar.getTime();
//        theDate=DateUtil.getStartOfDate(today);
//        Date startDate;
//        Date endDate;
//        calendar.setTime(theDate);
//        calendar.add(Calendar.DAY_OF_MONTH,-15);
//        startDate=calendar.getTime();
//        calendar.setTime(theDate);
//        calendar.add(Calendar.DAY_OF_MONTH,15);
//        endDate=calendar.getTime();
//        Date[] dateArr=new Date[31];
//        for(int i=-15;i<16;i++){
//            calendar.setTime(theDate);
//            calendar.add(Calendar.DAY_OF_MONTH,i);
//            dateArr[i+15]=calendar.getTime();
//        }
//        long startTime=Long.parseLong(request.getParameter("startTime"));
//        long endTime=Long.parseLong(request.getParameter("endTime"));
//        LOG.info("startTime:"+ DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(startTime)));
//        LOG.info("endTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(endTime)));
//
//        resultInt=deviceService.countAbnormalDevice(ownerPhoneNumber);
//        return resultInt;
//    }
}
