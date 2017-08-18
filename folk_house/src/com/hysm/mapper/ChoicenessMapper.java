/**
精选特惠
 * 
 */
package com.hysm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.context.annotation.Primary;

import com.hysm.domain.HouseType;
import com.hysm.mapper.sqlpro.ChoicenessPro;

/**
 * @author john
 * 
 */
public interface ChoicenessMapper
{

    /**
     * 按城市查询精选
     * 
     * @param city_name
     * @param stime
     * @param etime
     * @return
     */
    @SelectProvider(method = "queryChoicenessIndex", type = ChoicenessPro.class)
    public List<HouseType> queryChoicenessIndex(@Param("city_name")
    String city_name, @Param("stime")
    String stime, @Param("etime")
    String etime, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps);

    /**
     * 按城市查询精选数量
     * 
     * @param city_name
     * @param stime
     * @param etime
     * @return
     */
    @SelectProvider(method = "queryChoicenessIndexNum", type = ChoicenessPro.class)
    public int queryChoicenessIndexNum(@Param("city_name")
    String city_name, @Param("stime")
    String stime, @Param("etime")
    String etime);

    /**
     * @discription 查询商品的精选推荐按属性
     * @author 刘正义
     * @created 2016-1-21 上午9:35:11
     * @param city_name
     *            城市
     * @param stime
     *            入住时间
     * @param etime
     *            结束时间
     * @param pro
     *            属性
     * @param beginNum
     *            开始条数
     * @param ps
     *            查询条数
     * @return
     */
    @SelectProvider(method = "queryChoicenessByPro", type = ChoicenessPro.class)
    public List<HouseType> queryChoicenessByPro(@Param("city_name")
    String city_name, @Param("stime")
    String stime, @Param("etime")
    String etime, @Param("pro")
    String pro, @Param("beginNum")
    int beginNum, @Param("ps")
    int ps);

    /**
     * @discription 查询按属性民居数量
     * @author 刘正义
     * @created 2016-1-21 上午9:40:07
     * @param city_name
     * @param stime
     * @param etime
     * @param pro
     * @return
     */
    @SelectProvider(method = "queryChoicenessByProNum", type = ChoicenessPro.class)
    public int queryChoicenessByProNum(@Param("city_name")
    String city_name, @Param("stime")
    String stime, @Param("etime")
    String etime, @Param("pro")
    String pro);
}
