/**
 * 
 */
package com.hysm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.domain.Characteristic;
import com.hysm.domain.CharacteristicValue;
import com.hysm.domain.House;
import com.hysm.domain.HouseType;
import com.hysm.domain.Img;
import com.hysm.domain.PageBean;
import com.hysm.domain.Price;
import com.hysm.domain.Property;
import com.hysm.domain.PropertyValue;
import com.hysm.domain.Schedule;
import com.hysm.mapper.FolkMapper;
import com.hysm.service.FolkService;

/**
 * @author john
 * 
 */
@Service("folk")
public class FolkServiceImpl implements FolkService
{

    @Autowired
    private FolkMapper folkmapper;

    /*
     * 给房屋添加价格
     * 
     * @see com.hysm.service.FolkService#savePrice(com.hysm.domain.Price)
     */
    @Override
    public int savePrice(Price price)
    {
        int result = folkmapper.savePrice(price);

        return result;
    }

    /*
     * 添加房屋类型
     * 
     * @see
     * com.hysm.service.FolkService#saveHouseType(com.hysm.domain.HouseType)
     */
    @Override
    public int saveHouseType(HouseType housetype, Price price, List<String> imgs)
    {
        int htnum = folkmapper.saveHouseType(housetype);
        // 如果保存成功
        if (htnum > 0)
        {
            // 获取房屋类型 id
            price.setHt_id(housetype.getHt_id());
            // 保存价格
            folkmapper.savePrice(price);
            // 保存图片
            if (imgs.size() > 0)
            {
                folkmapper.saveImg(imgs, housetype.getHt_id());
            }
        }

        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * 查询属性和属性值
     * 
     * @see com.hysm.service.FolkService#queryPro()
     */
    @Override
    public List<Property> queryPro()
    {

        List<Property> plist = folkmapper.queryPro();

        for (Property pro : plist)
        {

            List<PropertyValue> pvlist = folkmapper.queryProValue(pro
                    .getPro_id());

            pro.setPvlist(pvlist);
        }

        return plist;
    }

    /*
     * (non-Javadoc) 根据宾馆查询民居
     * 
     * @see com.hysm.service.FolkService#queryHousetype(int)
     */
    @Override
    public PageBean<HouseType> queryHousetype(int hotelid, int pn)
    {

        PageBean<HouseType> pbht = new PageBean<HouseType>();

        int ps = 15;
        int beginNum = (pn - 1) * ps;
        int rowCount = folkmapper.queryHotelCount(hotelid);
        pbht.setPageSize(ps);
        pbht.setPageNum(pn);
        pbht.setRowCount(rowCount);

        List<HouseType> list = folkmapper.queryHouseType(hotelid, beginNum, ps);

        pbht.setList(list);
        return pbht;
    }

    /*
     * (non-Javadoc) 根据房屋的类型 id 查询民居的价格
     * 
     * @see com.hysm.service.FolkService#queryHousePriceByHtid(java.lang.String)
     */
    @Override
    public Price queryHousePriceByHtid(String htid)
    {

        Price price = folkmapper.queryHousePrice(htid);

        return price;
    }

    /*
     * (non-Javadoc) 房屋类型下架
     * 
     * @see com.hysm.service.FolkService#downHouseType(java.lang.String)
     */
    @Override
    public int downHouseType(String htid)
    {

        int result = folkmapper.downHouseType(htid);

        return result;
    }

    /*
     * (non-Javadoc) 房屋类型 上架
     * 
     * @see com.hysm.service.FolkService#upHouseType(java.lang.String)
     */
    @Override
    public int upHouseType(String htid)
    {
        int result = folkmapper.upHouseType(htid);

        return result;
    }

    /*
     * (non-Javadoc) 查询商品的类型和价格
     * 
     * @see com.hysm.service.FolkService#queryHousetypeAndImg(java.lang.String)
     */
    @Override
    public HouseType queryHousetypeAndImg(String htid)
    {

        HouseType housetype = folkmapper.queryHouseTypeByhtid(htid);

        if (housetype != null)
        {
            List<Img> ilist = folkmapper.queryImgByid(htid);

            housetype.setIlist(ilist);
        }

        return housetype;
    }

    /*
     * (non-Javadoc) 删除图片
     * 
     * @see com.hysm.service.FolkService#deleteImg(java.lang.String)
     */
    @Override
    public int deleteImg(String imgid)
    {

        int result = folkmapper.deletImg(imgid);

        return result;
    }

    /*
     * (non-Javadoc) 查询同一宾馆的房型是否相同
     * 
     * @see com.hysm.service.FolkService#queryechoHouseType(int,
     * java.lang.String) return 查询记录条数
     */
    @Override
    public int queryechoHouseType(int hotel_id, String ht_name)
    {
        int result = folkmapper.queryechoHouseType(hotel_id, ht_name);

        return result;
    }

    /*
     * (non-Javadoc) 修改房型和房型的图片
     * 
     * @see
     * com.hysm.service.FolkService#alertHousetype(com.hysm.domain.HouseType,
     * java.util.Map)
     */
    @Override
    public int alertHousetype(HouseType housetype, List<String> imgs)
    {
        // 修改用户的房型
        int result = folkmapper.alertHouseTypeByid(housetype);

        // 保存图片
        if (imgs.size() > 0)
        {
            folkmapper.saveImg(imgs, housetype.getHt_id());
        }
        return 0;
    }

    /*
     * (non-Javadoc) 房间
     * 
     * @see com.hysm.service.FolkService#saveHouse(com.hysm.domain.House)
     */
    @Override
    public int saveHouse(House house)
    {
        int result = folkmapper.saveHouse(house);

        return result;
    }

    /*
     * (non-Javadoc) 查询特色和特色值
     * 
     * @see com.hysm.service.FolkService#queryCharac()
     */
    @Override
    public List<Characteristic> queryCharac()
    {
        List<Characteristic> list = folkmapper.queryCharac();

        if (list != null && list.size() > 0)
        {
            for (Characteristic charac : list)
            {

                List<CharacteristicValue> cvlist = folkmapper
                        .queryCharacValue(charac.getCharac_id());
                charac.setCvlist(cvlist);

            }

        }
        return list;
    }

    /*
     * (non-Javadoc) 查询房间
     * 
     * @see com.hysm.service.FolkService#queryHouseByHtid(java.lang.String)
     */
    @Override
    public List<House> queryHouseByHtid(String htid)
    {

        List<House> houselist = folkmapper.queryHouseByHtid(htid);

        if (houselist != null && houselist.size() > 0)
        {
            for (House house : houselist)
            {
                String charav_name = folkmapper.queryCharacValueOne(house
                        .getCharav_id());

                if (charav_name != null)
                {
                    house.setCharav_name(charav_name);

                }
            }

        }

        return houselist;
    }

    /*
     * (non-Javadoc) 下架商品
     * 
     * @see com.hysm.service.FolkService#downHouse(java.lang.String)
     */
    @Override
    public int downHouse(String house_id)
    {
        int result = folkmapper.downHouse(house_id);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hysm.service.FolkService#upHouse(java.lang.String)
     */
    @Override
    public int upHouse(String house_id)
    {
        int result = folkmapper.upHouse(house_id);

        return result;
    }

    /*
     * (non-Javadoc) 根据房间id 查询房间
     * 
     * @see com.hysm.service.FolkService#queryHouseById(java.lang.String)
     */
    @Override
    public House queryHouseById(String hid)
    {
        House house = folkmapper.queryHouseById(hid);

        return house;
    }

    /*
     * (non-Javadoc) 根据 id 修改房间信息
     * 
     * @see com.hysm.service.FolkService#alertHouseById(com.hysm.domain.House)
     */
    @Override
    public int alertHouseById(House house)
    {

        int result = folkmapper.alertHouseById(house);

        return result;
    }

    /**
     * @discription 根据房间id 查询日程
     * @author 刘正义
     * @created 2016-3-8 下午7:55:27
     * @param house_id
     *            房间id
     * @return
     * @see com.hysm.service.FolkService#querySchedule(int)
     */

    @Override
    public List<Schedule> querySchedule(String house_id)
    {
        List<Schedule> list = folkmapper.querySchedule(house_id);

        return list;
    }

    /**
     * @discription 根据宾馆id 查询房间
     * @author 刘正义
     * @created 2016-3-8 下午8:06:34
     * @param hotel_id
     *            宾馆id
     * @return
     * @see com.hysm.service.FolkService#queryHouseAll(int)
     */

    @Override
    public List<House> queryHouseAll(int hotel_id)
    {
        List<House> list = folkmapper.queryHouseAll(hotel_id);

        return list;
    }
}
