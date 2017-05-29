package com.yishu.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/5/22.
 */
public interface SwipeRecordService {

//    Integer add(com.yishu.model.SwipeRecord swipeRecord);
//    Integer update(com.yishu.model.SwipeRecord swipeRecord);
//    Integer deleteByTime(String timestamp);
//    Integer deleteByDeviceid(String deviceid);
    List<com.yishu.model.SwipeRecord> listAll();
//    List<com.yishu.model.SwipeRecord> listByDeivceid(String deviceid);
    List<com.yishu.model.SwipeRecord> listByTimezone(@Param("startTime") String startTime, @Param("endTime") String endTime);
//    List<com.yishu.model.SwipeRecord> listByResult(int result);
}
