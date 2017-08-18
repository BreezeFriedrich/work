/**
 * 附近公寓
 */
package com.hysm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.hysm.domain.HouseType;
import com.hysm.domain.Img;
import com.hysm.domain.Price;
import com.hysm.mapper.sqlpro.NearbyPro;
import com.hysm.pojo.House;

public interface NearbyMapper
{

    /**
     * @discription 在此输入一句话描述作用
     * @author 刘正义
     * @created 2016-1-8 上午10:02:11
     * @param map
     *            经纬度
     * @param times
     *            日期
     * @return
     */
    @SelectProvider(method = "queryNearby", type = NearbyPro.class)
    public List<HouseType> queryNearby(@Param("map")
    Map<String, Double> map, @Param("times")
    Map<String, String> times, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps);

    /**
     * @discription 查询数量
     * @author 刘正义
     * @created 2016-1-21 下午12:01:31
     * @param map
     * @param times
     * @return
     */
    @SelectProvider(method = "queryNearbyNum", type = NearbyPro.class)
    public int queryNearbyNum(@Param("map")
    Map<String, Double> map, @Param("times")
    Map<String, String> times);

    /**
     * @discription 在此输入一句话描述作用
     * @author 刘正义
     * @created 2016-1-8 上午10:02:11
     * @param map
     *            经纬度
     * @param times
     *            日期
     * @return
     */
    @SelectProvider(method = "queryNearbyHouse", type = NearbyPro.class)
    public List<HouseType> queryNearbyHouse(@Param("map")
    Map<String, Double> map, @Param("times")
    Map<String, String> times, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps);

    /**
     * @discription 查询数量
     * @author 刘正义
     * @created 2016-1-21 上午11:59:49
     * @param map
     * @param times
     * @return
     */
    @SelectProvider(method = "queryNearbyHouseNum", type = NearbyPro.class)
    public int queryNearbyHouseNum(@Param("map")
    Map<String, Double> map, @Param("times")
    Map<String, String> times);

    /**
     * @discription 根据tag 查询民居
     * @author 刘正义
     * @created 2016-1-11 下午2:21:15
     * @param map
     * @param times
     * @return
     */
    @SelectProvider(method = "queryNearbyTag", type = NearbyPro.class)
    public List<HouseType> queryNearbyTag(@Param("map")
    Map<String, Double> map, @Param("times")
    Map<String, String> times, @Param("tagid")
    String tagid, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps);

    /**
     * @discription 查询数量
     * @author 刘正义
     * @created 2016-1-21 上午11:58:15
     * @param map
     * @param times
     * @param tagid
     * @return
     */
    @SelectProvider(method = "queryNearbyTagNum", type = NearbyPro.class)
    public int queryNearbyTagNum(@Param("map")
    Map<String, Double> map, @Param("times")
    Map<String, String> times, @Param("tagid")
    String tagid);

    /**
     * @discription 查询商品图片
     * @author 刘正义
     * @created 2016-1-8 下午5:34:27
     * @param ht_ids
     * @return
     */
    @SelectProvider(method = "queryImageById", type = NearbyPro.class)
    public List<Img> queryImageById(String ht_ids);

    /**
     * @discription 查询商品的价格
     * @author 刘正义
     * @created 2016-1-9 上午9:02:15
     * @param ht_ids
     * 
     * @return
     */
    @SelectProvider(method = "queryPriceById", type = NearbyPro.class)
    public List<Price> queryPriceById(String ht_ids);

    /**
     * @discription 查询房屋的价格和图片
     * @author 刘正义
     * @created 2016-1-9 上午11:39:09
     * @param ht_ids
     * @return
     */
    @SelectProvider(method = "queryPriceAndImage", type = NearbyPro.class)
    public List<Price> queryPriceAndImage(@Param("ht_ids")
    String ht_ids);

    /**
     * @discription 根据属性查询附近民居
     * @author 刘正义
     * @created 2016-1-12 上午9:15:36
     * @param map
     *            坐标
     * @param times
     *            日期
     * @param pro
     *            属性
     * @return
     */
    @SelectProvider(method = "queryNearbyPro", type = NearbyPro.class)
    public List<HouseType> queryNearbyPro(@Param("map")
    Map<String, Double> map, @Param("times")
    Map<String, String> times, @Param("pro")
    List<String> pro, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps);

    /**
     * @discription 查询数量
     * @author 刘正义
     * @created 2016-1-21 下午1:45:58
     * @param map
     * @param times
     * @param pro
     * @return
     */
    @SelectProvider(method = "queryNearbyProNum", type = NearbyPro.class)
    public int queryNearbyProNum(@Param("map")
    Map<String, Double> map, @Param("times")
    Map<String, String> times, @Param("pro")
    List<String> pro);

    /**
     * @discription 根据属性和标签查询民居
     * @author 刘正义
     * @created 2016-1-12 上午11:29:39
     * @param map
     *            地理位置
     * @param times
     *            日期
     * @param pro
     *            属性组
     * @param tagid
     *            标签
     * @return
     */
    @SelectProvider(method = "queryNearbyProAndTag", type = NearbyPro.class)
    public List<HouseType> queryNearbyProAndTag(@Param("map")
    Map<String, Double> map, @Param("times")
    Map<String, String> times, @Param("pro")
    List<String> pro, @Param("tagid")
    String tagid, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps);

    /**
     * @discription 查询数量
     * @author 刘正义
     * @created 2016-1-21 下午1:41:36
     * @param map
     * @param times
     * @param pro
     * @param tagid
     * @return
     */
    @SelectProvider(method = "queryNearbyProAndTagNum", type = NearbyPro.class)
    public int queryNearbyProAndTagNum(@Param("map")
    Map<String, Double> map, @Param("times")
    Map<String, String> times, @Param("pro")
    List<String> pro, @Param("tagid")
    String tagid);

    /**
     * @discription 根据价格查询民居
     * @author 刘正义
     * @created 2016-1-12 下午5:43:57
     * @return
     */
    @SelectProvider(method = "queryNearbyPrice", type = NearbyPro.class)
    public List<HouseType> queryNearbyPrice(@Param("map")
    Map<String, Double> map, @Param("times")
    Map<String, String> times, @Param("pro")
    List<String> pro, @Param("tagid")
    String tagid, @Param("price")
    String price, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps);

    /**
     * @discription 查询数量
     * @author 刘正义
     * @created 2016-1-21 下午1:38:00
     * @param map
     * @param times
     * @param pro
     * @param tagid
     * @param price
     * @return
     */
    @SelectProvider(method = "queryNearbyPriceNum", type = NearbyPro.class)
    public int queryNearbyPriceNum(@Param("map")
    Map<String, Double> map, @Param("times")
    Map<String, String> times, @Param("pro")
    List<String> pro, @Param("tagid")
    String tagid, @Param("price")
    String price);
}
