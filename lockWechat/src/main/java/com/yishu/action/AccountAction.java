package com.yishu.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.entities.Parameterizable;
import com.yishu.jwt.*;
import com.yishu.service.IUserService;
import com.yishu.util.JwtHelper;
import com.yishu.util.MD5;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * @bottom Copyright &#169; {inceptionYear}&#x2013;{currentYear} {organizationName}. All rights reserved.
 */
public class AccountAction extends ActionSupport implements Parameterizable,SessionAware {

//    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;
    @Autowired
    private Audience audience;

    private LoginPara loginPara;
    public LoginPara getLoginPara() {
        return loginPara;
    }
    public void setLoginPara(LoginPara loginPara) {
        this.loginPara = loginPara;
    }


    public String login (LoginPara loginPara) {
        ResultMsg resultMsg=getJwtAccessToken(loginPara);
        if (0==resultMsg.getErrcode()) {
            session.put("jwtAccessToken",resultMsg.getJwtAccessToken());
            session.remove("authenticateErrMsg");
            return Action.SUCCESS;
        } else {
            session.put("authenticateErrMsg",resultMsg.getErrmsg());
            session.remove("jwtAccessToken");
        }
        return Action.LOGIN;
    }
    /**
     * 验证account(username+password),若通过验证则授权JwtAccessToken
     * 流程:1.比对request body 中的 LoginPara.clientId & 配置 jwt.properties 中的 audience.clientId;
     *     2.根据request body 中 LoginPara.userName,数据库中取出User帐户性息(其中User.password为经过加盐MD5处理后的值);
     *     3.将request body 中 LoginPara.password 做加盐MD5处理: MD5.getMD5(loginPara.getPassword()+user.getSalt());
     *     4.比对 2 和 3 中的password值,若成功则执行 5 ;
     *     5.根据 LoginPara 产生 jwt 认证所需的JwtAccessToken ;
     * @param loginPara
     * @return
     */
    public ResultMsg getJwtAccessToken (LoginPara loginPara) {
        ResultMsg resultMsg;
        try{
            /*
            //验证clientId
            if(null == loginPara.getClientId() || 0 != (loginPara.getClientId().compareTo(audience.getClientId()) )){
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_CLIENTID.getErrcode(), ResultStatusCode.INVALID_CLIENTID.getErrmsg(), null);
                return resultMsg;
            }
            */

            //验证用户名密码
            User user = userService.findUserByName(loginPara.getUsername());
            System.out.println("user = "+user+": {"+"'name'"+" : "+user.getName()+","+"'password'"+" : "+user.getPassword()+"}");
            if (user == null){
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(),
                        ResultStatusCode.INVALID_PASSWORD.getErrmsg(), null);
                return resultMsg;
            } else {
                String md5Password = MD5.getMD5(loginPara.getPassword()+user.getSalt());
                System.out.println("md5Password : "+md5Password);
                System.out.println("md5Password : "+user.getPassword());
                if (md5Password.compareTo(user.getPassword()) != 0){
                    resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(), ResultStatusCode.INVALID_PASSWORD.getErrmsg(), null);
                    return resultMsg;
                }
            }
            System.out.println("!start to create JwtAccessToken");

            //拼装accessToken
            String accessToken = JwtHelper.createJWT(loginPara.getUsername(), String.valueOf(user.getId()),
                    user.getRole(), audience.getClientId(), audience.getName(),
                    audience.getExpiresSecond() * 1000, audience.getBase64Secret());

            //返回accessToken
            JwtAccessToken jwtAccessToken = new JwtAccessToken();
            jwtAccessToken.setAccess_token(accessToken);
            jwtAccessToken.setExpiration(new Date().getTime()+audience.getExpiresSecond());
            jwtAccessToken.setToken_type("jwt");
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(), jwtAccessToken);
            return resultMsg;
        } catch(Exception ex) {
            System.out.println("!exception");
            resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrcode(), ResultStatusCode.SYSTEM_ERR.getErrmsg(), null);
            return resultMsg;
        }
    }

    //********************************************************************************************************
    private JwtAccessToken jwtAccessToken;
    /*jwtAccessToken设置了getXXX，json插件将jwtAccessToken转为json*/
    public JwtAccessToken getJwtAccessToken() {
        return jwtAccessToken;
    }
    public void setJwtAccessToken(JwtAccessToken jwtAccessToken) {
        this.jwtAccessToken = jwtAccessToken;
    }

    private String salt;
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

    Map<String, String> params;
    @Override
    public void addParam(String s, String s1) {

    }
    @Override
    public void setParams(Map<String, String> map) {
        this.params =map;
    }
    @Override
    public Map<String, String> getParams() {
        return this.params;
    }

    private Map session;
    @Override
    public void setSession(Map<String, Object> map) {
        this.session =map;
    }
}