package com.yishu.service;

import com.yishu.pojo.RoomType;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-12 11:20 admin
 * @since JDK1.7
 */
public interface IRoomService {
    public List<RoomType> getRoom(String ownerPhoneNumber);
    public boolean addRoomType(String ownerPhoneNumber,String roomType);
    public boolean editRoomType(String ownerPhoneNumber,String roomTypeId,String newRoomType);
    public boolean deleteRoomType(String ownerPhoneNumber,String roomTypeId);

    public boolean addRoom(String ownerPhoneNumber,String roomTypeId,String roomName,String gatewayCode,String lockCode);
    public boolean editRoom(String ownerPhoneNumber,String roomTypeId,String roomId,String newLockCode,String newRoomName);
    public boolean deleteRoom(String ownerPhoneNumber,String roomId);
    public List<Map> getUnusedDeviceList(String ownerPhoneNumber);
}
