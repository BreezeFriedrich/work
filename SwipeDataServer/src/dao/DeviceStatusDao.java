package dao;

import model.DeviceStatus;

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
    Integer discardDuplicate();
}
