package com.yishu.util;

import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**结合servlet-api中HttpServletRequest和HttpServletResponse,处理cookie.
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-12-21 18:12 admin
 * @since JDK1.7
 */
public class CookieUtil {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CookieUtil.class.getName());

    //读取cookie数组，之后迭代出各个cookie
    public static void showCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();//根据请求数据，找到cookie数组

        if (null==cookies) {//如果没有cookie数组
            System.out.println("没有cookie");
        } else {
            for(Cookie cookie : cookies){
                System.out.println("cookieName:"+cookie.getName()+",cookieValue:"+ cookie.getValue());
            }
        }
    }

    /**
     * 创建cookie，并将新cookie添加到“响应对象”response中。
     *
     * @param response
     * @param cookie_name
     * @param cookie_value
     * @param timeToLiveInSec cookie的存活时间(second).
     */
    public static void addCookie(HttpServletResponse response,String cookie_name,String cookie_value,int timeToLiveInSec){
        Cookie cookie = new Cookie(cookie_name,cookie_value);//创建新cookie
        cookie.setMaxAge(timeToLiveInSec);
        cookie.setPath("/");//设置作用域
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
    }

    //修改cookie，可以根据某个cookie的name修改它（不只是name要与被修改cookie一致，path、domain必须也要与被修改cookie一致）
    public static void editCookie(HttpServletRequest request,HttpServletResponse response,String cookie_name,String cookie_value,int timeToLiveInSec){
        Cookie[] cookies = request.getCookies();
        if (null==cookies) {
            System.out.println("没有cookies");
        } else {
            for(Cookie cookie : cookies){
                //迭代时如果发现与指定cookieName相同的cookie，就修改相关数据
                if(cookie.getName().equals(cookie_name)){
                    cookie.setValue(cookie_value);//修改value
                    cookie.setPath("/");
                    cookie.setMaxAge(timeToLiveInSec);// 修改存活时间
                    response.addCookie(cookie);//将修改过的cookie存入response，替换掉旧的同名cookie
                    break;
                }
            }
        }
    }

    //删除某个cookie
    public static void delCookie(HttpServletRequest request,HttpServletResponse response,String cookie_name){
        Cookie[] cookies = request.getCookies();
        if (null==cookies) {
            System.out.println("没有cookie");
        } else {
            for(Cookie cookie : cookies){
                //如果找到同名cookie，就将value设置为null，将存活时间设置为0，再替换掉原cookie，这样就相当于删除了。
                if(cookie.getName().equals(cookie_name)){
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    //删除所有cookies即Cookie[]
    public static void delAllCookie(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (null==cookies) {
            System.out.println("没有cookie");
        } else {
            for(Cookie cookie : cookies){
                cookie.setValue(null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                break;
            }
        }
    }
}
