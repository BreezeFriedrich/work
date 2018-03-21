package com.yishu.pojo;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-12-22 15:22 admin
 * @since JDK1.7
 */
public class User<X> {
    private String phoneNumber;
    private int grade;
    private String name;
    private String location;
    private List<X> subordinateList;
    private List<RoomTypeContainRoom> roomTypeContainRoomList;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<X> getSubordinateList() {
        return subordinateList;
    }

    public void setSubordinateList(List<X> subordinateList) {
        this.subordinateList = subordinateList;
    }

    public List<RoomTypeContainRoom> getRoomTypeContainRoomList() {
        return roomTypeContainRoomList;
    }

    public void setRoomTypeContainRoomList(List<RoomTypeContainRoom> roomTypeContainRoomList) {
        this.roomTypeContainRoomList = roomTypeContainRoomList;
    }
}
