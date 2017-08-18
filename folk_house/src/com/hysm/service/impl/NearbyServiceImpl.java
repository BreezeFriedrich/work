/**
 * 
 */
package com.hysm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.domain.HouseType;
import com.hysm.domain.Price;
import com.hysm.mapper.NearbyMapper;
import com.hysm.pojo.PageBean;

import com.hysm.service.INearbyService;
import com.hysm.util.Range;

/**
 * @author john
 * 
 */
@Service("nearbyservice")
public class NearbyServiceImpl implements INearbyService
{

    @Autowired
    NearbyMapper nearbymapper;

    /**
     * @discription 查询附近公寓 先查询附近3公里以内，没有在查询6公里以内
     * @author 刘正义
     * @created 2016-1-7 下午4:40:43
     * @param longitude
     *            经度
     * @param latitude
     *            纬度
     * @param times
     *            开始时间 和 结束时间
     * 
     * @return
     * 
     */

    @Override
    public PageBean<HouseType> queryNearby(int pn, double longitude,
            double latitude, Map<String, String> times)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();
        int ps = 10;
        int beginNum = (pn - 1) * ps;
        Map<String, double[]> map = Range.returnLLSquarePoint(longitude,
                latitude, 10);

        Map<String, Double> map1 = new HashMap<String, Double>();
        map1.put("lon", longitude);
        map1.put("lat", latitude);

        pb.setPageSize(ps);

        List<HouseType> housetype = nearbymapper.queryNearbyHouse(map1, times,
                beginNum, ps);
        int rowCount = nearbymapper.queryNearbyHouseNum(map1, times);

        pb.setRowCount(rowCount);

        StringBuffer ht_ids = new StringBuffer();

        if (housetype.size() > 0)
        {
            for (HouseType house : housetype)
            {
                ht_ids.append("'").append(house.getHt_id()).append("'")
                        .append(",");

            }
            ht_ids.setLength(ht_ids.length() - 1);

            Map<String, Object> house = new HashMap<String, Object>();

            List<Price> plist = nearbymapper.queryPriceAndImage(ht_ids
                    .toString());

            house.put("price", plist);

            pb.setList(housetype);
            pb.setMap(house);
        }
        return pb;
    }

    /**
     * @discription 根据标签查询民居
     * @author 刘正义
     * @created 2016-1-11 下午2:24:49
     * @param longitude
     * @param latitude
     * @param times
     * @param tagid
     * @return
     * @see com.hysm.service.INearbyService#queryNearbyTag(double, double,
     *      java.util.Map, java.lang.String)
     */

    @Override
    public PageBean<HouseType> queryNearbyTag(int pn, double longitude,
            double latitude, Map<String, String> times, String tagid)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();
        int ps = 10;
        int beginNum = (pn - 1) * ps;
        pb.setPageSize(ps);
        Map<String, Double> map1 = new HashMap<String, Double>();
        map1.put("lon", longitude);
        map1.put("lat", latitude);
        List<HouseType> housetype = nearbymapper.queryNearbyTag(map1, times,
                tagid, beginNum, ps);
        int rowCount = nearbymapper.queryNearbyTagNum(map1, times, tagid);
        pb.setRowCount(rowCount);

        Map<String, Object> houseandprice = null;
        List<Price> plist = null;
        if (housetype.size() > 0)
        {
            StringBuffer ht_ids = new StringBuffer();

            for (HouseType house : housetype)
            {
                ht_ids.append("'").append(house.getHt_id()).append("'")
                        .append(",");

            }
            ht_ids.setLength(ht_ids.length() - 1);

            houseandprice = new HashMap<String, Object>();

            plist = nearbymapper.queryPriceAndImage(ht_ids.toString());
            pb.setList(housetype);
            houseandprice.put("price", plist);
            pb.setMap(houseandprice);
        }

        return pb;

    }

    /**
     * @discription 根据属性查询附近民居
     * @author 刘正义
     * @created 2016-1-12 上午9:11:16
     * @param longitude
     * @param latitude
     * @param times
     * @param pro
     * @return
     * @see com.hysm.service.INearbyService#queryNearbyPro(double, double,
     *      java.util.Map, java.util.List)
     */

    @Override
    public PageBean<HouseType> queryNearbyPro(int pn, double longitude,
            double latitude, Map<String, String> times, List<String> pro)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();
        int ps = 10;
        int beginNum = (pn - 1) * ps;
        Map<String, Double> map1 = new HashMap<String, Double>();
        pb.setPageSize(ps);

        map1.put("lon", longitude);
        map1.put("lat", latitude);
        List<HouseType> housetype = nearbymapper.queryNearbyPro(map1, times,
                pro, beginNum, ps);//
        Map<String, Object> houseandprice = null;
        List<Price> plist = null;
        if (housetype.size() > 0)
        {

            int rowCount = nearbymapper.queryNearbyProNum(map1, times, pro);
            pb.setRowCount(rowCount);

            StringBuffer ht_ids = new StringBuffer();

            for (HouseType house : housetype)
            {
                ht_ids.append("'").append(house.getHt_id()).append("'")
                        .append(",");

            }
            ht_ids.setLength(ht_ids.length() - 1);

            houseandprice = new HashMap<String, Object>();

            plist = nearbymapper.queryPriceAndImage(ht_ids.toString());

            pb.setList(housetype);
            houseandprice.put("price", plist);
            pb.setMap(houseandprice);
        }

        return pb;
    }

    /**
     * @discription 在此输入一句话描述作用
     * @author 刘正义
     * @created 2016-1-12 上午11:26:46
     * @param longitude
     * @param latitude
     * @param times
     * @param pro
     * @param tagid
     * @return
     * @see com.hysm.service.INearbyService#queryNearbyProAndTag(double, double,
     *      java.util.Map, java.util.List, java.lang.String)
     */

    @Override
    public PageBean<HouseType> queryNearbyProAndTag(int pn, double longitude,
            double latitude, Map<String, String> times, List<String> pro,
            String tagid)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();

        int ps = 10;
        int beginNum = (pn - 1) * ps;
        pb.setPageSize(ps);
        Map<String, Double> map1 = new HashMap<String, Double>();
        map1.put("lon", longitude);
        map1.put("lat", latitude);
        List<HouseType> housetype = nearbymapper.queryNearbyProAndTag(map1,
                times, pro, tagid, beginNum, ps); //
        Map<String, Object> houseandprice = null;
        List<Price> plist = null;
        if (housetype.size() > 0)
        {

            int rowCount = nearbymapper.queryNearbyProAndTagNum(map1, times,
                    pro, tagid);
            pb.setRowCount(rowCount);

            StringBuffer ht_ids = new StringBuffer();

            for (HouseType house : housetype)
            {
                ht_ids.append("'").append(house.getHt_id()).append("'")
                        .append(",");

            }
            ht_ids.setLength(ht_ids.length() - 1);

            houseandprice = new HashMap<String, Object>();

            plist = nearbymapper.queryPriceAndImage(ht_ids.toString());
            pb.setList(housetype);

            houseandprice.put("price", plist);
            pb.setMap(houseandprice);
        }

        return pb;
    }

    /**
     * @discription 在此输入一句话描述作用
     * @author 刘正义
     * @created 2016-1-12 下午5:19:15
     * @param longitude
     * @param latitude
     * @param times
     * @param pro
     * @param tagid
     * @param price
     * @return
     * @see com.hysm.service.INearbyService#queryNearbyPrice(double, double,
     *      java.util.Map, java.util.List, java.lang.String, java.lang.String)
     */

    @Override
    public PageBean<HouseType> queryNearbyPrice(int pn, double longitude,
            double latitude, Map<String, String> times, List<String> pro,
            String tagid, String price)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();
        int ps = 10;
        int beginNum = (pn - 1) * ps;
        pb.setPageSize(ps);
        Map<String, Double> map1 = new HashMap<String, Double>();
        map1.put("lon", longitude);
        map1.put("lat", latitude);

        List<HouseType> housetype = nearbymapper.queryNearbyPrice(map1, times,
                pro, tagid, price, beginNum, ps); //
        Map<String, Object> houseandprice = null;
        List<Price> plist = null;
        if (housetype.size() > 0)
        {
            int rowCount = nearbymapper.queryNearbyPriceNum(map1, times, pro,
                    tagid, price);
            pb.setRowCount(rowCount);
            StringBuffer ht_ids = new StringBuffer();

            for (HouseType house : housetype)
            {
                ht_ids.append("'").append(house.getHt_id()).append("'")
                        .append(",");

            }
            ht_ids.setLength(ht_ids.length() - 1);

            houseandprice = new HashMap<String, Object>();

            plist = nearbymapper.queryPriceAndImage(ht_ids.toString());
            pb.setList(housetype);
            houseandprice.put("price", plist);
            pb.setMap(houseandprice);
        }

        return pb;
    }

}
