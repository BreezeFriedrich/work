package com.yishu.service;

import com.yishu.model.SwipeRecord;
import com.yishu.model.SwipeRecordStrategy;

import java.util.HashMap;
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
//    List<SwipeRecord> listAllWithStrategy(String orderColumn,String orderDir,SwipeRecordStrategy strategy);
    List<SwipeRecord> listAllWithStrategy(HashMap paramMap);
    List<SwipeRecord> listByTimezoneWhenFail(String startTime, String endTime);
//    List<SwipeRecord> listByDeivceid(String deviceid);
    List<SwipeRecord> listByTimezone(String startTime,String endTime);
    public int countByParam(String endTime,int result,String deviceid);
    public int deleteByParam(String endTime,int result,String deviceid);
//    List<SwipeRecord> listByResult(int result);
}
