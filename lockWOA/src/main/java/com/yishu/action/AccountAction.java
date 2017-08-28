package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yishu.jwt.*;
import com.yishu.service.IUserService;
import com.yishu.util.JwtHelper;
import com.yishu.util.MD5;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.opensymphony.xwork2.config.entities.Parameterizable;

import javax.security.auth.Subject;
import java.util.Map;

public class AccountAction extends ActionSupport implements Parameterizable,SessionAware {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    @Autowired
    private Audience audienceEntity;

    private LoginPara loginPara;

    public LoginPara getLoginPara() {
        return loginPara;
    }

    public void setLoginPara(LoginPara loginPara) {
        this.loginPara = loginPara;
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
    public Object getAccessToken(LoginPara loginPara)
    {
        ResultMsg resultMsg;
        try{
            if(null == loginPara.getClientId() || 0 != (loginPara.getClientId().compareTo(audienceEntity.getClientId()) )){
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_CLIENTID.getErrcode(), ResultStatusCode.INVALID_CLIENTID.getErrmsg(), null);
                return resultMsg;
            }System.out.println("!clientid verified");

            //验证用户名密码
            User user = userService.findUserByName(loginPara.getUserName());
            System.out.println("user = "+user+": {"+"'name'"+" : "+user.getName()+","+"'password'"+" : "+user.getPassword()+"}");
            if (user == null){
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(),
                        ResultStatusCode.INVALID_PASSWORD.getErrmsg(), null);
                return resultMsg;
            }
            else{
                String md5Password = MD5.getMD5(loginPara.getPassword()+user.getSalt());
                System.out.println("md5Password : "+md5Password);
                System.out.println("md5Password : "+user.getPassword());
                if (md5Password.compareTo(user.getPassword()) != 0){
                    resultMsg = new ResultMsg(ResultStatusCode.INVALID_PASSWORD.getErrcode(), ResultStatusCode.INVALID_PASSWORD.getErrmsg(), null);
                    return resultMsg;
                }
            }System.out.println("!start to create JwtAccessToken");

            //拼装accessToken
            String accessToken = JwtHelper.createJWT(loginPara.getUserName(), String.valueOf(user.getId()),
                    user.getRole(), audienceEntity.getClientId(), audienceEntity.getName(),
                    audienceEntity.getExpiresSecond() * 1000, audienceEntity.getBase64Secret());

            //返回accessToken
            JwtAccessToken accessTokenEntity = new JwtAccessToken();
            accessTokenEntity.setAccess_token(accessToken);
            accessTokenEntity.setExpires_in(audienceEntity.getExpiresSecond());
            accessTokenEntity.setToken_type("bearer");
            resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(), accessTokenEntity);
            return resultMsg;
        }
        catch(Exception ex){
            System.out.println("!exception");
            resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getErrcode(), ResultStatusCode.SYSTEM_ERR.getErrmsg(), null);
            return resultMsg;
        }
    }

    //********************************************************************************************************
    private User user;

    public String login(){
        String username = user.getUsername();
        String password = user.getPassword();
        logger.debug("username => " + username);
        logger.debug("password => " + password);
        loginService.login(user);
        jwtAccessToken=new JwtAccessToken();
        jwtAccessToken.getAccess_token()
        return "login";
    }
    public String login(User user, Model model){
        String username = user.getUsername();
        String password = user.getPassword();
        logger.debug("username => " + username);
        logger.debug("password => " + password);
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        String msg = null;
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            msg = e.getMessage();
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            msg = "用户名和密码的组合不正确";
        } catch (LockedAccountException e){
            e.printStackTrace();
            msg = e.getMessage();
        }
        if(msg == null){
//            return "redirect:/admin/user/list";
            return "main";
        }

        model.addAttribute("msg",msg);
        return "login";
    }

    private JwtAccessToken jwtAccessToken;
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

    //    Map<String, String> paramsMap;
    @Override
    public void addParam(String s, String s1) {

    }
    @Override
    public void setParams(Map<String, String> map) {
//        paramsMap=map;
    }
    @Override
    public Map<String, String> getParams() {
        return null;
    }

    private Map sessionMap;
    @Override
    public void setSession(Map<String, Object> map) {
        this.sessionMap=map;
    }
}