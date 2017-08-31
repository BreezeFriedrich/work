package com.yishu.web;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.yishu.jwt.Audience;
import com.yishu.jwt.JwtAccessToken;
import com.yishu.jwt.ResultStatusCode;
import com.yishu.util.JwtHelper;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public class AuthenticationInterceptor implements Interceptor{
    @Autowired
    Audience audience;

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        ActionContext actionContext=actionInvocation.getInvocationContext();
        Map sessionMap = actionContext.getSession();
        HttpServletRequest request =(HttpServletRequest)actionContext.get(ServletActionContext.HTTP_REQUEST);

        //ActionContext ctx = ActionContext.getContext();
        //Map<String,Object> sessionMap = ActionContext.getContext().getSession();
        //Map sessionMap = (Map)ActionContext.getContext().get(ActionContext.SESSION);
        //HttpSession session = ServletActionContext.getRequest().getSession();

//        JwtAccessToken jwtAccessToken = (JwtAccessToken) sessionMap.get(Struts2PortfolioConstants.getJwtAccessToken());
        JwtAccessToken jwtAccessToken = null;
        if (sessionMap.containsKey("jwtAccessToken")){
            jwtAccessToken = (JwtAccessToken) sessionMap.get("jwtAccessToken");
        }
        if( null!=jwtAccessToken && jwtAccessToken.getExpiration()>=new Date().getTime() ) {
//            Action action = (Action) actionInvocation.getAction();
//            if(action instanceof UserAware){
//                ((UserAware)action).setUser(user);
//            }

            if (isAuthenticated(request,jwtAccessToken)) {
                sessionMap.remove("authenticateErrMsg");
                return actionInvocation.invoke();
            }else{
                sessionMap.put("authenticateErrMsg", ResultStatusCode.INVALID_TOKEN.getErrmsg());
                sessionMap.remove("jwtAccessToken");
            }
        }
        return Action.LOGIN;
    }

    public boolean isAuthenticated(HttpServletRequest request,JwtAccessToken token) {
        String reqHeaderAuth=request.getHeader("Authorization");
        if (null != reqHeaderAuth && reqHeaderAuth.length() > 4) {
            String tokenType = reqHeaderAuth.substring(0,3).toLowerCase();
            if (0 == tokenType.compareTo("jwt")) {
                String tokenStr = reqHeaderAuth.substring(4,reqHeaderAuth.length());
                if ( null != JwtHelper.parseJWT(token.getAccess_token(),audience.getBase64Secret()) ) {
                    return true;
                }
            }
        }

        return false;
    }

}
