package com.yishu.pojo;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-31 16:18 admin
 * @since JDK1.7
 */
public class GatewayLock {
    private String gatewayName;
    private String gatewayCode;
    private String gatewayLocation;
    private String gatewayComment;
    private String gatewayStatus;

    private String lockName;
    private String lockCode;
    private String lockLocation;
    private String lockComment;
    private String lockStatus;
    private String lockPower;

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
