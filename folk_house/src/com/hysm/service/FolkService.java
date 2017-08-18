/**
 * 
 */
package com.hysm.service;

import java.util.List;
import java.util.Map;

import com.hysm.domain.Characteristic;
import com.hysm.domain.House;
import com.hysm.domain.HouseType;
import com.hysm.domain.PageBean;
import com.hysm.domain.Price;
import com.hysm.domain.Property;
import com.hysm.domain.Schedule;

/**
 * @author john
 * 
 */
public interface FolkService
{

    // 给房屋添加价格
    public int savePrice(Price price);

    // 添加房屋类型
    public int saveHouseType(HouseType housetype, Price price, List<String> imgs);

    // 查询属性和属性值
    public List<Property> queryPro();

    // 根据宾馆查询民居
    public PageBean<HouseType> queryHousetype(int hotelid, int pn);

    // 查询商品价格
    public Price queryHousePriceByHtid(String htid);

    // 房屋下架
    public int downHouseType(String htid);

    // 房屋上架
    public int upHouseType(String htid);

    // 查询 商品的类型和价格
    public HouseType queryHousetypeAndImg(String htid);

    // 删除图片
    public int deleteImg(String imgid);

    // 查询同一宾馆的房型是否重复
    public int queryechoHouseType(int hotel_id, String ht_name);

    // 修改房型 和房型的图片
    public int alertHousetype(HouseType housetype, List<String> imgs);

    // 保存房间
    public int saveHouse(House house);

    // 查询特色
    public List<Characteristic> queryCharac();

    // 查询房间
    public List<House> queryHouseByHtid(String htid);

    // 下架商品
    public int downHouse(String house_id);

    // 上架商品
    public int upHouse(String house_id);

    // 查询房间 根据id
    public House queryHouseById(String hid);

    // 修改根据id房间的信息
    public int alertHouseById(House house);

    /**
     * @discription 根据房间id查询日程
     * @author 刘正义
     * @created 2016-3-8 下午7:54:01
     * @param house_id
     *            房间id
     * @return
     */
    public List<Schedule> querySchedule(String house_id);

    /**
     * @discription 根据宾馆id 查询房间
     * @author 刘正义
     * @created 2016-3-8 下午8:06:01
     * @param hotel_id
     * @return
     */
    public List<House> queryHouseAll(int hotel_id);

}
