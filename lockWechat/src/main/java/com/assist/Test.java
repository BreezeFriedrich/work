/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.assist;

import org.slf4j.Logger;

import java.util.Date;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-19 9:57 admin
 * @since JDK1.7
 */
public class Test {
    private static final Logger logger= org.slf4j.LoggerFactory.getLogger("Test");
    public static void main(String[] args){
//        System.out.println("HI");
        new Test().testPrint(10);
    }

    /**
     * 测试打印和日志耗时.
     * @param num
     */
    public void testPrint(int num){
        long time1=new Date().getTime();
        for (int i=0;i<num;i++){
            System.out.println(".");
        }
        long time2=new Date().getTime();
        for (int i=0;i<num;i++){
            System.out.println("从SSL 协议所提供的服务及其工作流程可以看出，SSL协议运行的基础是商家对消费者信息保密的承诺，这就有利于商家而不利于消费者。在电子商务初级阶段，由于运作电子商务的企业大多是信誉较高的大公司，因此这问题还没有充分暴露出来。但随着电子商务的发展，各中小型公司也参与进来，这样在电子支付过程中的单一认证问题就越来越突出。虽然在SSL3.0中通过数字签名和数字证书可实现浏览器和Web服务器双方的身份验证，但是SSL协议仍存在一些问题，比如，只能提供交易中客户与服务器间的双方认证，在涉及多方的电子交易中，SSL协议并不能协调各方间的安全传输和信任关系。在这种情况下，Visa和 MasterCard两大信用卡公组织制定了SET协议，为网上信用卡支付提供了全球性的标准。");
        }
        long time3=new Date().getTime();
        for (int i=0;i<num;i++){
            org.slf4j.LoggerFactory.getLogger(String.valueOf(num)).info(".");
        }
        long time4=new Date().getTime();
        for (int i=0;i<num;i++){
            org.slf4j.LoggerFactory.getLogger("long "+String.valueOf(num)).info("从SSL 协议所提供的服务及其工作流程可以看出，SSL协议运行的基础是商家对消费者信息保密的承诺，这就有利于商家而不利于消费者。在电子商务初级阶段，由于运作电子商务的企业大多是信誉较高的大公司，因此这问题还没有充分暴露出来。但随着电子商务的发展，各中小型公司也参与进来，这样在电子支付过程中的单一认证问题就越来越突出。虽然在SSL3.0中通过数字签名和数字证书可实现浏览器和Web服务器双方的身份验证，但是SSL协议仍存在一些问题，比如，只能提供交易中客户与服务器间的双方认证，在涉及多方的电子交易中，SSL协议并不能协调各方间的安全传输和信任关系。在这种情况下，Visa和 MasterCard两大信用卡公组织制定了SET协议，为网上信用卡支付提供了全球性的标准。");
        }
        long time5=new Date().getTime();
        System.err.println("System.out('.      ') 用时: "+(time2-time1)*1.000/num);
        System.err.println("System.out('longStr') 用时: "+(time3-time2)*1.000/num);
        System.err.println("logger.info('.      ') 用时: "+(time4-time3)*1.000/num);
        System.err.println("logger.info('longStr') 用时: "+(time5-time4)*1.000/num);
    }
}
