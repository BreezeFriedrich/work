package com.yishu.controller;

import com.yishu.pojo.Device;
import com.yishu.pojo.Lock;
import com.yishu.service.IDeviceService;
import com.yishu.service.ILockService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-03 13:49 admin
 * @since JDK1.7
 */
@Controller
@RequestMapping("/lock")
public class LockController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("LockController");

    @Autowired
    private ILockService lockService;

    @Autowired
    private IDeviceService deviceService;

    private boolean resultBoolean;
    private List resultList;
    private Map resultMap;
    private int resultInt;
    private Lock lock;

    @RequestMapping("/hasLockAdded.do")
    @ResponseBody
    public Map hasLockAdded(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- lock/hasLockAdded.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String lockCode=request.getParameter("lockCode");
        resultMap=lockService.hasLockAdded(ownerPhoneNumber,lockCode);
        return resultMap;
    }

    @RequestMapping("/addLock.do")
    @ResponseBody
    public boolean addLock(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- lock/addLock.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        String lockName=request.getParameter("lockName");
        String lockLocation=request.getParameter("lockLocation");
        String lockComment=request.getParameter("lockComment");
        resultBoolean=lockService.addLock(ownerPhoneNumber,gatewayCode,lockCode,lockName,lockLocation,lockComment);
        return resultBoolean;
    }

    @RequestMapping("/modifyLockInfo.do")
    @ResponseBody
    public boolean modifyLockInfo(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- lock/modifyLockInfo.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String lockCode=request.getParameter("lockCode");
        String lockName=request.getParameter("lockName");
        String lockLocation=request.getParameter("lockLocation");
        String lockComment=request.getParameter("lockComment");
        resultBoolean=lockService.modifyLockInfo(ownerPhoneNumber,lockCode,lockName,lockLocation,lockComment);
        return resultBoolean;
    }

    @RequestMapping("/deleteLock.do")
    @ResponseBody
    public boolean deleteLock(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- lock/deleteLock.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//        String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
        String lockCode=request.getParameter("lockCode");
        resultBoolean=lockService.deleteLock(ownerPhoneNumber,lockCode);
        return resultBoolean;
    }

    @RequestMapping("/getSpecificLock.do")
    @ResponseBody
    public Lock getSpecificLock(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- lock/getSpecificLock.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");

        List jsonList=deviceService.getDeviceInfo(ownerPhoneNumber);
        Device device=null;
        Iterator iter;
        //遍历网关,找到对应gatewayCode的网关信息
        iter=jsonList.iterator();
        while (iter.hasNext()) {
            device=(Device)(iter.next());
            String specificGatewayCode = device.getGatewayCode();
            if (gatewayCode.equals(specificGatewayCode)) {
                break;
            }
        }
        //遍历门锁,找到对应lockCode的门锁信息
        iter=device.getLockLists().iterator();
        while (iter.hasNext()) {
            lock=(Lock)(iter.next());
            String specificLockCode = lock.getLockCode();
            if (lockCode.equals(specificLockCode)) {
                break;
            }
        }
        return lock;
    }
}
