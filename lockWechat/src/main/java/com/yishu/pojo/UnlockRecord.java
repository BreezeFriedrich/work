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

    private String lockCode;
    private String gatewayCode;
    private int openMode;
    private String timetag;

    private CardInfo cardInfo;
    private PasswordInfo passwordInfo;

    public String getLockCode() {
        return lockCode;
    }

    public void setLockCode(String lockCode) {
        this.lockCode = lockCode;
    }

    public String getGatewayCode() {
        return gatewayCode;
    }

    public void setGatewayCode(String gatewayCode) {
        this.gatewayCode = gatewayCode;
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

    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public PasswordInfo getPasswordInfo() {
        return passwordInfo;
    }

    public void setPasswordInfo(PasswordInfo passwordInfo) {
        this.passwordInfo = passwordInfo;
    }

    @Override
    public String toString() {
        return "UnlockRecord [lockCode=" + lockCode + ", gatewayCode="
                + gatewayCode + ", openMode=" + openMode + ", timetag="
                + timetag + "]";
    }

    public class CardInfo{
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
    public class PasswordInfo{
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
