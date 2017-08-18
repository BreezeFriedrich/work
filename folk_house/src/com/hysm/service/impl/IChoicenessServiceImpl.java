/**
 * 精选推荐
 */
package com.hysm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.domain.HouseType;
import com.hysm.domain.Price;
import com.hysm.mapper.ChoicenessMapper;
import com.hysm.mapper.NearbyMapper;
import com.hysm.pojo.PageBean;
import com.hysm.service.IChoicenessService;

/**
 * @author john
 * 
 */
@Service("choiceness")
public class IChoicenessServiceImpl implements IChoicenessService
{

    @Autowired
    private ChoicenessMapper choicemapper;

    @Autowired
    private NearbyMapper nearbymapper;

    @Override
    public PageBean<HouseType> queryChoicenessIndex(int pn, String city_name,
            String stime, String etime)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();
        int ps = 10;
        int beginNum = (pn - 1) * ps;
        List<HouseType> list = choicemapper.queryChoicenessIndex(city_name,
                stime, etime, beginNum, ps);
        int rn = choicemapper.queryChoicenessIndexNum(city_name, stime, etime);
        pb.setPageNum(pn);

        pb.setPageSize(ps);
        pb.setRowCount(rn);

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
     * @discription 按属性查询民居
     * @author 刘正义
     * @created 2016-1-21 上午9:49:27
     * @param pn
     *            页码
     * @param city_name
     *            城市
     * @param stime
     *            开始时间
     * @param etime
     *            结束时间
     * @param pro
     *            属性
     * @return
     * @see com.hysm.service.IChoicenessService#queryChoicenessByPro(int,
     *      java.lang.String, java.lang.String, java.lang.String,
     *      java.lang.String)
     */

    @Override
    public PageBean<HouseType> queryChoicenessByPro(int pn, String city_name,
            String stime, String etime, String pro)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();
        int ps = 10;
        int beginNum = (pn - 1) * ps;
        pb.setPageSize(ps);
        List<HouseType> house = null;
        int rn = 0;
        if (pro == null || "".equals(pro))
        {
            house = choicemapper.queryChoicenessIndex(city_name, stime, etime,
                    beginNum, ps);
            rn = choicemapper.queryChoicenessIndexNum(city_name, stime, etime);
        }
        else
        {
            house = choicemapper.queryChoicenessByPro(city_name, stime, etime,
                    pro, beginNum, ps);
            rn = choicemapper.queryChoicenessByProNum(city_name, stime, etime,
                    pro);

        }
        System.out.println(rn + "总数量");
        pb.setRowCount(rn);
        List<Price> plist = null;
        Map<String, Object> houseandprice = null;

        if (house.size() > 0)
        {
            StringBuffer ht_ids = new StringBuffer();
            for (HouseType housetype : house)
            {
                ht_ids.append("'").append(housetype.getHt_id()).append("'")
                        .append(",");
            }
            ht_ids.setLength(ht_ids.length() - 1);
            houseandprice = new HashMap<String, Object>();
            plist = nearbymapper.queryPriceAndImage(ht_ids.toString());
            pb.setList(house);
            houseandprice.put("price", plist);
            pb.setMap(houseandprice);

        }
        return pb;
    }
}
