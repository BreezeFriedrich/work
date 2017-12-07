/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service;

import com.yishu.pojo.Records;
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

    public Records<UnlockRecord> getUnlockRecordPage(String ownerPhoneNumber, String startTime, String endTime, int pageNum, int pageSize) throws ParseException;

//    public Map<String,UnlockRecord> getUnlockRecordLock(String ownerPhoneNumber, String startTime, String endTime, String lockCode);

    public Map getUnlockRecordDevice(String ownerPhoneNumber, String startTime, String endTime);

    public Map getUnlockRecordDevicePage(String ownerPhoneNumber, String startTime, String endTime, int pageNum,int pageSize);
}
