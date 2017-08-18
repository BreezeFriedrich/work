/**
 * 
 */
package com.hysm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hysm.domain.HouseType;

import com.hysm.domain.Characteristic;
import com.hysm.domain.CharacteristicValue;
import com.hysm.domain.House;
import com.hysm.domain.Img;
import com.hysm.domain.Price;
import com.hysm.domain.Property;
import com.hysm.domain.PropertyValue;
import com.hysm.domain.Schedule;
import com.hysm.mapper.sqlpro.FolkPro;

/**
 * @author john
 * 
 */
public interface FolkMapper
{

    // 添加房屋价格
    @InsertProvider(method = "savePrice", type = FolkPro.class)
    @Options(useGeneratedKeys = true, keyProperty = "price_id")
    public int savePrice(Price price);

    // 添加房屋的类型
    @InsertProvider(method = "saveHouseType", type = FolkPro.class)
    @Options(useGeneratedKeys = true, keyProperty = "ht_id")
    int saveHouseType(HouseType housetype);

    // 添加房屋图片
    @InsertProvider(method = "saveImg", type = FolkPro.class)
    int saveImg(@Param("imgs")
    List<String> imgs, @Param("ht_id")
    int ht_id);

    // 查询商品的属性
    @SelectProvider(method = "queryPro", type = FolkPro.class)
    List<Property> queryPro();

    // 查询属性值

    @SelectProvider(method = "queryProValue", type = FolkPro.class)
    List<PropertyValue> queryProValue(@Param("proid")
    int proid);

    // 根据宾馆id查询民居
    @SelectProvider(method = "queryHouseType", type = FolkPro.class)
    List<HouseType> queryHouseType(@Param("hotelid")
    int hotelid, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps);

    // 根据宾馆id 查询房屋数量
    @Select("select count(ht_id) from h_house_type  where  hotel_id=#{hotelid}")
    int queryHotelCount(int hotelid);

    // 根据名句的id查询价格
    @SelectProvider(method = "queryHousePrice", type = FolkPro.class)
    Price queryHousePrice(String htid);

    // 上架
    @Update("update h_house_type set state=1    where ht_id=#{htid} ")
    int upHouseType(String htid);

    // 下架
    @Update("update  h_house_type  set state=2   where  ht_id=#{htid}")
    int downHouseType(String htid);

    // 查询商品的类型
    @SelectProvider(method = "queryHouseTypeByhtid", type = FolkPro.class)
    HouseType queryHouseTypeByhtid(String htid);

    // 查询图片
    @SelectProvider(method = "queryImgByid", type = FolkPro.class)
    public List<Img> queryImgByid(String htid);

    @Update("update h_img set state=3 where img_id=#{imgid}")
    public int deletImg(String imgid);

    // 查询同一宾馆房间类型是否重复
    @SelectProvider(method = "queryechoHouseType", type = FolkPro.class)
    int queryechoHouseType(@Param("hotel_id")
    int hotel_id, @Param("ht_name")
    String ht_name);

    // 修改房型
    @UpdateProvider(method = "alertHouseTypeByid", type = FolkPro.class)
    int alertHouseTypeByid(@Param("housetype")
    HouseType housetype);

    // 添加房型下房间
    @UpdateProvider(method = "saveHouse", type = FolkPro.class)
    int saveHouse(@Param("house")
    House house);

    // 查询特色名称
    @Select("select * from h_characteristic where state=1")
    List<Characteristic> queryCharac();

    // 查询特色值
    @Select("select  * from  h_characteristic_value where  chara_id =#{charac_id}  and state=1")
    List<CharacteristicValue> queryCharacValue(@Param("charac_id")
    int charac_id);

    // 查询宾馆的房型下的房间
    @SelectProvider(method = "queryHouseByHtid", type = FolkPro.class)
    public List<House> queryHouseByHtid(String htid);

    // 查询特色值
    @Select("select charav_name from  h_characteristic_value where charav_id=#{charav_id}")
    public String queryCharacValueOne(@Param("charav_id")
    int charav_id);

    // 下架商品
    @Update("update h_house set house_state=5  where house_id=#{house_id}")
    public int downHouse(String house_id);

    // 上架商品
    @Update("update  h_house set  house_state=2  where  house_id=#{house_id}")
    public int upHouse(String house_id);

    // 根据id查询房间的信息
    @Select("select * from  h_house  where  house_id=#{hid} ")
    public House queryHouseById(String hid);

    // 根据id修改房间的信息
    @UpdateProvider(method = "alertHouseById", type = FolkPro.class)
    public int alertHouseById(@Param("house")
    House house);

    /**
     * @discription 查询日程表
     * @author 刘正义
     * @created 2016-3-8 下午7:49:03
     * @param house_id
     *            房间id beginNum 开始查询位置
     * 
     * @return
     */
    @SelectProvider(method = "querySchedule", type = FolkPro.class)
    public List<Schedule> querySchedule(@Param("house_id")
    String house_id);

    /**
     * @discription 查询宾馆下的所有房间
     * @author 刘正义
     * @created 2016-3-8 下午8:02:12
     * @param hotel_id
     *            宾馆id
     * @return
     */
    @SelectProvider(method = "queryHouseAll", type = FolkPro.class)
    public List<House> queryHouseAll(int hotel_id);

}
