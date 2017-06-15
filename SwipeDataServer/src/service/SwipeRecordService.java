package service;

import model.SwipeRecord;
import model.SwipeRecordStrategy;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/5/16.
 */
public interface SwipeRecordService {

    public void add(SwipeRecord swipeRecord);
    public void update(SwipeRecord swipeRecord);
    public void deleteByTime(String timestamp);
    public void deleteByDeviceid(String deviceid);
    public List<SwipeRecord> listAll();
//    public List<SwipeRecord> listAllWithStrategy(String orderColumn, String orderDir, SwipeRecordStrategy strategy);
    public List<SwipeRecord> listAllWithStrategy(HashMap paramMap);
    public List<SwipeRecord> listByTimezoneWhenFail(String startTime,String endTime);
    public List<SwipeRecord> listByDeivceid(String deviceid);
    public List<SwipeRecord> listByTimezone(String startTime,String endTime);
    List<SwipeRecord> listByResult(int result);
    public int countByParam(String endTime,int result,String deviceid);
    public int deleteByParam(String endTime,int result,String deviceid);
}
/*
Integer add(SwipeRecord swipeRecord);
    Integer update(SwipeRecord swipeRecord);
    Integer deleteByTime(String timestamp);
    Integer deleteByDeviceid(String deviceid);
    List<SwipeRecord> listByDeivceid(String deviceid);
    List<SwipeRecord> listByTimestamp(@Param("startTime") String startTime, @Param("endTime") String endTime);
    List<SwipeRecord> listByResult(int result);
 */