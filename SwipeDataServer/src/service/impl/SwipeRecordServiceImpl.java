package service.impl;

import dao.SwipeRecordDao;
import model.SwipeRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.SwipeRecordService;

import java.util.List;

/**
 * Created by admin on 2017/5/16.
 */
@Service("swipeRecordService")
public class SwipeRecordServiceImpl implements SwipeRecordService {

    private static  final Logger logger = LoggerFactory.getLogger(SwipeRecordServiceImpl.class);
    @Autowired
    private SwipeRecordDao swipeRecordDao;

    @Override
    public void add(SwipeRecord swipeRecord) {
        swipeRecordDao.add(swipeRecord);
    }

    @Override
    public void update(SwipeRecord swipeRecord) {
        swipeRecordDao.update(swipeRecord);
    }

    @Override
    public void deleteByTime(String timestamp) {
        swipeRecordDao.deleteByTime(timestamp);
    }

    @Override
    public void deleteByDeviceid(String deviceid) {
        swipeRecordDao.deleteByDeviceid(deviceid);
    }

    @Override
    public List<SwipeRecord> listAll() {
        return swipeRecordDao.listAll();
    }

    @Override
    public List<SwipeRecord> listByDeivceid(String deviceid) {
        return swipeRecordDao.listByDeivceid(deviceid);
    }

    @Override
    public List<SwipeRecord> listByTimezone(String startTime, String endTime) {
        logger.info("startTime:"+startTime+" endTime:"+endTime);
        return swipeRecordDao.listByTimezone(startTime,endTime);
    }

    @Override

    public List<SwipeRecord> listByResult(int result) {
        return swipeRecordDao.listByResult(result);
    }
}
