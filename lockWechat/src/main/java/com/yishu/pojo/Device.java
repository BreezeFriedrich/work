/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.pojo;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-09-08 14:10 admin
 * @since JDK1.7
 */
public class Device {
    private List<Lock> lockList;
    private String gatewayName;
    private String gatewayCode;
    private String gatewayLocation;
    private String gatewayComment;
    private String gatewayStatus;

    public List<Lock> getLockList() {
        return lockList;
    }

    public void setLockList(List<Lock> lockList) {
        this.lockList = lockList;
    }

    public String getGatewayName() {
        return gatewayName;
    }

    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    public String getGatewayCode() {
        return gatewayCode;
    }

    public void setGatewayCode(String gatewayCode) {
        this.gatewayCode = gatewayCode;
    }

    public String getGatewayLocation() {
        return gatewayLocation;
    }

    public void setGatewayLocation(String gatewayLocation) {
        this.gatewayLocation = gatewayLocation;
    }

    public String getGatewayComment() {
        return gatewayComment;
    }

    public void setGatewayComment(String gatewayComment) {
        this.gatewayComment = gatewayComment;
    }

    public String getGatewayStatus() {
        return gatewayStatus;
    }

    public void setGatewayStatus(String gatewayStatus) {
        this.gatewayStatus = gatewayStatus;
    }
}
