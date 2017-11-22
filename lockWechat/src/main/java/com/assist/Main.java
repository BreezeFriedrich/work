/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.assist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("main")
public class Main {

    @Value("${APPID}")
    public String APPID;
    @Value("${APPSECRET}")
    public String APPSECRET;

    public void printConfig() {
        //@Value("${}")搭配component-scan 、@Component读取配置.
        System.out.println("APPSECRET : " + APPSECRET);
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

//        ApplicationContext context=new ClassPathXmlApplicationContext("spring/spring.xml");
////        System.out.println("APPSECRET : "+((WechatWebAccessTokenUtil)(context.getBean("getOpenidByCode"))).SECRET);
//        ((WechatWebAccessTokenUtil)(context.getBean("getOpenid"))).printConfig();

        String urlStr = "http://192.168.1.47:9018";
        System.err.println(urlStr);
    }
}