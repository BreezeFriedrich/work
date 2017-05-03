package com.yishu.controller;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by admin on 2017/5/3.
 */
public class DispatcherServlet extends HttpServlet{

    private static WebApplicationContext wc;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //init spring factory
         wc = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
    }

    public static WebApplicationContext getWc() {
            return wc;
        }

    public static Object getBean(String beanName){
            return wc.getBean(beanName);
        }
}
