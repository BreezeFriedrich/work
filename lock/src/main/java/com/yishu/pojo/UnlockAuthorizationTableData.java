package com.yishu.pojo;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-02-07 17:14 admin
 * @since JDK1.7
 */
public class UnlockAuthorizationTableData {
    private int openMode;
    private String credential;
    private String name;
    private String startTime;
    private String endTime;

    public int getOpenMode() {
        return openMode;
    }

    public void setOpenMode(int openMode) {
        this.openMode = openMode;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
