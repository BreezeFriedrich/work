package com.yishu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 2017/5/3.
 */
public class DispatcherServlet extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        Logger logger= LoggerFactory.getLogger(DispatcherServlet.class);
//        logger.info("getServletPath():"+request.getServletPath());
//        logger.info("getContextPath():"+request.getContextPath());
//        logger.info("getRequestURI():"+request.getRequestURI());
//        logger.info("getRequestURL():"+request.getRequestURL().toString());
//        logger.info("getQueryString():"+request.getQueryString());
        String str=request.getRequestURI();
        String str2=(str.split("/"))[2];
        String str3=request.getContextPath()+"/"+"WEB-INF"+"/"+"jsp"+"/"+str2;
        logger.info("request.getRequestDispatcher(str3).forward-->>"+str3);
        try {
            request.getRequestDispatcher(str3).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
