/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service;

import java.util.List;

public interface RoomService {
	String getDeviceInfo(String ownerPhoneNumber);
	String getUnlockRecord(String ownerPhoneNumber, String startTime, String endTime);
	String getIDAuth(String ownerPhoneNumber, String gatewayCode, String lockCode);
	String getPwdAuth(String ownerPhoneNumber, String gatewayCode, String lockCode);
	String delIDAuth(String ownerPhoneNumber, String cardNumb, String serviceNumb, String lockCode);
	String delPwdAuth(String ownerPhoneNumber, String gatewayCode, String serviceNumb, String lockCode);
	String doIDAuth(String ownerPhoneNumber, String gatewayCode, String lockCode, String name, String cardNumb, String startTime, String endTime);
	String doPwdAuth(String ownerPhoneNumber, String gatewayCode, String lockCode, String password, String startTime, String endTime);
    String getOrderList(String ownerPhoneNumber, String startTime, String endTime);
	String getRoomList(String ownerPhoneNumber);
	String addRoomType(String ownerPhoneNumber, String roomType);
	String getFreeLock(String ownerPhoneNumber);
	String delRoom(String ownerPhoneNumber, String roomId);
	String resetRoom(String ownerPhoneNumber, String roomTypeId, String roomId, String newLockCode, String newRoomName);
	String addRoom(String ownerPhoneNumber, String roomTypeId, String roomName, String lockCode, String gatewayCode);
	String addOrder(String ownerPhoneNumber, String startTime, String endTime, String roomTypeId, String roomId, String cardInfoList, String password);
	String delOrder(String ownerPhoneNumber, String orderNumber);
	String updateOrder(String ownerPhoneNumber, String orderNumber, String startTime, String endTime, String roomId, String cardInfoList, String password);
	String getOrderContent(String ownerPhoneNumber, String orderNumber);
	String delRoomType(String ownerPhoneNumber, String roomTypeId);
}
