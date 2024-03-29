package service;

import model.DeviceStatus;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/5/10.
 */
public interface ModuleService {

    public void deleteByTime(String endTime);
    public void deleteByDeviceid(String deviceid);

    public List<DeviceStatus> listByDeviceid(String deviceid);
    public List<DeviceStatus> listByStatus(int status);
    public List<DeviceStatus> listAllWithoutDuplicate();
    public List<DeviceStatus> listAll();
    public List<DeviceStatus> listAllWithStrategy(HashMap paramMap);
    public List<DeviceStatus> listByTimezone(String startTime,String endTime);
    public List<DeviceStatus> listByParam(String endTime,int status,String deviceid);
    public int countByParam(String endTime,int status,String deviceid);
    public int deleteByParam(String endTime,int status,String deviceid);
    public void discardDuplicate();//删除过去模块status记录,只保留最新的模块status

}
