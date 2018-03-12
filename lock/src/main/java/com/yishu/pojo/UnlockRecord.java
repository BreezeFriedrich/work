/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.pojo;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-14 16:17 admin
 * @since JDK1.7
 */
public class UnlockRecord {
    //确保存在默认无参构造,用于JACKSON解析.

    private String gatewayCode;
    private String lockCode;
    private String gatewayName;
    private String lockName;
    private int openMode;//1身份证开锁,2密码开锁.
    private String timetag;

    private CardAuthInfo cardInfo;
    private PwdAuthInfo passwordInfo;

    public String getGatewayCode() {
        return gatewayCode;
    }

    public void setGatewayCode(String gatewayCode) {
        this.gatewayCode = gatewayCode;
    }

    public String getLockCode() {
        return lockCode;
    }

    public void setLockCode(String lockCode) {
        this.lockCode = lockCode;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public int getOpenMode() {
        return openMode;
    }

    public void setOpenMode(int openMode) {
        this.openMode = openMode;
    }

    public String getTimetag() {
        return timetag;
    }

    public void setTimetag(String timetag) {
        this.timetag = timetag;
    }

    public CardAuthInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardAuthInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public PwdAuthInfo getPasswordInfo() {
        return passwordInfo;
    }

    public void setPasswordInfo(PwdAuthInfo passwordInfo) {
        this.passwordInfo = passwordInfo;
    }

    @Override
    public String toString() {
        return "UnlockRecord [" +
                "gatewayCode=" + gatewayCode +
                ",lockCode=" + lockCode +
                ",gatewayName=" + gatewayName +
                ",lockName=" + lockName +
                ",openMode=" + openMode +
                ",timetag=" + timetag +
                "]";
    }

    public class CardAuthInfo{
        private String name;
        private String cardNumb;
        private String dnCode;
        private String serviceNumb;

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

        public String getServiceNumb() {
            return serviceNumb;
        }

        public void setServiceNumb(String serviceNumb) {
            this.serviceNumb = serviceNumb;
        }
    }
    public class PwdAuthInfo{
        private String password;
        private String serviceNumb;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getServiceNumb() {
            return serviceNumb;
        }

        public void setServiceNumb(String serviceNumb) {
            this.serviceNumb = serviceNumb;
        }
    }
}