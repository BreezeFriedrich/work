/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.pojo;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-08 14:19 admin
 * @since JDK1.7
 */
public class IdentityCard {
    private String serviceNumb;
    private String name;
    private String cardNumb;
    private String dnCode;
    private String startTime;
    private String endTime;

    public String getServiceNumb() {
        return serviceNumb;
    }

    public void setServiceNumb(String serviceNumb) {
        this.serviceNumb = serviceNumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumb() {
        return cardNumb;
    }

    public void setCardNumb(String cardNumb) {
        this.cardNumb = cardNumb;
    }

    public String getDnCode() {
        return dnCode;
    }

    public void setDnCode(String dnCode) {
        this.dnCode = dnCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
