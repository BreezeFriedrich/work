package com.yishu.util;

import static com.yishu.util.GetNetworkTime.getWebsiteDatetime;
import static org.junit.Assert.*;

import com.yishu.util.GetNetworkTime;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

/** 
* GetNetworkTime Tester. 
* 
* @author <Authors name> 
* @since <pre>十月 16, 2017</pre> 
* @version 1.0 
*/ 
public class GetNetworkTimeTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 


/** 
* 
* Method: getWebsiteDatetime(String webUrl) 
* 
*/ 
@Test
public void testGetWebsiteDatetime() throws Exception { 
//TODO: Test goes here... 
/*
try { 
   Method method = GetNetworkTime.getClass().getMethod("getWebsiteDatetime", String.class);
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) {
} 
*/

    String webUrl1 = "http://www.bjtime.cn";//bjTime
    String webUrl2 = "http://www.baidu.com";//百度
    String webUrl3 = "http://www.taobao.com";//淘宝
    String webUrl4 = "http://www.ntsc.ac.cn";//中国科学院国家授时中心
    String webUrl5 = "http://www.360.cn";//360
    String webUrl6 = "http://www.beijing-time.org";//beijing-time

    System.out.println(getWebsiteDatetime(webUrl1) + " [bjtime]");
    System.out.println(getWebsiteDatetime(webUrl2) + " [百度]");
    System.out.println(getWebsiteDatetime(webUrl3) + " [淘宝]");
    System.out.println(getWebsiteDatetime(webUrl4) + " [中国科学院国家授时中心]");
    System.out.println(getWebsiteDatetime(webUrl5) + " [360安全卫士]");
    System.out.println(getWebsiteDatetime(webUrl6) + " [beijing-time]");

} 

} 
