package service;

import model.SwipeRecord;
import org.apache.ibatis.annotations.Param;

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
    public List<SwipeRecord> listByDeivceid(String deviceid);
    public List<SwipeRecord> listByTime(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
    List<SwipeRecord> listByResult(int result);
}
/*
Integer add(SwipeRecord swipeRecord);
    Integer update(SwipeRecord swipeRecord);
    Integer deleteByTime(String timestamp);
    Integer deleteByDeviceid(String deviceid);
    List<SwipeRecord> listByDeivceid(String deviceid);
    List<SwipeRecord> listByTimestamp(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
    List<SwipeRecord> listByResult(int result);
 */