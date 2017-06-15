package dao;

import model.SwipeRecord;
import model.SwipeRecordStrategy;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/5/10.
 */
public interface SwipeRecordDao {

    Integer add(SwipeRecord swipeRecord);
    Integer update(SwipeRecord swipeRecord);
    Integer deleteByTime(String timestamp);
    Integer deleteByDeviceid(String deviceid);
    List<SwipeRecord> listAll();
//    List<SwipeRecord> listAllWithStrategy(@Param("orderColumn") String orderColumn, @Param("orderDir") String orderDir, @Param("strategy")SwipeRecordStrategy strategy);
    List<SwipeRecord> listAllWithStrategy(HashMap paramMap);
    List<SwipeRecord> listByTimezoneWhenFail(@Param("startTime") String startTime, @Param("endTime") String endTime);
    List<SwipeRecord> listByDeivceid(String deviceid);
    List<SwipeRecord> listByTimezone(@Param("startTime") String startTime, @Param("endTime") String endTime);
    List<SwipeRecord> listByResult(int result);
    Integer countByParam (@Param("endTime") String endTime,@Param("result") int result,@Param("deviceid") String deviceid);
    Integer deleteByParam(@Param("endTime") String endTime,@Param("result") int result,@Param("deviceid") String deviceid);
}
