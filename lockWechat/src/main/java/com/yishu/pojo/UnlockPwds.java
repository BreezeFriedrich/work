/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.pojo;

import java.util.List;

/**
 * 智能锁开锁密码
 *
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-08 14:37 admin
 * @since JDK1.7
 */
public class UnlockPwds {
    private String defaultPassword1;
    private String defaultPassword2;
    private List<UnlockPwd> passwordList;

    public String getDefaultPassword1() {
        return defaultPassword1;
    }

    public void setDefaultPassword1(String defaultPassword1) {
        this.defaultPassword1 = defaultPassword1;
    }

    public String getDefaultPassword2() {
        return defaultPassword2;
    }

    public void setDefaultPassword2(String defaultPassword2) {
        this.defaultPassword2 = defaultPassword2;
    }

    public List<UnlockPwd> getPasswordList() {
        return passwordList;
    }

    public void setPasswordList(List<UnlockPwd> passwordList) {
        this.passwordList = passwordList;
    }
}
