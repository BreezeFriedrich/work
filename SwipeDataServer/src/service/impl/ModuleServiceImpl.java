package service.impl;

import dao.DeviceStatusDao;
import model.DeviceStatus;
import service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/5/10.
 */
@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private DeviceStatusDao deviceStatusDao;

    @Override
    public void deleteByTime(String endTime) {
        deviceStatusDao.deleteByTime(endTime);
    }

    @Override
    public void deleteByDeviceid(String deviceid) {
        deviceStatusDao.deleteByDeiviceid(deviceid);
    }

    @Override
    public List<DeviceStatus> listByDeviceid(String deviceid) {
        return deviceStatusDao.listByDeviceid(deviceid);
    }

    @Override
    public List<DeviceStatus> listByStatus(int status) {
        return deviceStatusDao.listByStatus(status);
    }

    @Override
    public List<DeviceStatus> listAllWithoutDuplicate() {
        return deviceStatusDao.listAllWithoutDuplicate();
    }

    @Override
    public List<DeviceStatus> listAll() {
        return deviceStatusDao.listAll();
    }

    @Override
    public List<DeviceStatus> listByTimezone(String startTime, String endTime) {
        return deviceStatusDao.listByTimezone(startTime,endTime);
    }

    @Override
    public List<DeviceStatus> listByParam(String endTime, int status, String deviceid) {
        return deviceStatusDao.listByParam(endTime,status,deviceid);
    }

    @Override
    public int countByParam(String endTime, int status, String deviceid) {
        return deviceStatusDao.countByParams(endTime, status, deviceid);
    }

    @Override
    public int deleteByParam(String endTime, int status, String deviceid) {
        return deviceStatusDao.deleteByParam(endTime, status, deviceid);
    }

    @Override
    public void discardDuplicate() {
        deviceStatusDao.discardDuplicate();
    }
}
