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
    public String lockCode;
    public String gatewayCode;
    public int openMode;
    public String timetag;

    public CardInfo cardInfo;
    public PasswordInfo passwordInfo;

    @Override
    public String toString() {
        return "UnlockRecord [lockCode=" + lockCode + ", gatewayCode="
                + gatewayCode + ", openMode=" + openMode + ", timetag="
                + timetag + "]";
    }

    public class CardInfo{
        public String name;
        public String cardNumb;
        public String dnCode;
        public String serviceNumb;
    }
    public class PasswordInfo{
        public String password;
        public String serviceNumb;
    }
}
