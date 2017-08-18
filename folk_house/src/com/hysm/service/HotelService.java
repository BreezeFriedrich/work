/**
 * 
 */
package com.hysm.service;

import java.util.List;

import com.hysm.domain.House;
import com.hysm.domain.Servicer;

/**
 * 宾馆操作
 * 
 * @author john
 * 
 */

public interface HotelService
{

    // 添加服务
    public int addService(String hs_name, int hs_id);

    // 添加服务员
    public int addServicer(Servicer servicer);

    // 修改服务员
    public int reviseServicer(Servicer servicer);

    // 冻结服务员
    public int FreezyServicer(int id);

    // 激活管理员

    public int activateServicer(int id);

    // 根据宾馆id 查询服务员
    public List<Servicer> queryServicerById(String mhid);

    // 根据服务员id 查询服务员
    public Servicer queryServicerBySid(String id);

    // 根据服务员id修改服务员信息
    public int resiveServicerBySid(Servicer servicer);

    /**
     * @discription 根据服务员id查询服务房间
     * @author 刘正义
     * @created 2016-3-12 上午10:24:31
     * @param sid
     *            服务员id
     * @return
     */
    public List<House> queryHouse(String sid);

    /**
     * @discription 根据宾馆id查询清洁员
     * @author 刘正义
     * @created 2016-3-12 下午2:59:17
     * @param hotelid
     *            宾馆id
     * @return
     */
    public List<Servicer> queryClear(int hotelid);

    /**
     * @discription 给房间添加清洁员
     * @author 刘正义
     * @created 2016-3-12 下午5:12:20
     * @param hid
     *            房间id
     * @param servicerid
     *            清洁员id；
     * @return
     */
    public int addClear(String hid, String servicerid);

}
