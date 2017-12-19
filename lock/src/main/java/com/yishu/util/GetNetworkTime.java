/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-16 18:00 admin
 * @since JDK1.7
 */
public class GetNetworkTime {

    private static String webUrl1 = "http://www.bjtime.cn";//bjTime
    private static String webUrl2 = "http://www.baidu.com";//百度
    private static String webUrl3 = "http://www.taobao.com";//淘宝
    private static String webUrl4 = "http://www.ntsc.ac.cn";//中国科学院国家授时中心
    private static String webUrl5 = "http://www.360.cn";//360
    private static String webUrl6 = "http://www.beijing-time.org";//beijing-time

    /**
     * 获取网络时间
     * 与时间权威网站建立http连接，返回连接的时间
     *
     * @param webUrl
     * @return
     */
    public static String getWebsiteDatetime(String webUrl){
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            Date date = new Date(ld);// 转换为标准时间对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
            return sdf.format(date);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWebsiteDatetimeFromAcademy(){
        return getWebsiteDatetime(webUrl4);
    }
}
