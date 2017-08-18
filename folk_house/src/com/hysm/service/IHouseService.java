package com.hysm.service;

import java.util.List;

import com.hysm.pojo.House;
import com.hysm.pojo.Manager;
import com.hysm.pojo.PageBean;
import com.hysm.pojo.Price;
import com.hysm.pojo.Property;
import com.hysm.pojo.PropertyValue;
import com.hysm.pojo.Sku;
import com.hysm.pojo.Supporting;
import com.hysm.pojo.Tag;

public interface IHouseService
{

    String get_user();

    /**
     * @discription 用户登录
     * @author 刘正义
     * @created 2015-12-26 下午3:45:08
     * @param name
     *            用户名
     * @param pwd
     *            密码
     * @return
     */
    public Manager login(String name, String pwd);

    /**
     * @discription 根据城市查询标签
     * @author 刘正义
     * @created 2015-12-26 下午3:45:26
     * @param city
     *            城市
     * @return
     */
    public List<Tag> queryTagListByCity(String city);

    /**
     * @discription 查询属性
     * @author 刘正义
     * @created 2015-12-23 上午10:08:16
     * @return
     */
    public List<Property> queryAllProperty();

    /**
     * @discription 查询配置
     * @author 刘正义
     * @created 2015-12-23 上午10:17:36
     * @return
     */
    public List<Supporting> queryAllSupporting();

    /**
     * @discription 添加民居
     * @author 刘正义
     * @created 2015-12-28 上午11:28:11
     * @param house
     * @param sku
     * @param tags
     * @param pro
     * @param provalue
     * @return
     */
    public int insertHouse(House house, Sku sku, List<Integer> tags,
            List<Integer> pro, List<PropertyValue> provalue,
            List<Integer> suppor);

    /**
     * @discription 给民居添加图片
     * @author 刘正义
     * @created 2015-12-28 上午11:29:20
     * @param list
     * @param houseid
     * @return
     */
    public int insertHouseImage(List<String> list, int houseid);

    /**
     * @discription 添加民居价格
     * @author 刘正义
     * @created 2015-12-28 下午3:33:34
     * @param price
     * @param houseid
     * @return
     */
    public int insertPrice(Price price, int houseid);

    /**
     * @discription 分页查询house * @author 刘正义
     * @created 2015-12-28 下午5:45:56
     * @param merchantsid
     * @param pn
     * @param etime 
     * @param stime 
     * @return
     */
    public PageBean<House> queryHouse(int merchantsid, int pn);
    
    public PageBean<House> queryHouse1(int merchantsid, int pn, String stime, String etime);

    /**
     * @discription 根据id 查询 house
     * @author 刘正义
     * @created 2015-12-29 上午9:06:02
     * @param houseid
     * @return
     */
    public House queryHouseById(int houseid);

    /**
     * @discription 修改房屋基本数据
     * @author 刘正义
     * @created 2015-12-30 上午10:08:59
     * @param house
     * @return
     */
    public int updateHouse(House house);

    /**
     * @discription 修改房屋
     * @author 刘正义
     * @created 2015-12-30 下午4:26:57
     * @param houseid
     * @param list
     * @return
     */
    public int updateHouseSupp(int houseid, List<Integer> list);

    /**
     * @discription 修改价格
     * @author 刘正义
     * @created 2015-12-31 上午9:27:29
     * @param price
     * @return
     */
    public int updatePrice(Price price);

    /**
     * @discription 修改房屋标签
     * @author 刘正义
     * @created 2015-12-31 下午2:08:34
     * @param houseid
     * @param list
     * @return
     */
    public int updateHouseTag(int houseid, List<Integer> list);

}
