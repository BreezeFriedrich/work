/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service;

import com.yishu.pojo.Records;
import com.yishu.pojo.UnlockRecord;
import com.yishu.pojo.UnlockRecordTableData;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-14 15:40 admin
 * @since JDK1.7
 */
public interface IRecordService {
    public List<UnlockRecord> getUnlockRecord(String ownerPhoneNumber, long startTime, long endTime);
    public List<UnlockRecord> getUnlockRecordFilter(String ownerPhoneNumber, long startTime, long endTime,Map<String,Object> filterparamMap);
    public List<UnlockRecord> filterUnlockRecord(List<UnlockRecord> unlockRecords,Map<String,Object> filterparamMap);
    public List<UnlockRecord> orderUnlockRecord(List<UnlockRecord> unlockRecords,Map<String,Object> orderparamMap);
    public List<UnlockRecordTableData> convertUnlockRecordToTabularData(List<UnlockRecord> unlockRecords);

    public Records<UnlockRecord> getUnlockRecordPage(String ownerPhoneNumber, long startTime, long endTime, int pageNum, int pageSize);

    public Records<UnlockRecord> getGatewayUnlockRecordPage(String ownerPhoneNumber, long startTime, long endTime, String gatewayCode, int pageNum, int pageSize);

    public List<UnlockRecord> getLockUnlockRecord(String ownerPhoneNumber, long startTime, long endTime, String lockCode);

    public Records<UnlockRecord>[] getLockUnlockRecordDaily(String ownerPhoneNumber, Date theDate, String lockCode);

    public Records<UnlockRecord> getLockUnlockRecordPage(String ownerPhoneNumber, long startTime, long endTime, String lockCode, int pageNum, int pageSize);

    public Map getUnlockRecordDevice(String ownerPhoneNumber, long startTime, long endTime);

    public Map getUnlockRecordDevicePage(String ownerPhoneNumber, long startTime, long endTime, int pageNum, int pageSize);

    public Map getUnlockOperator(String ownerPhoneNumber, long startTime, long endTime);

    public Records<UnlockRecord> getOperatorUnlockRecordPage(String ownerPhoneNumber, long startTime, long endTime, String cardNum, int pageNum, int pageSize);
}
