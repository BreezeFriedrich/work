package com.yishu.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yishu.pojo.*;
import com.yishu.service.IRecordService;
import com.yishu.util.DateUtil;
import com.yishu.util.PageUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

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
        resultList=recordService.getUnlockRecord(ownerPhoneNumber,Long.parseLong(startTime),Long.parseLong(endTime));
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
        Records<UnlockRecord> records=recordService.getUnlockRecordPage(ownerPhoneNumber,Long.parseLong(startTime),Long.parseLong(endTime),pageNum,pageSize);
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
        Records<UnlockRecord> records=recordService.getGatewayUnlockRecordPage(ownerPhoneNumber,Long.parseLong(startTime),Long.parseLong(endTime),gatewayCode,pageNum,pageSize);
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
        List recordList=recordService.getLockUnlockRecord(ownerPhoneNumber,Long.parseLong(startTime),Long.parseLong(endTime),lockCode);
        Records<UnlockRecord> records=new Records<>();
        records.setTotalSize(recordList.size());
        records.setRows(recordList);
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
//        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
        String lockCode=request.getParameter("lockCode");
        String theDateStr=request.getParameter("theDate");
        JsonDto jsonDto=null;
        BizDto bizDto=null;
        Date theDate= null;
        try {
            theDate = DateUtil.yyyy_MM_dd.parse(theDateStr);
            Records<UnlockRecord>[] dailyRecords=recordService.getLockUnlockRecordDaily(ownerPhoneNumber,theDate,lockCode);
            if(null==dailyRecords){
                bizDto=BizDto.EMPTY_RESULT;
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

    @RequestMapping("/getDailyUnlockRecordLockPage.do")
    @ResponseBody
    public JsonDto getDailyUnlockRecordLockPage(HttpServletRequest request) throws ParseException {
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getDailyUnlockRecordLockPage.do -->>--");
        }
        JsonDto jsonDto=null;
        BizDto bizDto=null;
        HttpSession session=request.getSession(false);
//        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
        String theDateStr=request.getParameter("date");
        if(null==theDateStr){
            jsonDto=JsonDto.WRONG_REQUEST_PARAM;
            return jsonDto;
        }
        Date theDate= null;
        theDate = DateUtil.yyyy_MM_dd.parse(theDateStr);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(theDate);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        final long startTime=calendar.getTime().getTime();
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        final long endTime=calendar.getTime().getTime();
        long[] period={startTime,endTime};

        HashMap filterMap= new HashMap(10);
        //过滤条件
//        filterMap.put("period",period);
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        filterMap.put("gatewayCode",gatewayCode);
        filterMap.put("lockCode",lockCode);
        //获取排序字段
        HashMap orderMap= new HashMap(2);
        String orderColumn = request.getParameter("orderColumn");
        if(orderColumn == null){
            orderColumn = "timestamp";
        }
        orderMap.put("orderColumn",orderColumn);
        //获取排序方式
        String orderDir = request.getParameter("orderDir");
        if(orderDir == null){
            orderDir = "desc";
        }
        orderMap.put("orderDir",orderDir);
        String draw = request.getParameter("draw");//防跨站脚本随机数,直接返回前台
        //数据起始位置
        String startIndex = request.getParameter("startIndex");
        //每页显示的条数
        String pageSize = request.getParameter("pageSize");


        List<UnlockRecord> recordList=recordService.getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        if(null==recordList){
            bizDto=BizDto.EMPTY_RESULT;
            jsonDto=new JsonDto(bizDto);
            return jsonDto;
        }
        List<UnlockRecord> recordList2=recordService.filterUnlockRecord(recordList,filterMap);
        Map<String, Object> info = new HashMap<String, Object>();
        if(recordList2==null){
            info.put("pageData",null);
            info.put("total",0);
        }else{
            /*
            PageUtil<UnlockRecord> pageUtil=new PageUtil<UnlockRecord>(recordList2);
            pageUtil.remodel((Integer.parseInt(pageSize)),Integer.parseInt(startIndex));
            info.put("pageData", pageUtil.getList());
            info.put("total", pageUtil.getTotal());
            */
            List<UnlockRecord> recordList3=PageUtil.page(recordList2,Integer.parseInt(pageSize),Integer.parseInt(startIndex));
            List<UnlockRecordTableData> recordTableDataList=recordService.convertUnlockRecordToTabularData(recordList3);
            info.put("pageData", recordTableDataList);
            info.put("total", recordTableDataList.size());
        }
        info.put("draw", Integer.parseInt(draw));//防止跨站脚本（XSS）攻击
        bizDto=new BizDto(info);
        jsonDto=new JsonDto(bizDto);
        return jsonDto;
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
        getdata=HttpUtil.httpsPostToGateway(postdata);
//        logger.info("#DATA     ~ "+getdata);

        deviceStatusList=getDataListFromJson(getdata);
        if(deviceStatusList.size()>0){
            return deviceStatusList;
        }
        return null;
    }
    */
    @RequestMapping("/unlockRecordFilterOrderPage.do")
    @ResponseBody
    public JsonDto unlockRecordFilterOrderPage(HttpServletRequest request) throws ParseException {
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/unlockRecordFilterOrderPage.do -->>--");
        }
        JsonDto jsonDto=null;
        BizDto bizDto=null;
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
//        String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
        if(null==ownerPhoneNumber || "".equals(ownerPhoneNumber)){
            jsonDto=JsonDto.WRONG_REQUEST_PARAM;
            return jsonDto;
        }
        String startTimeStr=request.getParameter("startTime");
        String endTimeStr=request.getParameter("endTime");
        long startTime;
        long endTime=new Date().getTime();

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        endTime=calendar.getTime().getTime();
        calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)-1);
        startTime=calendar.getTime().getTime();
//        long[] period={startTime,endTime};
        if(null!=startTimeStr){
            calendar.setTime(DateUtil.yyyy_MM_dd0HH$mm.parse(startTimeStr));
            startTime=calendar.getTime().getTime();
        }
        if(null!=endTimeStr){
            calendar.setTime(DateUtil.yyyy_MM_dd0HH$mm.parse(endTimeStr));
            endTime=calendar.getTime().getTime();
        }

        HashMap filterMap= new HashMap(4);
        //过滤条件
//        filterMap.put("period",period);
        String gatewayCode=request.getParameter("gatewayCode");
        String gatewayName=request.getParameter("gatewayName");
        String lockCode=request.getParameter("lockCode");
        String lockName=request.getParameter("lockName");
        if(null!=gatewayCode && !"".equals(gatewayCode)){
            filterMap.put("gatewayCode",gatewayCode);
        }
        if(null!=gatewayName && !"".equals(gatewayName)){
            filterMap.put("gatewayName",gatewayName);
        }
        if(null!=lockCode && !"".equals(lockCode)){
            filterMap.put("lockCode",lockCode);
        }
        if(null!=lockName && !"".equals(lockName)){
            filterMap.put("lockName",lockName);
        }
        String openModeStr=request.getParameter("openMode");
        if(null!=openModeStr && !"".equals(openModeStr)){
            int openMode= Integer.parseInt(request.getParameter("openMode"));
            filterMap.put("openMode",openMode);
        }

        /*
        //获取排序字段
        HashMap orderMap= new HashMap(2);
        String orderColumn = request.getParameter("orderColumn");
        if(orderColumn == null){
            orderColumn = "timestamp";
        }
        orderMap.put("orderColumn",orderColumn);
        //获取排序方式
        String orderDir = request.getParameter("orderDir");
        if(orderDir == null){
            orderDir = "desc";
        }
        orderMap.put("orderDir",orderDir);
        */

//        String[] order=request.getParameterValues("order");
        String orderStr=request.getParameter("order");
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode rootNode= null;
        List orderList=new ArrayList(2);
        if(null!=orderStr){
//        Order[] orders;
            try {
                rootNode = objectMapper.readTree(orderStr);
                orderList=objectMapper.readValue(rootNode.traverse(), new TypeReference<List<Order>>(){});
//            orders=objectMapper.treeToValue(rootNode,Order[].class);
            /*
            Iterator<JsonNode> elements = rootNode.elements();
            Order order;
            while(elements.hasNext()){
                JsonNode orderNode = elements.next();
                order=objectMapper.readValue(orderNode.traverse(),Order.class);
                orderList.add(order);
            }
            */
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(orderList.isEmpty()){
            Order order=new Order();
            order.setOrderColumn("timestamp");
            order.setOrderDir("desc");
            orderList.add(order);
        }

        String draw = request.getParameter("draw");//防跨站脚本随机数,直接返回前台
        //数据起始位置
        String startIndex = request.getParameter("startIndex");
        //每页显示的条数
        String pageSize = request.getParameter("pageSize");

        List<UnlockRecord> recordList=recordService.getUnlockRecord(ownerPhoneNumber,startTime,endTime);
        if(null==recordList){
            bizDto=BizDto.EMPTY_RESULT;
            jsonDto=new JsonDto(bizDto);
            return jsonDto;
        }
        List<UnlockRecord> recordList2=recordService.filterUnlockRecord(recordList,filterMap);
        List<UnlockRecordTableData> recordTableDataList=recordService.convertUnlockRecordToTabularData(recordList2);
        List<UnlockRecordTableData> recordTableDataList2=recordService.orderUnlockRecordTableData(recordTableDataList,orderList);
        Map<String, Object> info = new HashMap<String, Object>();
        if(recordTableDataList2==null){
            info.put("pageData",null);
            info.put("total",0);
        }else{
            /*
            PageUtil<UnlockRecordTableData> pageUtil=new PageUtil<UnlockRecord>(recordTableDataList2);
            pageUtil.remodel((Integer.parseInt(pageSize)),Integer.parseInt(startIndex));
            info.put("pageData", pageUtil.getList());
            info.put("total", pageUtil.getTotal());
            */
            List<UnlockRecordTableData> recordTableDataList3=PageUtil.page(recordTableDataList2,Integer.parseInt(pageSize),Integer.parseInt(startIndex));
            info.put("pageData", recordTableDataList3);
            info.put("total", recordTableDataList2.size());
        }
        info.put("draw", Integer.parseInt(draw));//防止跨站脚本（XSS）攻击
        bizDto=new BizDto(info);
        jsonDto=new JsonDto(bizDto);
        return jsonDto;
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
        Records<UnlockRecord> records=recordService.getLockUnlockRecordPage(ownerPhoneNumber,Long.parseLong(startTime),Long.parseLong(endTime),lockCode,pageNum,pageSize);
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
        resultMap=recordService.getUnlockRecordDevice(ownerPhoneNumber,Long.parseLong(startTime),Long.parseLong(endTime));
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
        resultMap=recordService.getUnlockRecordDevicePage(ownerPhoneNumber,Long.parseLong(startTime),Long.parseLong(endTime),pageNum,pageSize);
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
        resultMap=recordService.getUnlockOperator(ownerPhoneNumber,Long.parseLong(startTime),Long.parseLong(endTime));
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
        Records<UnlockRecord> records=recordService.getOperatorUnlockRecordPage(ownerPhoneNumber,Long.parseLong(startTime),Long.parseLong(endTime),cardNum,pageNum,pageSize);
        return records;
    }
}
