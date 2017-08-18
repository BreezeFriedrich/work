package com.hysm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.hysm.mapper.sqlpro.HousePro;
import com.hysm.pojo.House;
import com.hysm.pojo.Image;
import com.hysm.pojo.Manager;
import com.hysm.pojo.Price;
import com.hysm.pojo.Property;
import com.hysm.pojo.PropertyValue;
import com.hysm.pojo.Sku;
import com.hysm.pojo.Supporting;
import com.hysm.pojo.Tag;

@Repository("houseMapper")
public interface HouseMapper
{

    @SelectProvider(method = "get_user", type = HousePro.class)
    String get_user();

    /**
     * @discription 商户登录
     * @author 刘正义
     * @created 2015-12-26 下午3:31:24
     * @param name
     *            用户名
     * @param pwd
     *            密码
     * @return
     */
    @SelectProvider(method = "login", type = HousePro.class)
    public Manager login(@Param("name")String name, @Param("pwd")String pwd);

    /**
     * @discription 根据城市获取城市id
     * @author 刘正义
     * @created 2015-12-22 下午4:17:57
     * @param city
     * @return
     */
    @SelectProvider(method = "queryCityIdByCityName", type = HousePro.class)
    public int queryCityIdByCityName(String city);

    /**
     * @discription 根据城市id 查询 城市标签
     * @author 刘正义
     * @created 2015-12-22 下午4:23:52
     * @param cityid
     * @return
     */

    @SelectProvider(method = "queryTagListByCityId", type = HousePro.class)
    public List<Tag> queryTagListByCityId(String city);

    /**
     * @discription 查询所有的配套设置
     * @author 刘正义
     * @created 2015-12-23 上午9:30:38
     * @return
     */
    @SelectProvider(method = "queryAllSupporting", type = HousePro.class)
    public List<Supporting> queryAllSupporting();

    /**
     * @discription 查询所有属性值
     * @author 刘正义
     * @created 2015-12-23 上午9:39:41
     * @return
     */
    @SelectProvider(method = "queryAllProperty", type = HousePro.class)
    public List<Property> queryAllProperty();

    /**
     * @discription 根据属性id查询属性值
     * @author 刘正义
     * @created 2015-12-23 上午10:02:16
     * @param propertyid
     * @return
     */
    @SelectProvider(method = "queryPropertyValueByPropertyId", type = HousePro.class)
    public List<PropertyValue> queryPropertyValueByPropertyId(int propertyid);

    /**
     * @discription 添加民居
     * @author 刘正义
     * @created 2015-12-24 下午2:45:03
     * @param house
     * @return
     */
    @InsertProvider(method = "addHouse", type = HousePro.class)
    @Options(useGeneratedKeys = true, keyProperty = "house_id")
    public int insertHouse(@Param("house")
    House house);

    /**
     * @discription 添加sku
     * @author 刘正义
     * @created 2015-12-24 下午2:45:20
     * @return
     */
    @InsertProvider(method = "addSku", type = HousePro.class)
    @Options(useGeneratedKeys = true, keyProperty = "sku_id")
    public int insertSku(Sku sku);

    /**
     * @discription 民居添加标签
     * @author 刘正义
     * @created 2015-12-26 上午10:01:01
     * @param tags
     *            标签id
     * @param houseid
     * @return
     */

    @InsertProvider(method = "addHouseTag", type = HousePro.class)
    public int insertHouseTag(@Param("tags")
    List<Integer> tags, @Param("houseid")
    int houseid);

    /**
     * @discription 添加可输入民居的属性值
     * @author 刘正义
     * @created 2015-12-26 上午11:21:58
     * @param provalue
     * @param skuid
     * @return
     */
    @InsertProvider(method = "addPropertyValue", type = HousePro.class)
    @Options(useGeneratedKeys = true, keyProperty = "v_id")
    public int insertPropertyValue(PropertyValue provalue);

    /**
     * @discription 添加sku与属性的关联
     * @author 刘正义
     * @created 2015-12-26 下午1:57:25
     * @param pro
     * @param skuid
     * @return
     */
    @InsertProvider(method = "insertSkuPro", type = HousePro.class)
    public int insertSkuPro(@Param("pro")
    List<Integer> pro, @Param("skuid")
    int skuid);

    /**
     * @discription 添加房间配套设置
     * @author 刘正义
     * @created 2015-12-29 下午3:25:19
     * @param list
     * @param houseid
     * @return
     */
    @InsertProvider(method = "insertHouseSupp", type = HousePro.class)
    public int insertHouseSupp(@Param("list")
    List<Integer> list, @Param("houseid")
    int houseid);

    /**
     * @discription 给房间添加图片
     * @author 刘正义
     * @created 2015-12-28 上午11:31:38
     * @param list
     *            图片s
     * @param houseid
     *            房间id
     * @return
     */
    @InsertProvider(method = "insertImage", type = HousePro.class)
    public int insertImage(@Param("list")
    List<String> list, @Param("houseid")
    int houseid);

    /**
     * @discription 添加价格
     * @author 刘正义
     * @created 2015-12-28 下午3:23:01
     * @param price
     * @return
     */
    @InsertProvider(method = "insertPrice", type = HousePro.class)
    @Options(useGeneratedKeys = true, keyProperty = "price_id")
    public int insertPrice(Price price);

    /**
     * @discription 修改房间信息
     * @author 刘正义
     * @created 2015-12-28 下午4:34:26
     * @param house
     * @return
     */
    @UpdateProvider(method = "updateHouse", type = HousePro.class)
    public int updateHouse(@Param("house")
    House house);

    /**
     * @discription 查询房间信息
     * @author 刘正义
     * @created 2015-12-28 下午5:41:00
     * @param merchantsid
     * @param beginNum
     * @param ps
     * @return
     */
    @SelectProvider(method = "queryHouse", type = HousePro.class)
    public List<House> queryHouse(@Param("merchantsid")int merchantsid, @Param("beginNum")int beginNum, @Param("ps")int ps);
    
    @SelectProvider(method = "queryHouse1", type = HousePro.class)
    public List<House> queryHouse1(@Param("merchantsid")int merchantsid, @Param("beginNum")int beginNum, @Param("ps")int ps,
    		@Param("stime")String stime,@Param("etime")String etime);


    /**
     * @discription 查询商品数量
     * @author 刘正义
     * @created 2015-12-28 下午5:55:29
     * @param merchantsid
     * @return
     */
    @SelectProvider(method = "queryHouseNum", type = HousePro.class)
    public int queryHouseNum(@Param("merchantsid")
    int merchantsid);

    /**
     * @discription 根据house id 查询 house
     * @author 刘正义
     * @created 2015-12-29 上午9:09:20
     * @param houseid
     * @return
     */
    @SelectProvider(method = "queryHouseById", type = HousePro.class)
    public House queryHouseById(int houseid);

    /**
     * @discription 根据skuid 查询 propertyvalue
     * @author 刘正义
     * @created 2015-12-29 上午9:29:56
     * @param houseid
     * @return
     */
    @SelectProvider(method = "queryPropertyValueById", type = HousePro.class)
    public List<PropertyValue> queryPropertyValueById(int skuid);

    /**
     * @discription 根据 houseid 查询 image
     * @author 刘正义
     * @created 2015-12-29 上午9:32:24
     * @param houseid
     * @return
     */
    @SelectProvider(method = "queryImageById", type = HousePro.class)
    public List<Image> queryImageById(int houseid);

    /**
     * @discription 根据shopid 查询 Tag
     * @author 刘正义
     * @created 2015-12-29 上午10:04:01
     * @param houseid
     * @return
     */
    @SelectProvider(method = "queryTagById", type = HousePro.class)
    public List<Tag> queryTagById(int houseid);

    /**
     * @discription 根据houseid 查询 价格
     * @author 刘正义
     * @created 2015-12-29 上午10:15:49
     * @param houseid
     * @return
     */
    @SelectProvider(method = "queryPriceByHouseid", type = HousePro.class)
    public List<Price> queryPriceByHouseid(int houseid);

    /**
     * @discription 查询配置信息
     * @author 刘正义
     * @created 2015-12-29 下午4:41:18
     * @param houseid
     * @return
     */
    @SelectProvider(method = "querySupportingById", type = HousePro.class)
    public List<Supporting> querySupportingById(int houseid);

    /**
     * @discription 根据skuid 删除房屋属性数据
     * @author 刘正义
     * @created 2015-12-30 下午3:15:09
     * @param houseid
     * @return
     */
    @DeleteProvider(method = "DeleteProValueBySkuId", type = HousePro.class)
    public int deleteProValueBySkuId(int skuid);

    /**
     * @discription 删除 房屋配置信息
     * @author 刘正义
     * @created 2015-12-30 下午4:12:41
     * @param houseid
     * @return
     */
    @DeleteProvider(method = "deleteSuppByHouseId", type = HousePro.class)
    public int deleteSuppByHouseId(int houseid);

    /**
     * @discription 修改价格
     * @author 刘正义
     * @created 2015-12-31 上午9:08:41
     * @param price
     * @return
     */
    @UpdateProvider(method = "updatePrice", type = HousePro.class)
    public int updatePrice(@Param("price")
    Price price);

    /**
     * @discription 根据房屋id 的删除房屋标签
     * @author 刘正义
     * @created 2015-12-31 下午2:12:45
     * @param houseid
     * @return
     */
    @DeleteProvider(method = "deleteHouseTag", type = HousePro.class)
    public int deleteHouseTag(int houseid);
}
