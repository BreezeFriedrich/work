/**
 * 
 */
package com.hysm.mapper.sqlpro;

import java.util.Map;

/**
 * @author john
 * 
 */
public class FeaturePro
{

    public String queryAllCharacteristic()
    {

        String sql = "select chara_name,charac_id from h_characteristic where state=1";

        return sql;
    }

    public String queryCharacteristicValue()
    {

        String sql = "select charav_id,charav_name ,chara_id from h_characteristic_value where chara_id=#{chara_id} and state = 1";

        return sql;
    }

    public String queryCharaHouseTypeByCity(Map<String, Object> map)
    {
        String vid = (String) map.get("vid");
        String vidsql = "";
        if (vid != null && !vid.equals(""))
        {

            vidsql = " and charav_id in (" + vid + ")";

        }

        StringBuilder sql = new StringBuilder();

        sql.append(
                "select a.name,a.address, c.*,b.ht_name from m_hotel  a, h_house_type  b,h_domains d, (select ht_id,count(house_id) as num from  h_house a where is_characteristic=1 ")
                .append(vidsql)
                .append(" and  a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{etime} < starttime or #{stime}>endtime ")
                .append("  union all select house_id  from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) group by ht_id) c where a.id=b.hotel_id  and b.ht_id=c.ht_id and d.name=#{city_name} and a.city=d.id ")
                .append(" order BY b.ht_id limit #{beginNum},#{ps}");

        return sql.toString();

    }

    public String queryCharaHouseType(Map<String, Object> map)
    {
        String vid = (String) map.get("vid");
        String vidsql = "";
        if (vid != null && !vid.equals(""))
        {

            vidsql = " and charav_id in (" + vid + ")";

        }

        StringBuilder sql = new StringBuilder();

        sql.append(
                "select a.name,a.address, c.*,b.ht_name from m_hotel  a, h_house_type  b, (select ht_id,count(house_id) as num from  h_house a where is_characteristic=1")
                .append(vidsql)
                .append("and a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{etime} < starttime or #{stime}>endtime ")
                .append("  union all select house_id  from  h_house WHERE   house_id  not in (select house_id from h_house_schedule )) group by ht_id) c where a.id=b.hotel_id  and b.ht_id=c.ht_id  ")
                .append(" order BY b.ht_id limit #{beginNum},#{ps}");

        return sql.toString();

    }

    public String queryCharaHouseTypeByCityNum(Map<String, Object> map)
    {
        String vid = (String) map.get("vid");
        String vidsql = "";
        if (vid != null && !vid.equals(""))
        {

            vidsql = " and charav_id in (" + vid + ")";

        }

        StringBuilder sql = new StringBuilder();

        sql.append(
                "select count(b.ht_id) from m_hotel  a, h_house_type  b,h_domains d, (select ht_id,count(house_id) as num from  h_house a where is_characteristic=1 ")
                .append(vidsql)
                .append(" and a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{etime} < starttime or #{stime}>endtime ")
                .append("  union all select house_id  from  h_house WHERE   house_id  not in (select house_id from h_house_schedule )) group by ht_id) c where a.id=b.hotel_id  and b.ht_id=c.ht_id and d.name=#{city_name} and a.city=d.id ");

        return sql.toString();

    }

    public String queryCharaHouseTypeNum(Map<String, Object> map)
    {

        String vid = (String) map.get("vid");
        String vidsql = "";
        if (vid != null && !vid.equals(""))
        {

            vidsql = " and charav_id in (" + vid + ")";

        }
        StringBuilder sql = new StringBuilder();

        sql.append(
                "select count(b.ht_id) from m_hotel  a, h_house_type  b, (select ht_id,count(house_id) as num from  h_house a where is_characteristic=1 ")
                .append(vidsql)
                .append(" and a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{etime} < starttime or #{stime}>endtime ")
                .append("  union all select house_id  from  h_house WHERE   house_id  not in (select house_id from h_house_schedule )) group by ht_id) c where a.id=b.hotel_id  and b.ht_id=c.ht_id ");

        return sql.toString();

    }
}
