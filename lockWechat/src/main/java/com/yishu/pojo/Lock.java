/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.pojo;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-08 14:15 admin
 * @since JDK1.7
 */
public class Lock {
    private String lockName;
    private String lockCode;
    private String lockLocation;
    private String lockComment;
    private String lockStatus;
    private String lockPower;

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public String getLockCode() {
        return lockCode;
    }

    public void setLockCode(String lockCode) {
        this.lockCode = lockCode;
    }

    public String getLockLocation() {
        return lockLocation;
    }

    public void setLockLocation(String lockLocation) {
        this.lockLocation = lockLocation;
    }

    public String getLockComment() {
        return lockComment;
    }

    public void setLockComment(String lockComment) {
        this.lockComment = lockComment;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getLockPower() {
        return lockPower;
    }

    public void setLockPower(String lockPower) {
        this.lockPower = lockPower;
    }
}
