package com.yishu.controller;

import com.yishu.pojo.*;
import com.yishu.service.IUnlockService;
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

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-03 11:43 admin
 * @since JDK1.7
 */
@Controller
@RequestMapping("/unlock")
public class UnlockController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("UnlockController");

    @Autowired
    private IUnlockService unlockService;

    private boolean resultBoolean;
    private List resultList;
    private int resultInt;

    /**
     * 获取(当期帐户、当前网关、当前门锁)已授权的开锁身份证信息
     *
     * @return resultList type:List<IdentityCard>
     */
    @RequestMapping("/getUnlockId.do")
    @ResponseBody
    public List getUnlockId(HttpServletRequest request){
        String[] strs={"",null,""};
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/getUnlockId.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        resultList=unlockService.getUnlockId(ownerPhoneNumber,gatewayCode,lockCode);
        return resultList;
    }

    /**
     * 添加身份证开锁授权
     *
     * @return
     */
    @RequestMapping("/authUnlockById.do")
    @ResponseBody
    public boolean authUnlockById(HttpServletRequest request) throws ParseException {
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/authUnlockById.do -->>--");
        }
        HttpSession session=request.getSession(false);
//        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        String name=request.getParameter("name");
        String cardNumb=request.getParameter("cardNumb");
        String dnCode=request.getParameter("dnCode");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        if(null==ownerPhoneNumber || null==gatewayCode || null==lockCode || null==name || null==cardNumb || null==startTime || null==endTime){
            return false;
        }
        long startTimeL=DateUtil.yyyy_MM_dd0HH$mm.parse(startTime).getTime();
        long endTimeL=DateUtil.yyyy_MM_dd0HH$mm.parse(endTime).getTime();
        resultBoolean=unlockService.authUnlockById(ownerPhoneNumber,gatewayCode,lockCode,name,cardNumb,dnCode,startTimeL,endTimeL);
        return resultBoolean;
    }

    /**
     * 删除开锁身份证
     * serviceNumb为授权时产生的操作序列号
     *
     * @return
     */
    @RequestMapping("/prohibitUnlockById.do")
    @ResponseBody
    public boolean prohibitUnlockById(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/prohibitUnlockById.do -->>--");
        }
        HttpSession session=request.getSession(false);
//        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
        String lockCode=request.getParameter("lockCode");
        String cardNumb=request.getParameter("cardNumb");
        String serviceNumb=request.getParameter("serviceNumb");
        resultBoolean=unlockService.prohibitUnlockById(ownerPhoneNumber,lockCode,cardNumb,serviceNumb);
        return resultBoolean;
    }

    /**
     * 获取(当期帐户、当前网关、当前门锁)已授权的开锁密码信息
     *
     * @return
     */
    @RequestMapping("/getUnlockPwd.do")
    @ResponseBody
    public UnlockPwds getUnlockPwd(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/getUnlockPwd.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        UnlockPwds unlockPwds=unlockService.getUnlockPwd(ownerPhoneNumber,gatewayCode,lockCode);
        return unlockPwds;
    }

    /**
     * 开锁密码授权
     *
     * @return
     */
    @RequestMapping("/authUnlockByPwd.do")
    @ResponseBody
    public boolean authUnlockByPwd(HttpServletRequest request) throws ParseException {
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/authUnlockByPwd.do -->>--");
        }
        HttpSession session=request.getSession(false);
//        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        String password=request.getParameter("password");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        if(null==ownerPhoneNumber || null==gatewayCode || null==lockCode || null==password || null==startTime || null==endTime){
            return false;
        }
        long startTimeL=DateUtil.yyyy_MM_dd0HH$mm.parse(startTime).getTime();
        long endTimeL=DateUtil.yyyy_MM_dd0HH$mm.parse(endTime).getTime();
        resultBoolean=unlockService.authUnlockByPwd(ownerPhoneNumber,gatewayCode,lockCode,password,startTimeL,endTimeL);
        return resultBoolean;
    }

    /**
     * 删除开锁密码
     * serviceNumb为授权时产生的操作序列号
     *
     * @return
     */
    @RequestMapping("/prohibitUnlockByPwd.do")
    @ResponseBody
    public boolean prohibitUnlockByPwd(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/prohibitUnlockByPwd.do -->>--");
        }
        HttpSession session=request.getSession(false);
//        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        String serviceNumb=request.getParameter("serviceNumb");
        resultBoolean=unlockService.prohibitUnlockByPwd(ownerPhoneNumber,gatewayCode,lockCode,serviceNumb);
        return resultBoolean;
    }

    /**
     * 获取(当期帐户、当前网关、当前门锁)开锁授权信息
     *
     * @return resultList type:List<IdentityCard>
     */
    @RequestMapping("/getUnlockAuthorization.do")
    @ResponseBody
    public UnlockAuthorization getUnlockAuthorization(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/getUnlockAuthorization.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        UnlockAuthorization unlockAuthorization=unlockService.getUnlockAuthorization(ownerPhoneNumber,gatewayCode,lockCode);
        return unlockAuthorization;
    }

    /**
     * 先获取(当期帐户、当前网关、当前门锁)开锁授权信息,再处理授权信息.
     *
     */
    @RequestMapping("/getUnlockAuthorizationDailyArr2.do")
    @ResponseBody
    public Authinfo getUnlockAuthorizationDailyArr2(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/getUnlockAuthorizationDailyArr2.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        UnlockAuthorization unlockAuthorization=unlockService.getUnlockAuthorization(ownerPhoneNumber,gatewayCode,lockCode);
        if(null==unlockAuthorization){
            return null;
        }
        long startTime=Long.parseLong(request.getParameter("startTime"));
        long endTime=Long.parseLong(request.getParameter("endTime"));
        try {
            LOG.info("startTime:"+ DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(startTime)));
            LOG.info("endTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(endTime)));
            Authinfo authinfo=unlockService.getUnlockAuthorizationDailyArr2(unlockAuthorization,startTime,endTime);
            return authinfo;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    @RequestMapping("/getUnlockAuthorizationDailyArr.do")
    @ResponseBody
    public Authinfo getUnlockAuthorizationDailyArr(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/getUnlockAuthorizationDailyArr.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        UnlockAuthorization unlockAuthorization=unlockService.getUnlockAuthorization(ownerPhoneNumber,gatewayCode,lockCode);
        if(null==unlockAuthorization){
            return null;
        }
        String theDateStr=request.getParameter("theDate");
        Date theDate= null;
        try {
            theDate = DateUtil.yyyy_MM_dd.parse(theDateStr);
            Authinfo authinfo=unlockService.getUnlockAuthorizationDailyArr(unlockAuthorization,theDate);
            return authinfo;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    */
    @RequestMapping("/getUnlockAuthorizationDailyArr.do")
    @ResponseBody
    public JsonDto getUnlockAuthorizationDailyArr(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/getUnlockAuthorizationDailyArr.do -->>--");
        }
        HttpSession session=request.getSession(false);
//        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        JsonDto jsonDto=null;
        BizDto bizDto=null;
        UnlockAuthorization unlockAuthorization=unlockService.getUnlockAuthorization(ownerPhoneNumber,gatewayCode,lockCode);
        if(null==unlockAuthorization){
//            return null;
            bizDto= BizDto.EMPTY_RESULT;
            jsonDto=new JsonDto(bizDto);
            return jsonDto;
        }
        String theDateStr=request.getParameter("theDate");
        Date theDate= null;
        try {
            theDate = DateUtil.yyyy_MM_dd.parse(theDateStr);
            Authinfo authinfo=unlockService.getUnlockAuthorizationDailyArr(unlockAuthorization,theDate);
            if(null==authinfo){
                bizDto= BizDto.EMPTY_RESULT;
            }else {
                bizDto=new BizDto<Authinfo>(authinfo);
            }
            jsonDto=new JsonDto(bizDto);
        } catch (ParseException e) {
//            e.printStackTrace();
            LOG.error(null,e);
            jsonDto= JsonDto.EXCEPTION;
        }finally {
            return jsonDto;
        }
    }

    @RequestMapping("/getDailyUnlockAuthorization.do")
    @ResponseBody
    public JsonDto getDailyUnlockAuthorization(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/getDailyUnlockAuthorization.do -->>--");
        }
        HttpSession session=request.getSession(false);
//        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String ownerPhoneNumber=request.getParameter("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        JsonDto jsonDto=null;
        BizDto bizDto=null;
        UnlockAuthorization unlockAuthorization=unlockService.getUnlockAuthorization(ownerPhoneNumber,gatewayCode,lockCode);
        if(null==unlockAuthorization){
//            return null;
            bizDto= BizDto.EMPTY_RESULT;
            jsonDto=new JsonDto(bizDto);
            return jsonDto;
        }
        String theDateStr=request.getParameter("theDate");
        Date theDate= null;
        try {
            theDate = DateUtil.yyyy_MM_dd.parse(theDateStr);
            Authinfo authinfo=unlockService.getDailyUnlockAuthorization(unlockAuthorization,theDate);
            if(null==authinfo){
                bizDto= BizDto.EMPTY_RESULT;
            }else {
                bizDto=new BizDto<Authinfo>(authinfo);
            }
            jsonDto=new JsonDto(bizDto);
        } catch (ParseException e) {
//            e.printStackTrace();
            LOG.error(null,e);
            jsonDto= JsonDto.EXCEPTION;
        }finally {
            return jsonDto;
        }
        ////////////
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- record/getDailyUnlockRecordLockPage.do -->>--");
        }
        JsonDto jsonDto=null;
        BizDto bizDto=null;
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
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
}
