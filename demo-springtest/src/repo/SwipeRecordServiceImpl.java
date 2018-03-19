package com.yishu.service.impl;

import com.yishu.dao.SwipeRecordDao;
import com.yishu.model.SwipeRecord;
import com.yishu.service.SwipeRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/5/16.
 */
@Service("swipeRecordService")
public class SwipeRecordServiceImpl implements SwipeRecordService {

    private static  final Logger logger = LoggerFactory.getLogger(SwipeRecordServiceImpl.class);
    @Autowired
    private SwipeRecordDao swipeRecordDao;

    public void add(SwipeRecord swipeRecord) {
        swipeRecordDao.add(swipeRecord);
    }

    public void update(SwipeRecord swipeRecord) {
        swipeRecordDao.update(swipeRecord);
    }

    public void deleteByTime(String timestamp) {
        swipeRecordDao.deleteByTime(timestamp);
    }

    public void deleteByDeviceid(String deviceid) {
        swipeRecordDao.deleteByDeviceid(deviceid);
    }

    public List<SwipeRecord> listAll() {
        return swipeRecordDao.listAll();
    }

//    public List<SwipeRecord> listAllWithStrategy(String orderColumn, String orderDir, SwipeRecordStrategy strategy) {
//        return swipeRecordDao.listAllWithStrategy(orderColumn,orderDir,strategy);
//    }

    public List<SwipeRecord> listAllWithStrategy(HashMap paramMap) {
        return swipeRecordDao.listAllWithStrategy(paramMap);
    }

    public List<SwipeRecord> listByTimezoneWhenFail(String startTime, String endTime) {
        return swipeRecordDao.listByTimezoneWhenFail(startTime,endTime);
    }

    public List<SwipeRecord> listByDeivceid(String deviceid) {
        return swipeRecordDao.listByDeivceid(deviceid);
    }

    public List<SwipeRecord> listByTimezone(String startTime, String endTime) {
//        logger.info("startTime:"+startTime+" endTime:"+endTime);
        return swipeRecordDao.listByTimezone(startTime,endTime);
    }

    public List<SwipeRecord> listByResult(int result) {
        return swipeRecordDao.listByResult(result);
    }

    public int countByParam(String endTime, int result, String deviceid) {
        return swipeRecordDao.countByParam(endTime, result, deviceid);
    }

    public int deleteByParam(String endTime, int result, String deviceid) {
        return swipeRecordDao.deleteByParam(endTime, result, deviceid);
    }
}
