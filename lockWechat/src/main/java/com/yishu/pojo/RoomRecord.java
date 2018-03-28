/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.pojo;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-28 15:20 admin
 * @since JDK1.7
 */
public class RoomRecord {
    private String roomTypeId;
    private String roomType;
    private String roomId;
    private String roomName;
    private String gatewayCode;
    private String lockCode;
    private int openMode;
    private String timetag;
    private UnlockRecord.CardInfo cardInfo;
    private UnlockRecord.PasswordInfo passwordInfo;

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

    public UnlockRecord.CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(UnlockRecord.CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public UnlockRecord.PasswordInfo getPasswordInfo() {
        return passwordInfo;
    }

    public void setPasswordInfo(UnlockRecord.PasswordInfo passwordInfo) {
        this.passwordInfo = passwordInfo;
    }
}
