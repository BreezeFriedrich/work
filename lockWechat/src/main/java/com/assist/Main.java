/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.assist;

import com.yishu.util.WechatWebAccessTokenUtil;
import com.yishu.web.OpenIdInterceptor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("main")
public class Main {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Main.class);

    @Value("${APPID}")
    public String APPID;

    public static String APPSECRET;
    @Value("${assignURL}")
    public String assignURL;

    public static String ownerURL;
    @Value("${gatewayURL}")
    public String gatewayURL;

    @Value("${APPSECRET}")
    public void setAPPSECRET(String APPSECRET) {
        Main.APPSECRET = APPSECRET;
    }
    /*
    //注入static成员的错误示例
    @Value("${ownerURL:192.168.0.1}")
    public static void setOwnerURL(String ownerURL) {//1.方法也加了 static修饰.
        Main.ownerURL = ownerURL;
    }
    @Value("${ownerURL:192.168.0.1}")
    public void setOwnerURL(String ownerURL) {
        ownerURL = ownerURL;//
    }
    */

    public void printConfig() {
        //@Value("${}")搭配component-scan 、@Component读取配置.
        LOG.info("APPID:"+APPID);
        LOG.info("APPSECRET:" + APPSECRET);
        LOG.info("assignURL:"+assignURL);
        LOG.info("ownerURL:"+ownerURL);
        LOG.info("gatewayURL:"+gatewayURL);
    }

    public static void main(String[] args) throws Exception {
        /*locale
        Locale defaultLocale= Locale.getDefault();
        System.out.println("locale : "+defaultLocale);

        Locale [] locales = Locale.getAvailableLocales();
        for(Locale locale:locales){
            //输出所有支持的国家
            System.out.print(locale.getDisplayCountry()+":"+locale.getCountry());
            //输出所有支出的语言
            System.out.println(locale.getDisplayLanguage()+":"+locale.getLanguage());
        }
        */

        ApplicationContext context=new ClassPathXmlApplicationContext("spring/spring.xml");
        ((WechatWebAccessTokenUtil)(context.getBean("wechatWebAccessTokenUtil"))).printConfig();
        ((Main)(context.getBean("main"))).printConfig();
//        ((OpenIdInterceptor)(context.getBean("openIdInterceptor"))).printConfig();

//        String urlStr = "http://192.168.1.47:9018";
//        LOG.info(urlStr);
        ((WechatUtil)(context.getBean("wechatUtil"))).printConfig();
    }
}