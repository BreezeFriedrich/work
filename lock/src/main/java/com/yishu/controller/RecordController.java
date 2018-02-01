package com.yishu.controller;

import com.yishu.pojo.BizDto;
import com.yishu.pojo.JsonDto;
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
import java.util.HashMap;
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

    /*
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
    */
    @RequestMapping("/getLockUnlockRecordDaily.do")
    @ResponseBody
    public JsonDto getLockUnlockRecordDaily(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getLockUnlockRecordDaily.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String lockCode=request.getParameter("lockCode");
        String theDateStr=request.getParameter("theDate");
        JsonDto jsonDto=null;
        BizDto bizDto=null;
        Date theDate= null;
        try {
            theDate = DateUtil.yyyy_MM_dd.parse(theDateStr);
            Records<UnlockRecord>[] dailyRecords=recordService.getLockUnlockRecordDaily(ownerPhoneNumber,theDate,lockCode);
            if(null==dailyRecords){
                bizDto=BizDto.NO_RESULT;
            }else {
                bizDto=new BizDto(dailyRecords);
            }
            jsonDto=new JsonDto(bizDto);
//            throw(new ParseException("parseError",157));
        } catch (ParseException e) {
//            e.printStackTrace();
            LOG.error(null,e);
            jsonDto=JsonDto.EXCEPTION;
        }finally {
            return jsonDto;
        }
    }

    @RequestMapping("/getUnlockRecordLockDailyPage.do")
    @ResponseBody
    public Map getUnlockRecordLockDailyPage(HttpServletRequest request) throws ParseException {
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getUnlockRecordLockDailyPage.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");

        HashMap paramMap= new HashMap(15);
        paramMap.put("ownerPhoneNumber",ownerPhoneNumber);
        //直接返回前台
        String draw = request.getParameter("draw");
        //数据起始位置
        String startIndex = request.getParameter("startIndex");
        //每页显示的条数
        String pageSize = request.getParameter("pageSize");
        //获取排序字段
        String orderColumn = request.getParameter("orderColumn");
        if(orderColumn == null){
            orderColumn = "timestamp";
        }
        paramMap.put("orderColumn",orderColumn);
        //获取排序方式
        String orderDir = request.getParameter("orderDir");
        if(orderDir == null){
            orderDir = "desc";
        }
        paramMap.put("orderDir",orderDir);
        //查询条件
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        if(null != gatewayCode && !"".equals(gatewayCode)){
            paramMap.put("gatewayCode",gatewayCode);
        }
        if(null != lockCode && !"".equals(lockCode)){
            paramMap.put("lockCode",lockCode);
        }
        String theDateStr=request.getParameter("date");
        Date theDate= null;
        theDate = DateUtil.yyyy_MM_dd.parse(theDateStr);
        if(null != theDateStr && !"".equals(theDateStr)){
            paramMap.put("date",theDate);
        }
        Records<UnlockRecord>[] dailyRecords=recordService.getLockUnlockRecordDaily(ownerPhoneNumber,theDate,lockCode);

        List<DeviceStatus> deviceStatuses = moduleService.listAllWithStrategy(paramMap);
        Map<String, Object> info = new HashMap<String, Object>();
        if(deviceStatuses==null){
            info.put("pageData",null);
            info.put("total",0);
        }else{
            PageUtil<DeviceStatus> pageUtil=new PageUtil<DeviceStatus>(deviceStatuses);
            pageUtil.remodel((Integer.parseInt(pageSize)),Integer.parseInt(startIndex));
            info.put("pageData", pageUtil.getList());
            info.put("total", pageUtil.getTotal());
        }
        info.put("draw", Integer.parseInt(draw));//防止跨站脚本（XSS）攻击
//        return JSONObject.fromObject(info)+"";
        return info;
    }
    /*
    @Override
    public List<DeviceStatus> listAllWithStrategy(HashMap paramMap) {

        paramMap.put("sign",3);
        try {
            postdata=objectMapper.writeValueAsString(paramMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        logger.info("postdata:"+postdata);
        getdata=HttpUtil.postData(postdata);
//        logger.info("#DATA     ~ "+getdata);

        deviceStatusList=getDataListFromJson(getdata);
        if(deviceStatusList.size()>0){
            return deviceStatusList;
        }
        return null;
    }
    */

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
