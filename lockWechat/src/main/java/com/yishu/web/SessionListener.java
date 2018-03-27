/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.web;

import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

/**
 * @author <a href="http://blog.163.com/go_lins/blog/static/1686863262010742387982/">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-27 10:10 admin
 * @since JDK1.7
 */
public class SessionListener implements HttpSessionListener {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger("SessionListener");

    public static HashMap sessionMap = new HashMap();
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        // 初始化当前session
        sessionMap.put(session.getId(), session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        // 判断当前session user是否有值
        if (session.getAttribute("user") != null   && session.getAttribute("user").toString().length() > 0) {
            // session销毁清空map 更新map
            sessionMap.remove(session.getAttribute("user").toString());
            session.invalidate();
        }
    }
}