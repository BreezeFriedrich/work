package com.hysm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.mapper.HouseMapper;
import com.hysm.pojo.House;
import com.hysm.pojo.Image;
import com.hysm.pojo.Manager;
import com.hysm.pojo.PageBean;
import com.hysm.pojo.Price;
import com.hysm.pojo.Property;
import com.hysm.pojo.PropertyValue;
import com.hysm.pojo.Sku;
import com.hysm.pojo.Supporting;
import com.hysm.pojo.Tag;
import com.hysm.service.IHouseService;

@Service
public class HouseSereviceImpl implements IHouseService
{
    @Autowired
    private HouseMapper houseMapper;

    public String get_user()
    {
        return houseMapper.get_user();
    }

    /**
     * @discription 根据城市返回城市的 标签 tag
     * @author 刘正义
     * @created 2015-12-22 下午4:53:19
     * @param city
     * @return
     * @see com.hysm.service.IHouseService#queryTagListByCity(java.lang.String)
     */

    @Override
    public List<Tag> queryTagListByCity(String city)
    {
        List<Tag> list = houseMapper.queryTagListByCityId(city);
        return list;
    }

    /**
     * @discription 查询属性
     * @author 刘正义
     * @created 2015-12-23 上午10:08:40
     * @return
     * @see com.hysm.service.IHouseService#queryAllProperty()
     */

    @Override
    public List<Property> queryAllProperty()
    {
        List<Property> propertys = houseMapper.queryAllProperty();
        for (Property property : propertys)
        {
            int propertyid = property.getPro_id();
            List<PropertyValue> pvlist = houseMapper.queryPropertyValueByPropertyId(propertyid);
            property.setPvlist(pvlist);
        }

        return propertys;
    }

    /**
     * @discription 查询配置
     * @author 刘正义
     * @created 2015-12-23 上午10:18:08
     * @return
     * @see com.hysm.service.IHouseService#queryAllSupporting()
     */

    @Override
    public List<Supporting> queryAllSupporting()
    {
        List<Supporting> supportings = houseMapper.queryAllSupporting();
        return supportings;
    }

    /**
     * @discription 添加民居
     * @author 刘正义
     * @created 2015-12-24 下午2:54:09
     * @param house
     * @param sku
     * @return
     * @see com.hysm.service.IHouseService#addHouse(com.hysm.pojo.House,
     *      com.hysm.pojo.Sku)
     */

    @Override
    public int insertHouse(House house, Sku sku, List<Integer> tags,
            List<Integer> pro, List<PropertyValue> provalue,
            List<Integer> suppor)
    {
        int result = 1;
        int skuvalue = 0;

        // 创建sku
        if (sku != null)
        {
            skuvalue = houseMapper.insertSku(sku);
        }
        // 处理可以输入的输入值
        if (skuvalue == 1 && sku != null && provalue != null
                && provalue.size() > 0)
        {
            for (int i = 0; i < provalue.size(); i++)
            {
                houseMapper.insertPropertyValue(provalue.get(i));
                pro.add(provalue.get(i).getV_id());
            }
        }
        // 处理属性值
        if (sku != null && pro.size() > 0)
        {
            houseMapper.insertSkuPro(pro, sku.getSku_id());
        }
        // 添加商品
        if (house != null)
        {
            house.setSku_id(sku.getSku_id());
            houseMapper.insertHouse(house);
        }
        // 添加标签
        if (tags != null && tags.size() > 0 && house != null)
        {
            houseMapper.insertHouseTag(tags, house.getHouse_id());
        }
        // 添加配套设置
        if (suppor != null && suppor.size() > 0 && house != null)
        {
            houseMapper.insertHouseSupp(suppor, house.getHouse_id());
        }

        return house.getHouse_id();
    }

    /**
     * @discription 用户登录
     * @author 刘正义
     * @created 2015-12-26 下午3:47:09
     * @param name
     * @param pwd
     * @return
     * @see com.hysm.service.IHouseService#login(java.lang.String,
     *      java.lang.String)
     */

    @Override
    public Manager login(String name, String pwd)
    {
        Manager manager = houseMapper.login(name, pwd);
        return manager;
    }

    /**
     * @discription 给民居添加图片
     * @author 刘正义
     * @created 2015-12-28 上午11:29:53
     * @param list
     * @param houseid
     * @return
     * @see com.hysm.service.IHouseService#insertHouseImage(java.util.List, int)
     */

    @Override
    public int insertHouseImage(List<String> list, int houseid)
    {
        int val = houseMapper.insertImage(list, houseid);
        return val;
    }

    /**
     * @discription 添加民居价格
     * @author 刘正义
     * @created 2015-12-28 下午3:34:01
     * @param price
     * @param houseid
     * @return
     * @see com.hysm.service.IHouseService#insertPrice(com.hysm.pojo.Price, int)
     */

    @Override
    public int insertPrice(Price price, int houseid)
    {
        price.setHouse_id(houseid);
        int val = houseMapper.insertPrice(price);

        return val;
    }

    /**
     * @discription 分页查询
     * @author 刘正义
     * @created 2015-12-28 下午5:47:05
     * @param merchantsid
     *            店铺id
     * @param pn
     *            页码
     * @return
     * @see com.hysm.service.IHouseService#queryHouse(int, int)
     */

    public PageBean<House> queryHouse(int merchantsid, int pn)
    {
        PageBean<House> pbhouse = new PageBean<House>();
        int pageSize = 10;
        int beginNum = (pn - 1) * pageSize;
        int rowCount = houseMapper.queryHouseNum(merchantsid);
        pbhouse.setPageSize(pageSize);
        pbhouse.setRowCount(rowCount);
        pbhouse.setPageNum(pn);
       /* List<House> list = houseMapper.queryHouse(merchantsid, beginNum,
                pageSize);*/
        List<House> list = houseMapper.queryHouse(merchantsid, beginNum,pageSize);
        pbhouse.setList(list);
        return pbhouse;
    }

    public PageBean<House> queryHouse1(int merchantsid, int pn,String stime,String etime)
    {
        PageBean<House> pbhouse = new PageBean<House>();
        int pageSize = 10;
        int beginNum = (pn - 1) * pageSize;
        int rowCount = houseMapper.queryHouseNum(merchantsid);
        pbhouse.setPageSize(pageSize);
        pbhouse.setRowCount(rowCount);
        pbhouse.setPageNum(pn);
       /* List<House> list = houseMapper.queryHouse(merchantsid, beginNum,
                pageSize);*/
        List<House> list = houseMapper.queryHouse1(merchantsid, beginNum,pageSize,stime,etime);
        pbhouse.setList(list);
        return pbhouse;
    }
    
    /**
     * @discription 根据id 查询 房屋 信息
     * @author 刘正义
     * @created 2015-12-29 上午9:06:54
     * @param houseid
     * @return
     * @see com.hysm.service.IHouseService#queryHouseById(int)
     */

    @Override
    public House queryHouseById(int houseid)
    {
        House house = houseMapper.queryHouseById(houseid);
        List<Tag> tlist = houseMapper.queryTagById(houseid);// 标签
        List<Image> imagelist = houseMapper.queryImageById(houseid);// 图片
        List<PropertyValue> pvlist = houseMapper.queryPropertyValueById(house.getSku_id());
        List<Price> priceList = houseMapper.queryPriceByHouseid(houseid);// 价格
        List<Supporting> supplist = houseMapper.querySupportingById(houseid);// 配置信息
        house.setSupportinglist(supplist);
        house.setLtag(tlist);
        house.setLimage(imagelist);
        house.setPvaluelist(pvlist);
        house.setPricelist(priceList);

        return house;
    }

    /**
     * @discription 修改房屋基本数据
     * @author 刘正义
     * @created 2015-12-30 上午10:09:36
     * @param house
     * @return
     * @see com.hysm.service.IHouseService#updateHouse(com.hysm.pojo.House)
     */

    @Override
    public int updateHouse(House house)
    {
        int val = houseMapper.updateHouse(house);
        return val;
    }

    /**
     * @discription 修改 house 的配置信息
     * @author 刘正义
     * @created 2015-12-30 下午4:32:52
     * @param houseid
     * @param list
     * @return
     * @see com.hysm.service.IHouseService#updateHouseSupp(int, java.util.List)
     */

    @Override
    public int updateHouseSupp(int houseid, List<Integer> list)
    {
        houseMapper.deleteSuppByHouseId(houseid);
        int result = houseMapper.insertHouseSupp(list, houseid);
        return result;
    }

    /**
     * @discription 修改房屋价格
     * @author 刘正义
     * @created 2015-12-31 上午9:27:57
     * @param price
     * @return
     * @see com.hysm.service.IHouseService#updatePrice(com.hysm.pojo.Price)
     */

    @Override
    public int updatePrice(Price price)
    {
        int val = houseMapper.updatePrice(price);
        return val;
    }

    /**
     * @discription 修改房屋标签
     * @author 刘正义
     * @created 2015-12-31 下午2:09:12
     * @param houseid
     *            房间id
     * @param list
     * @return
     * @see com.hysm.service.IHouseService#updateHouseTag(int, java.util.List)
     */

    @Override
    public int updateHouseTag(int houseid, List<Integer> list)
    {
        houseMapper.deleteHouseTag(houseid);
        int val = houseMapper.insertHouseTag(list, houseid);

        return val;
    }
}
