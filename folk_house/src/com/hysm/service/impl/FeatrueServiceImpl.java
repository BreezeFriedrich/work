/**
 * 
 */
package com.hysm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.domain.Characteristic;
import com.hysm.domain.CharacteristicValue;
import com.hysm.domain.HouseType;
import com.hysm.domain.Price;
import com.hysm.mapper.FeatrueMapper;
import com.hysm.mapper.NearbyMapper;
import com.hysm.pojo.PageBean;
import com.hysm.service.IFeatrueService;

/**
 * 特色民居
 * 
 * @author john
 * 
 */
@Service("featrue")
public class FeatrueServiceImpl implements IFeatrueService
{

    @Autowired
    private FeatrueMapper featruemapper;

    @Autowired
    private NearbyMapper nearbymapper;

    /*
     * (non-Javadoc) 查询 特色民居
     * 
     * @see com.hysm.service.IFeatrueService#queryFeatrue(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, int)
     */
    @Override
    public PageBean<HouseType> queryFeatrue(String chara_id, String city_name,
            String stime, String etime, int pn)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();
        int ps = 10;
        int beginNum = (pn - 1) * ps;
        List<Characteristic> charlist = featruemapper.queryAllCharacteristic();

        if (chara_id == null || chara_id.equals(""))
        {

            chara_id = charlist.get(0).getCharac_id() + "";
        }
        List<CharacteristicValue> vlist = featruemapper
                .queryCharacteristicValue(chara_id);
        List<HouseType> housetype = null;

        StringBuilder sb = new StringBuilder();
        sb.append("");
        for (CharacteristicValue value : vlist)
        {

            sb.append(value.getCharav_id()).append(",");

        }
        if (sb.length() > 2)
        {

            sb.setLength(sb.length() - 1);
        }

        int rn = 0;

        if (city_name == null || city_name.equals(""))
        {

            housetype = featruemapper.queryCharaHouseType(sb.toString(), stime,
                    etime, beginNum, ps);

            rn = featruemapper.queryCharaHouseTypeNum(sb.toString(), stime,
                    etime);
        }
        else
        {
            housetype = featruemapper.queryCharaHouseTypeByCity(sb.toString(),
                    city_name, stime, etime, beginNum, ps);

            rn = featruemapper.queryCharaHouseTypeByCityNum(sb.toString(),
                    city_name, stime, etime);
        }

        pb.setPageNum(pn);

        pb.setPageSize(ps);
        pb.setRowCount(rn);
        List<Price> plist = null;
        Map<String, Object> houseandprice = new HashMap<String, Object>();
        if (housetype.size() > 0)
        {
            StringBuffer ht_ids = new StringBuffer();

            for (HouseType house : housetype)
            {
                ht_ids.append("'").append(house.getHt_id()).append("'")
                        .append(",");
            }
            ht_ids.setLength(ht_ids.length() - 1);

            plist = nearbymapper.queryPriceAndImage(ht_ids.toString());
            pb.setList(housetype);

        }
        houseandprice.put("price", plist);
        houseandprice.put("charlist", charlist);
        houseandprice.put("vlist", vlist);

        pb.setMap(houseandprice);

        return pb;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.hysm.service.IFeatrueService#queryHouseTypeByfeatid(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, int)
     */
    @Override
    public PageBean<HouseType> queryHouseTypeByfeatid(String featureid,
            String city_name, String stime, String etime, int pn)
    {
        PageBean<HouseType> pb = new PageBean<HouseType>();
        int ps = 10;
        int beginNum = (pn - 1) * ps;
        List<HouseType> housetype = null;
        int rn = 0;
        if (city_name == null || city_name.equals(""))
        {

            housetype = featruemapper.queryCharaHouseType(featureid, stime,
                    etime, beginNum, ps);

            rn = featruemapper.queryCharaHouseTypeNum(featureid, stime, etime);
        }
        else
        {
            housetype = featruemapper.queryCharaHouseTypeByCity(featureid,
                    city_name, stime, etime, beginNum, ps);

            rn = featruemapper.queryCharaHouseTypeByCityNum(
                    featureid.toString(), city_name, stime, etime);
        }

        pb.setPageNum(pn);

        pb.setPageSize(ps);
        pb.setRowCount(rn);
        List<Price> plist = null;
        Map<String, Object> houseandprice = new HashMap<String, Object>();
        if (housetype.size() > 0)
        {
            StringBuffer ht_ids = new StringBuffer();

            for (HouseType house : housetype)
            {
                ht_ids.append("'").append(house.getHt_id()).append("'")
                        .append(",");
            }
            ht_ids.setLength(ht_ids.length() - 1);

            plist = nearbymapper.queryPriceAndImage(ht_ids.toString());
            pb.setList(housetype);

        }
        houseandprice.put("price", plist);
        pb.setMap(houseandprice);
        return pb;
    }
}
