/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author   刘正义         
 * @created 2016-1-13 上午9:37:01    
 * tags     
 * see_to_target     
 */

package com.hysm.mapper.sqlpro;

import java.util.List;
import java.util.Map;

/**
 * @author john
 * 
 */
public class WeekPro
{

    public String queryIndexWeek(Map<String, Object> map)
    {
        String city_name = (String) map.get("city_name");
        String stime = (String) map.get("stime");
        String etime = (String) map.get("etime");

        String sql = " SELECT  a.ht_id,b.ht_name, COUNT(a.house_id) as num,c.* ";
        sql += " FROM h_house a,h_house_type b,m_hotel c ";
        sql += " WHERE a.house_id IN (SELECT  house_id FROM h_house_schedule WHERE";
        sql += "  '" + etime + "' < endtime   OR '" + stime
                + "' >starttime  union all  ";
        sql += " (SELECT  house_id FROM h_house WHERE  house_id NOT IN (SELECT   house_id FROM  h_house_schedule))) ";
        sql += " AND a.house_state = 2 ";
        sql += " AND a.ht_id IN (SELECT  ht_id FROM  h_house_type ";
        sql += " WHERE hotel_id IN (SELECT a.id ";
        sql += " FROM  m_hotel a,h_tag b,h_hotel_tag c,h_domains d ";
        sql += " WHERE a.city=d.id ";
        if (city_name != null && !city_name.equals(""))
        {
            sql += " and d.name='" + city_name + "'";
        }
        sql += " and a.id=c.hotel_id and b.tag_id=c.tag_id ";

        sql += " ) AND state = 1) ";
        sql += " and a.ht_id=b.ht_id and b.hotel_id=c.id ";
        sql += " GROUP BY a.ht_id desc";
        sql += " limit #{beginNum},#{ps} ";
        return sql;
    }

    public String queryWeekByCity(Map<String, Object> map)
    {
        String price = (String) map.get("price");

        String pricesql = this.buliderPrice(price);

        StringBuilder sql = new StringBuilder();

        sql.append(
                "select a.name,a.address, c.*,b.ht_name from m_hotel  a, h_house_type  b,h_domains d, (select house_state,ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{etime} < starttime or #{stime}>endtime or STATE<>1")
                .append("  union all select house_id  from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) group by ht_id) c where a.id=b.hotel_id  and b.ht_id=c.ht_id and d.name=#{city_name} and a.city=d.id and c.house_state=2 ")
                .append(pricesql)
                .append(" order BY b.ht_id desc limit #{beginNum},#{ps}");

        return sql.toString();
    }

    public String queryWeekByCityNum(Map<String, Object> map)
    {
        String price = (String) map.get("price");

        String pricesql = this.buliderPrice(price);

        StringBuilder sql = new StringBuilder();

        sql.append(
                "select count(b.ht_id) from m_hotel  a, h_house_type  b,h_domains d, (select house_state,ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{etime} < starttime or #{stime}>endtime or STATE<>1")
                .append("  union all select house_id  from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) group by ht_id) c where a.id=b.hotel_id  and b.ht_id=c.ht_id and d.name=#{city_name} and a.city=d.id and c.house_state=2 ")
                .append(pricesql);

        return sql.toString();

    }

    public String queryWeekByTagAndPriceAndPro(Map<String, Object> map)
    {
        String price = (String) map.get("price");
        int tagid = (Integer) map.get("tagid");
        String pro = (String) map.get("pro");
        String tagsql = "";

        if (tagid != 0)
        {
            tagsql = " and a.id in (select hotel_id from h_hotel_tag where tag_id=#{tagid} and state=1) ";

        }
        String prosql = "";
        if (pro != null && !pro.equals(""))
        {

            prosql = " and b.ht_id in (select ht_id from h_house_type_property where pro_id in(#{pro}))";

        }

        String pricesql = this.buliderPrice(price);

        StringBuilder sql = new StringBuilder();

        sql.append(
                "select a.name,a.address, c.*,b.ht_name from m_hotel  a, h_house_type  b,h_domains d, (select house_state,ht_id,count(house_id) as num from  h_house a where a.house_id not in ")
                .append(" (select distinct(house_id) from h_house_schedule where #{etime} <= endtime and #{stime}>=starttime and STATE in(-1,1))")
                .append("   group by ht_id) c where a.id=b.hotel_id  and b.ht_id=c.ht_id and d.name=#{city_name} and a.city=d.id and c.house_state=2 ")
                .append(pricesql).append(prosql).append(tagsql)
                .append(" order BY b.ht_id limit #{beginNum},#{ps}");
        return sql.toString();
    }

    public String queryWeekByTagAndPriceAndProNum(Map<String, Object> map)
    {

        String price = (String) map.get("price");
        int tagid = (Integer) map.get("tagid");
        String pro = (String) map.get("pro");
        String tagsql = "";

        if (tagid != 0)
        {
            tagsql = " and a.id in (select hotel_id from h_hotel_tag where tag_id=#{tagid} and state=1) ";

        }
        String prosql = "";

        System.out.println(pro + "pro");
        if (pro != null && !pro.equals(""))
        {

            prosql = " and b.ht_id in (select ht_id from h_house_type_property where pro_id in(#{pro}))";

        }

        String pricesql = this.buliderPrice(price);

        StringBuilder sql = new StringBuilder();

        sql.append(
                "select count(b.ht_id) from m_hotel  a, h_house_type  b,h_domains d, (select house_state,ht_id,count(house_id) as num from  h_house a where a.house_id not in ")
                .append(" (select distinct(house_id) from h_house_schedule where #{etime} <=endtime and #{stime}>=starttime and STATE in(-1,1))")
                .append("   group by ht_id) c where a.id=b.hotel_id  and b.ht_id=c.ht_id and d.name=#{city_name} and a.city=d.id and c.house_state=2 ")
                .append(pricesql).append(prosql).append(tagsql);
        return sql.toString();

    }

    private String buliderPrice(String price)
    {

        String pricesql = "";

        if (price == null)
        {

            return "";
        }

        if (price.equals("2"))
        {

            pricesql = " and b.ht_id in (  select ht_id from h_price where  single_price<20000 ) ";
        }
        else if (price.equals("3"))
        {

            pricesql = " and b.ht_id in( select ht_id from h_price where  single_price>=20000 and single_price<=30000 ) ";
        }
        else if (price.equals("4"))
        {

            pricesql = " and b.ht_id in( select ht_id from h_price where  single_price>30000 and single_price<=40000 )  ";

        }
        else if (price.equals("5"))
        {

            pricesql = " and b.ht_id in( select ht_id from h_price where  single_price>40000 and single_price<=50000 ) ";
        }
        else if (price.equals("6"))
        {

            pricesql = " and b.ht_id in( select ht_id from h_price where  single_price>50000 and single_price<=60000 ) ";
        }
        else if (price.equals("7"))
        {

            pricesql = " and b.ht_id in(  select  ht_id  from  h_price  where  single_price>60000 and single_price<=80000 ) ";

        }
        else if (price.equals("8"))
        {

            pricesql = " and b.ht_id in(   select  ht_id  from  h_price  where  single_price>80000)";
        }
        return pricesql;
    }
}
