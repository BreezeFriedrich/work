package com.yishu.pojo;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-12 11:31 admin
 * @since JDK1.7
 */
public class RoomType {
    private String roomTypeId;
    private String roomType;
    private List<Room> roomInfoList;

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

    public List<Room> getRoomInfoList() {
        return roomInfoList;
    }

    public void setRoomInfoList(List<Room> roomInfoList) {
        this.roomInfoList = roomInfoList;
    }
}
