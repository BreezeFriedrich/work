package com.yishu.web;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.*;

public class SessionListener implements HttpSessionListener , HttpSessionAttributeListener {

    // 保存当前登录的所有用户
    public static Map<HttpSession, Long> loginUser = new HashMap<HttpSession, Long>();

    // 用这个作为session中的key
    public static String SESSION_LOGIN_NAME = "user_id_key";

    //session创建时调用这个方法
    public void sessionCreated(HttpSessionEvent arg0) {

    }

    //Session失效或者过期的时候调用的这个方法,
    public void sessionDestroyed(HttpSessionEvent se) {
        // 如果session超时, 则从map中移除这个用户
        try {
            loginUser.remove(se.getSession());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //执行setAttribute的时候, 当这个属性本来不存在于Session中时, 调用这个方法.
    public void attributeAdded(HttpSessionBindingEvent se) {
        // 如果添加的属性是用户名, 则加入map中
        if (se.getName().equals(SESSION_LOGIN_NAME)) {
            loginUser.put(se.getSession(), Long.valueOf(se.getValue().toString()));
        }
    }

    //当执行removeAttribute时调用的方法
    public void attributeRemoved(HttpSessionBindingEvent se) {
        // 如果移除的属性是用户名, 则从map中移除
        if (se.getName().equals(SESSION_LOGIN_NAME)) {
            try {
                loginUser.remove(se.getSession());
            } catch (Exception e) {
            }
        }
    }

    //当执行setAttribute时 ,如果这个属性已经存在, 覆盖属性的时候, 调用这个方法
    public void attributeReplaced(HttpSessionBindingEvent se) {
        // 如果改变的属性是用户名, 则跟着改变map
        if (se.getName().equals(SESSION_LOGIN_NAME)) {
            loginUser.put(se.getSession(), Long.valueOf(se.getValue().toString()));
        }
    }

//别忘了到你的web.xml中去配置一下listener

//第二步

//写一个判断用户是否已经登陆的方法

    public boolean isLogonUser(Long userId) {
        Set<HttpSession> keys = SessionListener.loginUser.keySet();
        for (HttpSession key : keys) {
            if (SessionListener.loginUser.get(key).equals(userId)) {
                return true;
            }
        }
        return false;
    }
}



//第三步

//在用户登陆的action.method，或者是loginServlet.doGet/doPost中

//判断用户名、密码都OK后，再调用第二步的方法，参数为用户ID；true则表示该用户已经登陆


//写完此代码，就知道如何实现 一个用户登陆 踢掉之前登陆的用户了