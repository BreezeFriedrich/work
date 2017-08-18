/**
 * 
 */
package com.hysm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.domain.House;
import com.hysm.domain.Servicer;
import com.hysm.mapper.HotelMapper;
import com.hysm.service.HotelService;

/**
 * 宾馆操作实现类
 * 
 * @author john
 * 
 */
@Service("hotelservice")
public class HotelServiceImpl implements HotelService
{

    @Autowired
    private HotelMapper hotelmapper;

    /*
     * (non-Javadoc) 添加 服务
     * 
     * @see com.hysm.service.HotelService#addService()
     */
    @Override
    public int addService(String hs_name, int ht_id)
    {
        int result = hotelmapper.addService(ht_id, hs_name);

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hysm.service.HotelService#addServicer(com.hysm.domain.Servicer)
     */
    @Override
    public int addServicer(Servicer servicer)
    {
        int result = hotelmapper.addServicer(servicer);

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.hysm.service.HotelService#reviseServicer(com.hysm.domain.Servicer)
     */
    @Override
    public int reviseServicer(Servicer servicer)
    {
        int result = hotelmapper.reviseServicer(servicer);

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hysm.service.HotelService#FreezyServicer(int)
     */
    @Override
    public int FreezyServicer(int id)
    {
        int result = hotelmapper.FreezyServicer(id);

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hysm.service.HotelService#activateServicer(int)
     */
    @Override
    public int activateServicer(int id)
    {
        int result = hotelmapper.activateServicer(id);

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * 根据宾馆id 查询服务员
     * 
     * @see com.hysm.service.HotelService#queryServicerById(java.lang.String)
     */
    @Override
    public List<Servicer> queryServicerById(String mhid)
    {
        List<Servicer> list = hotelmapper.queryServicerById(mhid);

        return list;
    }

    /*
     * (non-Javadoc) 根据服务员id 查询服务员
     * 
     * @see com.hysm.service.HotelService#queryServicerBySid(java.lang.String)
     */
    @Override
    public Servicer queryServicerBySid(String id)
    {
        Servicer servicer = hotelmapper.queryServicerBySid(id);

        return servicer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.hysm.service.HotelService#resiveServicerBySid(com.hysm.domain.Servicer
     * )
     */
    @Override
    public int resiveServicerBySid(Servicer servicer)
    {
        int result = hotelmapper.resiveServicerBySid(servicer);

        return result;
    }

    /**
     * @discription 根据服务员id 查询服务房间
     * @author 刘正义
     * @created 2016-3-12 上午10:26:03
     * @param sid
     *            服务员id
     * @return
     * @see com.hysm.service.HotelService#queryHouse(java.lang.String)
     */

    @Override
    public List<House> queryHouse(String sid)
    {
        List<House> house = hotelmapper.queryHouseBySeriverId(sid);

        return house;
    }

    /**
     * @discription 根据宾馆id查询清洁员
     * @author 刘正义
     * @created 2016-3-12 下午3:00:18
     * @param hotelid
     *            宾馆id
     * @return
     * @see com.hysm.service.HotelService#queryClear(int)
     */

    @Override
    public List<Servicer> queryClear(int hotelid)
    {
        List<Servicer> list = hotelmapper.queryClear(hotelid);

        return list;
    }

    /**
     * @discription 给房间添加清洁员
     * @author 刘正义
     * @created 2016-3-12 下午5:14:06
     * @param hid
     * @param servicerid
     * @return
     * @see com.hysm.service.HotelService#addClear(java.lang.String,
     *      java.lang.String)
     */

    @Override
    public int addClear(String hid, String servicerid)
    {
        int result = hotelmapper.addClear(hid, servicerid);

        return result;
    }

}
