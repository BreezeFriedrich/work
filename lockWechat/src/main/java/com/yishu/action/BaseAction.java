package com.yishu.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @bottom <![CDATA[Copyright 2005, <a href="http://www.mycompany.com">MyCompany, Inc.<a>]]>
 */
public class BaseAction extends ActionSupport {

    HttpServletResponse response = ServletActionContext.getResponse();
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session= request.getSession();

    public void sentJson(String ja) throws IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setHeader("Cache-Control","no-cache");
        response.getWriter().write(ja);
    }

    public void sentXML(String xa) throws IOException{
        response.setContentType("text/xml;charset=utf-8");
        response.setHeader("Cache-Control","no-cache");
        response.getWriter().write(xa);
    }

//    public String getSessionUser(){
//        String openid=(String)session.getAttribute(IWXService.SESSION_USER);
//        if(openid==null){
//            openid="123";
//        }
//        return openid;
//    }
}