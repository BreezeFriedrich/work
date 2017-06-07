package com.yishu.service;

import com.yishu.model.SwipeRecord;
import java.util.List;

/**
 * Created by admin on 2017/5/22.
 */
public interface SwipeRecordService {

//    Integer add(SwipeRecord swipeRecord);
//    Integer update(SwipeRecord swipeRecord);
//    Integer deleteByTime(String timestamp);
//    Integer deleteByDeviceid(String deviceid);
    List<SwipeRecord> listAll();
//    List<SwipeRecord> listByDeivceid(String deviceid);
    List<SwipeRecord> listByTimezone(String startTime,String endTime);
    public int countByParam(String endTime,int result,String deviceid);
    public int deleteByParam(String endTime,int result,String deviceid);
//    List<SwipeRecord> listByResult(int result);
}
