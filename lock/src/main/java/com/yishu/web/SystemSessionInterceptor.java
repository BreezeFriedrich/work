package com.yishu.web;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-12-22 11:35 admin
 * @since JDK1.7
 */
public class SystemSessionInterceptor implements HandlerInterceptor {
    private static final String LOGIN_URL="/jsp/sessionexpire.jsp";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession(true);
        //session中获取用户名信息
        Object obj = session.getAttribute("ownerPhoneNumber");
        if (obj==null||"".equals(obj.toString())) {
            response.sendRedirect(request.getSession().getServletContext().getContextPath()+LOGIN_URL);
            /*
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();
            StringBuilder builder = new StringBuilder();
            builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
            builder.append("alert(\"登录已过期，请重新登录！\");");
            builder.append("parent.window.location.href='"+request.getContextPath()+"/f/login';");
            builder.append("</script>");
            out.print(builder.toString());
            out.close();
            return false;
             */
//            return false;
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
