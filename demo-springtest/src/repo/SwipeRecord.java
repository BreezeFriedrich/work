package com.yishu.model;

/**
 * Created by admin on 2017/5/10.
 */
public class SwipeRecord {

    private String deviceid;
    private String deviceip;
    private String clientid;
    private String clientip;
    private int result;
    private String timestamp;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDeviceip() {
        return deviceip;
    }

    public void setDeviceip(String deviceip) {
        this.deviceip = deviceip;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String toJson(){
        return "{\"deviceid\":+this.deviceid," +
                "\"deviceip\":this.deviceip," +
                "\"clientid\":this.clientid," +
                "\"clientip\":this.clientip," +
                "\"this.result\":this.result," +
                "\"this.timestamp\":this.timestamp}";
    }
}
