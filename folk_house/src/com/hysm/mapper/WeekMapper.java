/**     
 * @discription 周租月租
 * @author   刘正义         
 * @created 2016-1-13 上午9:34:16    
 * tags     
 * see_to_target     
 */

package com.hysm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.hysm.domain.HouseType;
import com.hysm.mapper.sqlpro.WeekPro;

public interface WeekMapper
{

    /**
     * @discription 周租月租首页
     * @author 刘正义
     * @created 2016-1-13 上午9:37:56
     * @return
     */
    @SelectProvider(method = "queryIndexWeek", type = WeekPro.class)
    public List<HouseType> queryIndexWeek(@Param("city_name")
    String city_name, @Param("stime")
    String stime, @Param("etime")
    String etime, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps);

    /**
     * @discription 根据城市和价格查询 周租和月租
     * @author 刘正义
     * @created 2016-1-13 下午6:04:28
     * @param city_name
     * @param stime
     * @param etime
     * @param beginNum
     * @param ps
     * @param price
     * @return
     */
    @SelectProvider(method = "queryWeekByCity", type = WeekPro.class)
    public List<HouseType> queryWeekByCity(@Param("city_name")
    String city_name, @Param("stime")
    String stime, @Param("etime")
    String etime, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps, @Param("price")
    String price);

    @SelectProvider(method = "queryWeekByCityNum", type = WeekPro.class)
    public int queryWeekByCityNum(@Param("city_name")
    String city_name, @Param("stime")
    String stime, @Param("etime")
    String etime, @Param("price")
    String price);

    /**
     * 多条件查询周租月租
     * 
     * @param ps
     * @param beginNum
     * @return
     */
    @SelectProvider(method = "queryWeekByTagAndPriceAndPro", type = WeekPro.class)
    public List<HouseType> queryWeekByTagAndPriceAndPro(@Param("ps")
    int ps, @Param("beginNum")
    int beginNum, @Param("price")
    String price, @Param("tagid")
    int tagid, @Param("pro")
    String pro, @Param("city_name")
    String city_name, @Param("stime")
    String stime, @Param("etime")
    String etime);

    /**
     * 查询数量
     * 
     * @param price
     *            价格区间
     * @param tagid
     *            标签id
     * @param pro
     *            属性
     * @param city_name
     *            城市
     * @param stime
     *            开始时间
     * @param etime
     *            结束时间
     * @return
     */
    @SelectProvider(method = "queryWeekByTagAndPriceAndProNum", type = WeekPro.class)
    public int queryWeekByTagAndPriceAndProNum(@Param("price")
    String price, @Param("tagid")
    int tagid, @Param("pro")
    String pro, @Param("city_name")
    String city_name, @Param("stime")
    String stime, @Param("etime")
    String etime);

}
