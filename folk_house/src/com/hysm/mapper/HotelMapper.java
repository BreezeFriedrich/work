/**
 * 
 */
package com.hysm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hysm.domain.House;
import com.hysm.domain.Servicer;
import com.hysm.mapper.sqlpro.HotelPro;

/**
 * 宾馆操作类
 * 
 * @author john
 * 
 */
public interface HotelMapper
{

    // 添加服务
    @InsertProvider(method = "addService", type = HotelPro.class)
    public int addService(@Param("ht_id")
    int ht_id, @Param("hs_name")
    String hs_name);

    // 添加服务员
    @InsertProvider(method = "addServicer", type = HotelPro.class)
    public int addServicer(Servicer servicer);

    // 修改服务员
    @UpdateProvider(method = "reviseServicer", type = HotelPro.class)
    public int reviseServicer(Servicer servicer);

    // 冻结服务员
    @UpdateProvider(method = "FreezyServicer", type = HotelPro.class)
    public int FreezyServicer(int id);

    // 激活服务员
    @UpdateProvider(method = "activateServicer", type = HotelPro.class)
    public int activateServicer(int id);

    // 根据宾馆id 查询服务员
    @SelectProvider(method = "queryServicerById", type = HotelPro.class)
    public List<Servicer> queryServicerById(String mhid);

    // 根据服务员id 查询服务员
    @SelectProvider(method = "queryServicerBySid", type = HotelPro.class)
    public Servicer queryServicerBySid(String id);

    // 根据服务员id修改服务员
    @UpdateProvider(method = "resiveServicerBySid", type = HotelPro.class)
    public int resiveServicerBySid(Servicer servicer);

    /**
     * @discription 根据服务员id 查询清扫房间
     * @author 刘正义
     * @created 2016-3-12 上午10:13:29
     * @param sid
     *            服务员id
     * @return
     */
    @SelectProvider(method = "queryHouseBySeriverId", type = HotelPro.class)
    public List<House> queryHouseBySeriverId(String sid);

    /**
     * @discription 根据宾馆id查询清洁员
     * @author 刘正义
     * @created 2016-3-12 下午2:46:28
     * @param hotelid
     *            宁
     * @return
     */
    @SelectProvider(method = "queryClear", type = HotelPro.class)
    public List<Servicer> queryClear(int hotelid);

    /**
     * @discription 给房间添加清洁员
     * @author 刘正义
     * @created 2016-3-12 下午4:52:38
     * @param hid
     *            房间id
     * @param servicerid
     *            服务员id
     * @return
     */
    @InsertProvider(method = "addClear", type = HotelPro.class)
    public int addClear(@Param("hid")
    String hid, @Param("servicerid")
    String servicerid);
}
