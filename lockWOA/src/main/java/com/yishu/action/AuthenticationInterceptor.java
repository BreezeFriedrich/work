package com.yishu.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.yishu.jwt.JwtAccessToken;
import com.yishu.pojo.Struts2PortfolioConstants;
import org.apache.struts2.ServletActionContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class AuthenticationInterceptor implements Interceptor{
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

        JwtAccessToken jwtAccessToken = (JwtAccessToken) sessionMap.get(Struts2PortfolioConstants.getJwtAccessToken());
        if(null!=jwtAccessToken) {
//            Action action = (Action) actionInvocation.getAction();
//            if(action instanceof UserAware){
//                ((UserAware)action).setUser(user);
//            }
            return actionInvocation.invoke();
        }
        return Action.LOGIN;
    }

    public isJwtAccessTokenExpired(JwtAccessToken jwtAccessToken){
        if (new Date().getTime()<jwtAccessToken.getExpires_in()){

        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        ResultMsg resultMsg;
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String auth = httpRequest.getHeader("Authorization");
        if ((auth != null) && (auth.length() > 7))
        {
            String HeadStr = auth.substring(0, 6).toLowerCase();
            if (HeadStr.compareTo("bearer") == 0)
            {
                auth = auth.substring(7, auth.length());
                if (JwtHelper.parseJWT(auth, audienceEntity.getBase64Secret()) != null)
                {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        resultMsg = new ResultMsg(ResultStatusCode.INVALID_TOKEN.getErrcode(), ResultStatusCode.INVALID_TOKEN.getErrmsg(), null);
        ObjectMapper mapper = new ObjectMapper();
        httpResponse.getWriter().write(mapper.writeValueAsString(resultMsg));
        return;
    }

}
