/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author   刘正义         
 * @created 2016-1-13 上午9:51:24    
 * tags     
 * see_to_target     
 */

package com.hysm.service;

import java.util.List;

import com.hysm.domain.HouseType;
import com.hysm.pojo.PageBean;

/**
 * @author john
 * 
 */
public interface IWeekService
{

    /**
     * @discription 查询周租月租首页数据
     * @author 刘正义
     * @created 2016-1-13 上午9:53:32
     * @param pn
     * @param city_name
     * @param stime
     * @param etime
     * @return
     */
    public PageBean<HouseType> queryIndexWeek(int pn, String city_name,
            String stime, String etime);

    /**
     * 根据城市查询周租月租
     * 
     * @param pn
     * @param city_name
     * @param stime
     * @param etime
     * @param price
     * @return
     */
    public PageBean<HouseType> queryWeekByCity(int pn, String city_name,
            String stime, String etime, String price);

    /**
     * 根据标签查询周租月租
     * 
     * @param pn
     *            页码
     * @param city_name
     *            城市
     * @param stime
     *            开始时间
     * @param etime
     *            离店时间
     * @param price
     *            价格区间
     * @param tagid
     *            标签
     * @param pro
     *            属性
     * @return
     */
    public PageBean<HouseType> queryWeekByTag(int pn, String city_name,
            String stime, String etime, String price, int tagid,
            List<String> pro);

    public PageBean<HouseType> countey_search(int pn, String city_name,
            String stime, String etime, int tagid);
}
