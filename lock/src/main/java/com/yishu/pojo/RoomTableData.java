package com.yishu.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-14 10:52 admin
 * @since JDK1.7
 */
public class RoomTableData {
    private String roomTypeId;
    private String roomType;
    private String roomId;
    private String roomName;
    private String gatewayCode;
    private String lockCode;
    @JsonProperty("DT_RowAttr")
    private Map DT_RowAttr;

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

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

    @JsonIgnore
    public Map getDT_RowAttr() {
        return DT_RowAttr;
    }

    @JsonIgnore
    public void setDT_RowAttr(Map DT_RowAttr) {
        this.DT_RowAttr = DT_RowAttr;
    }
}
