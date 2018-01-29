package com.yishu.controller;

import com.yishu.pojo.Records;
import com.yishu.pojo.UnlockRecord;
import com.yishu.service.IRecordService;
import com.yishu.util.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-03 14:05 admin
 * @since JDK1.7
 */
@Controller
@RequestMapping("/record")
public class RecordController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("RecordController");

    @Autowired
    private IRecordService recordService;

    private boolean resultBoolean;
    private List resultList;
    private Map resultMap;
    private int resultInt;

    /**
     * 获取用户ownerPhoneNumber的开锁记录
     *
     */
    @RequestMapping("/getUnlockRecord.do")
    @ResponseBody
    public List getUnlockRecord(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getUnlockRecord.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        resultList=recordService.getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        return resultList;
    }

    /**
     * 获取用户ownerPhoneNumber的开锁记录,再将数据分页.
     *
     * param pageNum 第几页的页码数字. param pageSize 每页可以展示多少条记录的数目.
     *
     */
    @RequestMapping("/getUnlockRecordPage.do")
    @ResponseBody
    public Records<UnlockRecord> getUnlockRecordPage(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getUnlockRecordPage.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        int pageNum= Integer.parseInt(request.getParameter("pageNum"));
        int pageSize= Integer.parseInt(request.getParameter("pageSize"));
        Records<UnlockRecord> records=recordService.getUnlockRecordPage(ownerPhoneNumber,startTime,endTime,pageNum,pageSize);
        return records;
    }

    @RequestMapping("/getGatewayUnlockRecordPage.do")
    @ResponseBody
    public Records<UnlockRecord> getGatewayUnlockRecordPage(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getGatewayUnlockRecordPage.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        int pageNum= Integer.parseInt(request.getParameter("pageNum"));
        int pageSize= Integer.parseInt(request.getParameter("pageSize"));
        Records<UnlockRecord> records=recordService.getGatewayUnlockRecordPage(ownerPhoneNumber,startTime,endTime,gatewayCode,pageNum,pageSize);
        return records;
    }


    @RequestMapping("/getLockUnlockRecord.do")
    @ResponseBody
    public Records<UnlockRecord> getLockUnlockRecord(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getLockUnlockRecord.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String lockCode=request.getParameter("lockCode");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        Records<UnlockRecord> records=recordService.getLockUnlockRecord(ownerPhoneNumber,startTime,endTime,lockCode);
        return records;
    }

    @RequestMapping("/getLockUnlockRecordDaily.do")
    @ResponseBody
    public Records<UnlockRecord>[] getLockUnlockRecordDaily(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getLockUnlockRecordDaily.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String lockCode=request.getParameter("lockCode");
        String theDateStr=request.getParameter("theDate");
        Date theDate= null;
        try {
            theDate = DateUtil.yyyy_MM_dd.parse(theDateStr);
            Records<UnlockRecord>[] dailyRecords=recordService.getLockUnlockRecordDaily(ownerPhoneNumber,theDate,lockCode);
            return dailyRecords;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/getLockUnlockRecordPage.do")
    @ResponseBody
    public Records<UnlockRecord> getLockUnlockRecordPage(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getLockUnlockRecordPage.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String lockCode=request.getParameter("lockCode");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        int pageNum= Integer.parseInt(request.getParameter("pageNum"));
        int pageSize= Integer.parseInt(request.getParameter("pageSize"));
        Records<UnlockRecord> records=recordService.getLockUnlockRecordPage(ownerPhoneNumber,startTime,endTime,lockCode,pageNum,pageSize);
        return records;
    }

    @RequestMapping("/getUnlockRecordDevice.do")
    @ResponseBody
    public Map getUnlockRecordDevice(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getUnlockRecordDevice.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        resultMap=recordService.getUnlockRecordDevice(ownerPhoneNumber,startTime,endTime);
        return resultMap;
    }

    @RequestMapping("/getUnlockRecordDevicePage.do")
    @ResponseBody
    public Map getUnlockRecordDevicePage(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getUnlockRecordDevicePage.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        int pageNum= Integer.parseInt(request.getParameter("pageNum"));
        int pageSize= Integer.parseInt(request.getParameter("pageSize"));
        resultMap=recordService.getUnlockRecordDevicePage(ownerPhoneNumber,startTime,endTime,pageNum,pageSize);
        return resultMap;
    }

    @RequestMapping("/getUnlockOperator.do")
    @ResponseBody
    public Map getUnlockOperator(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getUnlockOperator.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        resultMap=recordService.getUnlockOperator(ownerPhoneNumber,startTime,endTime);
        return resultMap;
    }

    @RequestMapping("/getOperatorUnlockRecordPage.do")
    @ResponseBody
    public Records<UnlockRecord> getOperatorUnlockRecordPage(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getOperatorUnlockRecordPage.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        String cardNum=request.getParameter("cardNum");
        int pageNum= Integer.parseInt(request.getParameter("pageNum"));
        int pageSize= Integer.parseInt(request.getParameter("pageSize"));
        Records<UnlockRecord> records=recordService.getOperatorUnlockRecordPage(ownerPhoneNumber,startTime,endTime,cardNum,pageNum,pageSize);
        return records;
    }
}
