package com.yishu.controller;

import com.yishu.pojo.Authinfo;
import com.yishu.pojo.UnlockAuthorization;
import com.yishu.pojo.UnlockPwds;
import com.yishu.service.IUnlockService;
import com.yishu.util.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    public boolean authUnlockById(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/authUnlockById.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        String name=request.getParameter("name");
        String cardNumb=request.getParameter("cardNumb");
        String dnCode=request.getParameter("dnCode");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        resultBoolean=unlockService.authUnlockById(ownerPhoneNumber,gatewayCode,lockCode,name,cardNumb,dnCode,startTime,endTime);
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
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
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
    public boolean authUnlockByPwd(HttpServletRequest request){
        if (LOG.isInfoEnabled()){
            LOG.info("-->>-- unlock/authUnlockByPwd.do -->>--");
        }
        HttpSession session=request.getSession(false);
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
        String gatewayCode=request.getParameter("gatewayCode");
        String lockCode=request.getParameter("lockCode");
        String password=request.getParameter("password");
        String startTime=request.getParameter("startTime");
        String endTime=request.getParameter("endTime");
        resultBoolean=unlockService.authUnlockByPwd(ownerPhoneNumber,gatewayCode,lockCode,password,startTime,endTime);
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
        String ownerPhoneNumber= (String) session.getAttribute("ownerPhoneNumber");
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
        long startTime=Long.parseLong(request.getParameter("startTime"));
        long endTime=Long.parseLong(request.getParameter("endTime"));
        try {
            LOG.info("startTime:"+ DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(startTime)));
            LOG.info("endTime:"+DateUtil.yyyy_MM_dd0HH$mm$ss.format(new Date(endTime)));
            Authinfo authinfo=unlockService.getUnlockAuthorizationDailyArr(unlockAuthorization,startTime,endTime);
            return authinfo;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
