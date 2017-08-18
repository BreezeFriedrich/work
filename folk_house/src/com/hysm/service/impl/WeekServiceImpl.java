/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author   刘正义         
 * @created 2016-1-13 上午9:55:14    
 * tags     
 * see_to_target     
 */

package com.hysm.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.domain.HouseType;
import com.hysm.domain.Price;
import com.hysm.mapper.NearbyMapper;
import com.hysm.mapper.WeekMapper;
import com.hysm.pojo.PageBean;
import com.hysm.service.IWeekService;

/**
 * @author john
 * 
 */
@Service("weekservice")
public class WeekServiceImpl implements IWeekService
{

    @Autowired
    private WeekMapper weekmapper;
    @Autowired
    private NearbyMapper nearbymapper;

    /**
     * @discription 查询周租月租页面
     * @author 刘正义
     * @created 2016-1-13 上午9:55:14
     * @param pn
     *            页码
     * @param city_name
     * @param stime
     * @param etime
     * @return
     * @see com.hysm.service.IWeekService#queryIndexWeek(int, java.lang.String,
     *      java.lang.String, java.lang.String)
     */

    @Override
    public PageBean<HouseType> queryIndexWeek(int pn, String city_name,
            String stime, String etime)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();
        int ps = 10;
        pb.setPageSize(ps);
        int beginNum = (pn - 1) * ps;
        List<HouseType> list = weekmapper.queryIndexWeek(city_name, stime,
                etime, beginNum, ps);
        List<Price> plist = null;
        Map<String, Object> houseandprice = null;
        if (list.size() > 0)
        {
            StringBuffer ht_ids = new StringBuffer();
            for (HouseType house : list)
            {
                ht_ids.append("'").append(house.getHt_id()).append("'")
                        .append(",");
            }
            ht_ids.setLength(ht_ids.length() - 1);
            houseandprice = new HashMap<String, Object>();
            plist = nearbymapper.queryPriceAndImage(ht_ids.toString());
            pb.setList(list);
            houseandprice.put("price", plist);
            pb.setMap(houseandprice);

        }
        return pb;
    }

    @Override
    public PageBean<HouseType> queryWeekByCity(int pn, String city_name,
            String stime, String etime, String price)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();
        int ps = 10;
        pb.setPageSize(ps);
        int beginNum = (pn - 1) * ps;
        int count = weekmapper.queryWeekByCityNum(city_name, stime, etime,
                price);

        pb.setRowCount(count);

        List<HouseType> list = weekmapper.queryWeekByCity(city_name, stime,
                etime, beginNum, ps, price);
        List<Price> plist = null;
        Map<String, Object> houseandprice = null;
        if (list.size() > 0)
        {
            StringBuffer ht_ids = new StringBuffer();
            for (HouseType house : list)
            {
                ht_ids.append("'").append(house.getHt_id()).append("'")
                        .append(",");
            }
            ht_ids.setLength(ht_ids.length() - 1);
            houseandprice = new HashMap<String, Object>();
            plist = nearbymapper.queryPriceAndImage(ht_ids.toString());
            pb.setList(list);
            houseandprice.put("price", plist);
            pb.setMap(houseandprice);

        }

        pb.setList(list);
        return pb;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.hysm.service.IWeekService#queryWeekByTag(int, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, int,
     * java.util.List)
     */
    @Override
    public PageBean<HouseType> queryWeekByTag(int pn, String city_name,
            String stime, String etime, String price, int tagid,
            List<String> pro)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();
        int ps = 10;
        pb.setPageSize(ps);
        int beginNum = (pn - 1) * 10;

        StringBuilder str = new StringBuilder();
        str.append("");
        if (pro.size() > 0)
        {
            for (String string : pro)
            {

                str.append(string).append(",");

            }
            str.setLength(str.length() - 1);
        }
        int rowpage = weekmapper.queryWeekByTagAndPriceAndProNum(price, tagid,
                str.toString(), city_name, stime, etime);

        pb.setRowCount(rowpage);
        List<HouseType> list = weekmapper
                .queryWeekByTagAndPriceAndPro(ps, beginNum, price, tagid,
                        str.toString(), city_name, stime, etime);

        List<Price> plist = null;
        Map<String, Object> houseandprice = null;
        if (list.size() > 0)
        {
            StringBuffer ht_ids = new StringBuffer();
            for (HouseType house : list)
            {
                ht_ids.append("'").append(house.getHt_id()).append("'")
                        .append(",");
            }
            ht_ids.setLength(ht_ids.length() - 1);
            houseandprice = new HashMap<String, Object>();
            plist = nearbymapper.queryPriceAndImage(ht_ids.toString());
            pb.setList(list);
            houseandprice.put("price", plist);
            pb.setMap(houseandprice);

        }

        pb.setList(list);
        return pb;

    }

    /**
     * @discription 在此输入一句话描述作用
     * @author 刘正义
     * @created 2016-1-21 下午6:07:07
     * @param pn
     * @param city_name
     * @param stime
     * @param etime
     * @param tagid
     * @return
     * @see com.hysm.service.IWeekService#countey_search(int, java.lang.String,
     *      java.lang.String, java.lang.String, int)
     */

    @Override
    public PageBean<HouseType> countey_search(int pn, String city_name,
            String stime, String etime, int tagid)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();
        int ps = 10;
        int beginNum = (pn - 1) * ps;
        pb.setPageSize(ps);
        List<HouseType> list = weekmapper.queryWeekByTagAndPriceAndPro(ps,
                beginNum, 0 + "", tagid, null, city_name, stime, etime);
        List<Price> plist = null;
        Map<String, Object> houseandprice = null;
        if (list.size() > 0)
        {
            int rs = weekmapper.queryWeekByTagAndPriceAndProNum(null, tagid,
                    null, city_name, stime, etime);

            pb.setRowCount(rs);
            StringBuffer ht_ids = new StringBuffer();
            for (HouseType house : list)
            {
                ht_ids.append("'").append(house.getHt_id()).append("'")
                        .append(",");
            }
            ht_ids.setLength(ht_ids.length() - 1);
            houseandprice = new HashMap<String, Object>();
            plist = nearbymapper.queryPriceAndImage(ht_ids.toString());
            pb.setList(list);
            houseandprice.put("price", plist);
            pb.setMap(houseandprice);
        }
        return pb;
    }
}