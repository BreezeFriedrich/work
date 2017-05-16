package dao;

import model.SwipeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/5/10.
 */
public interface SwipeRecordDao {

    Integer add(SwipeRecord swipeRecord);
    Integer update(SwipeRecord swipeRecord);
    Integer deleteByTime(String timestamp);
    Integer deleteByDeviceid(String deviceid);
    List<SwipeRecord> listByDeivceid(String deviceid);
    List<SwipeRecord> listByTimestamp(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
    List<SwipeRecord> listByResult(int result);
}
