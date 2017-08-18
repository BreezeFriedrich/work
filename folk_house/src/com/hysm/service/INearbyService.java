/**
 * 
 */
package com.hysm.service;

import java.util.List;
import java.util.Map;

import com.hysm.domain.HouseType;
import com.hysm.pojo.PageBean;

/**
 * @author john
 * 
 */
public interface INearbyService
{
    public PageBean<HouseType> queryNearby(int pn, double longitude,
            double latitude, Map<String, String> times);

    /**
     * @discription 根据标签查询民居
     * @author 刘正义
     * @created 2016-1-11 下午2:24:08
     * @param longitude
     * @param latitude
     * @param times
     * @param tagid
     * @return
     */
    public PageBean<HouseType> queryNearbyTag(int pn, double longitude,
            double latitude, Map<String, String> times, String tagid);

    /**
     * @discription 根据属性查询民居
     * @author 刘正义
     * @created 2016-1-12 上午9:07:34
     * @param longitude
     * @param latitude
     * @param times
     *            日期
     * @param pro
     *            属性
     * @return
     */
    public PageBean<HouseType> queryNearbyPro(int pn, double longitude,
            double latitude, Map<String, String> times, List<String> pro);

    /**
     * @discription 根据属性和标签查询民居
     * @author 刘正义
     * @created 2016-1-12 上午11:25:38
     * @param longitude
     * @param latitude
     * @param times
     * @param pro
     * @param tagid
     * @return
     */
    public PageBean<HouseType> queryNearbyProAndTag(int pn, double longitude,
            double latitude, Map<String, String> times, List<String> pro,
            String tagid);

    /**
     * @discription 根据民居价格查询民居
     * @author 刘正义
     * @created 2016-1-12 下午5:19:28
     * @param longitude
     * @param latitude
     * @param times
     *            时间段
     * @param pro
     *            属性
     * @param tagid
     *            标签id
     * @param price
     *            价格
     * @return 商品价格 和 信息
     */
    public PageBean<HouseType> queryNearbyPrice(int pn, double longitude,
            double latitude, Map<String, String> times, List<String> pro,
            String tagid, String price);

}
