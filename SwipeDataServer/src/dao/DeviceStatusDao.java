package dao;

import model.DeviceStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by admin on 2017/5/10.
 */
public interface DeviceStatusDao {

    Integer add(DeviceStatus deviceStatus);
    Integer update(DeviceStatus deviceStatus);
    Integer deleteByTime(String endTime);
    Integer deleteByDeiviceid(String deviceid);
    List<DeviceStatus> listByDeviceid(String deviceid);
    List<DeviceStatus> listByStatus(int status);

    List<DeviceStatus> listAllWithoutDuplicate();
    List<DeviceStatus> listAll();
    List<DeviceStatus> listByParam(@Param("endTime") String endTime,@Param("status") int status,@Param("deviceid") String deviceid);
    Integer discardDuplicate();
}
