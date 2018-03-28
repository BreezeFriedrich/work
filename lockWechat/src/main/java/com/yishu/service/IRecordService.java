/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service;

import com.yishu.pojo.Records;
import com.yishu.pojo.RoomRecord;
import com.yishu.pojo.RoomTypeContainRoom;
import com.yishu.pojo.UnlockRecord;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-14 15:40 admin
 * @since JDK1.7
 */
public interface IRecordService {
    public List<UnlockRecord> getUnlockRecord(String ownerPhoneNumber, String startTime, String endTime);

    public Records<UnlockRecord> getUnlockRecordPage(String ownerPhoneNumber, String startTime, String endTime, int pageNum, int pageSize);

    public Records<UnlockRecord> getGatewayUnlockRecordPage(String ownerPhoneNumber, String startTime, String endTime, String gatewayCode, int pageNum, int pageSize);

    public Records<UnlockRecord> getLockUnlockRecordPage(String ownerPhoneNumber, String startTime, String endTime, String lockCode, int pageNum, int pageSize);

    public Map getUnlockRecordDevice(String ownerPhoneNumber, String startTime, String endTime);

    public Map getUnlockRecordDevicePage(String ownerPhoneNumber, String startTime, String endTime, int pageNum,int pageSize);

    public Map getUnlockOperator(String ownerPhoneNumber, String startTime, String endTime);

    public Records<UnlockRecord> getOperatorUnlockRecordPage(String ownerPhoneNumber, String startTime, String endTime, String cardNum, int pageNum, int pageSize);

    public Records<RoomRecord> convertUnlockRecordToRoomRecord(List<UnlockRecord> unlockRecords, List<RoomTypeContainRoom> roomTypeCRs);
}
