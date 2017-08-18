/**
 * 
 */
package com.hysm.mapper.sqlpro;

import java.util.List;
import java.util.Map;

/**
 * @author john
 * 
 */
public class NearbyPro
{
    /*
     * public String queryNearby1() {
     * 
     * StringBuffer sql = new StringBuffer(); sql.append(
     * "select ht_id,count(house_id) as num from  h_house a where a.house_id in (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime"
     * ) .append(
     * " union all  select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2 "
     * ) .append(
     * "and  ht_id in ( select ht_id from h_house_type where hotel_id  in ( select id  from m_hotel where GPS_LO<#{map.maxlon}  and GPS_LO>#{map.minlon} and GPS_LA<#{map.maxlat} and GPS_LA>#{map.minlat} and STATE= 1 )  and  state=1  ) group by a.ht_id"
     * );
     * 
     * return sql.toString(); }
     */

    /**
     * @discription 查询周边 几公里以内
     * @author 刘正义
     * @created 2016-1-8 下午5:50:33
     * @return
     */
    public String queryNearby()
    {
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select c.name,c.ht_name ,c.gps_lo,c.gps_la,d.* from   (select  a.name, a.gps_lo,a.gps_la, b.*  from  m_hotel a RIGHT  JOIN  h_house_type b on a.id=b.hotel_id  where a.state=1 and b.state=1 and GPS_LO<#{map.maxlon}  and GPS_LO>#{map.minlon} and GPS_LA<#{map.maxlat} and GPS_LA>#{map.minlat} )  c ")
                .append(" left  join  (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime ")
                .append("  union all select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2) d on c.ht_id= d.ht_id  order by d.ht_id limit #{beginNum},#{ps}");

        return sql.toString();
    }

    public String queryNearbyNum()
    {
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select count(d.ht_id) from   (select  a.name, a.gps_lo,a.gps_la, b.*  from  m_hotel a RIGHT  JOIN  h_house_type b on a.id=b.hotel_id  where a.state=1 and b.state=1 and GPS_LO<#{map.maxlon}  and GPS_LO>#{map.minlon} and GPS_LA<#{map.maxlat} and GPS_LA>#{map.minlat} )  c ")
                .append(" left  join  (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime ")
                .append("  union all select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2) d on c.ht_id= d.ht_id  order by d.ht_id");

        return sql.toString();

    }

    /**
     * @discription 查询周边
     * @author 刘正义
     * @created 2016-1-9 下午2:43:55
     * @return
     */
    public String queryNearbyHouse()
    {
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select c.name,c.ht_name ,c.gps_lo,c.gps_la,c.ranges,d.* from   (select  a.name, a.gps_lo,a.gps_la, b.*, ROUND(6378.138*2*ASIN(SQRT(POW(SIN((#{map.lat}*PI()/180-a.gps_la*PI()/180)/2),2)+COS(#{map.lat}*PI()/180)*COS(a.gps_la*PI()/180)*POW(SIN((#{map.lon}*PI()/180-a.gps_lo*PI()/180)/2),2)))*1000) as ranges  from  m_hotel a RIGHT  JOIN  h_house_type b on a.id=b.hotel_id  where a.state=1 and b.state=1  )  c ")
                .append(" left  join  (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime ")
                .append("  union all select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2  group by ht_id ) d on c.ht_id= d.ht_id  order by c.ranges,d.ht_id limit #{beginNum},#{ps}");

        return sql.toString();

    }

    public String queryNearbyHouseNum()
    {

        StringBuffer sql = new StringBuffer();
        sql.append(
                "select count(d.ht_id) from   (select  a.name, a.gps_lo,a.gps_la, b.*, ROUND(6378.138*2*ASIN(SQRT(POW(SIN((#{map.lat}*PI()/180-a.gps_la*PI()/180)/2),2)+COS(#{map.lat}*PI()/180)*COS(a.gps_la*PI()/180)*POW(SIN((#{map.lon}*PI()/180-a.gps_lo*PI()/180)/2),2)))*1000) as ranges  from  m_hotel a RIGHT  JOIN  h_house_type b on a.id=b.hotel_id  where a.state=1 and b.state=1  )  c ")
                .append(" left  join  (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime ")
                .append("  union all select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2  group by ht_id ) d on c.ht_id= d.ht_id  order by c.ranges,d.ht_id");

        return sql.toString();
    }

    /**
     * @discription 查询图片列表
     * @author 刘正义
     * @created 2016-1-8 下午5:51:27
     * @return
     */
    public String queryImageById()
    {
        String sql = "select * from h_img where ht_id in (  #{ht_ids} ) and state=1 ";

        return sql;
    }

    /**
     * @discription 查询民居价格
     * @author 刘正义
     * @created 2016-1-8 下午5:54:40
     * @return
     */
    public String queryPriceById()
    {

        String sql = "select * from h_price where ht_id in ( #{ht_ids} ) and state =1";

        return sql;
    }

    /**
     * @discription 查询民居的价格和图片
     * @author 刘正义
     * @created 2016-1-9 上午8:56:22
     * @return
     */
    public String queryPriceAndImage(Map<String, String> map)
    {
        String ht_id = map.get("ht_ids");
        String sql = "select a.*, b.* from h_img a , h_price b where a.ht_id=b.ht_id and a.ht_id in ("
                + ht_id + " ) and  a.state=1 and b.state=1 group by a.ht_id";

        return sql;
    }

    /**
     * @discription 根据tag查询房屋
     * @author 刘正义
     * @created 2016-1-11 下午2:11:52
     * @return
     */
    public String queryNearbyTag()
    {

        StringBuffer sql = new StringBuffer();
        sql.append(
                "select c.name,c.ht_name ,c.gps_lo,c.gps_la,c.ranges,d.* from   (select  a.name, a.gps_lo,a.gps_la, b.*, ROUND(6378.138*2*ASIN(SQRT(POW(SIN((#{map.lat}*PI()/180-a.gps_la*PI()/180)/2),2)+COS(#{map.lat}*PI()/180)*COS(a.gps_la*PI()/180)*POW(SIN((#{map.lon}*PI()/180-a.gps_lo*PI()/180)/2),2)))*1000) as ranges  from  m_hotel a right  JOIN  h_house_type b on a.id=b.hotel_id  where a.state=1 and b.state=1  and a.id in (select distinct hotel_id from h_hotel_tag where tag_id=#{tagid} and state= 1)) c ")
                .append(" left  join  (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime ")
                .append("  union all select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2  group by ht_id ) d on c.ht_id= d.ht_id  ")
                .append("  order by c.ranges,d.ht_id limit #{beginNum},#{ps}");

        return sql.toString();

    }

    public String queryNearbyTagNum()
    {

        StringBuffer sql = new StringBuffer();
        sql.append(
                "select count(d.ht_id)  from   (select  a.name, a.gps_lo,a.gps_la, b.*, ROUND(6378.138*2*ASIN(SQRT(POW(SIN((#{map.lat}*PI()/180-a.gps_la*PI()/180)/2),2)+COS(#{map.lat}*PI()/180)*COS(a.gps_la*PI()/180)*POW(SIN((#{map.lon}*PI()/180-a.gps_lo*PI()/180)/2),2)))*1000) as ranges  from  m_hotel a right  JOIN  h_house_type b on a.id=b.hotel_id  where a.state=1 and b.state=1  and a.id in (select distinct hotel_id from h_hotel_tag where tag_id=#{tagid} and state= 1)) c ")
                .append(" left  join  (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime ")
                .append("  union all select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2  group by ht_id ) d on c.ht_id= d.ht_id  ")
                .append("  order by c.ranges,d.ht_id ");

        return sql.toString();

    }

    /**
     * @discription 根据属性查询附近民居
     * @author 刘正义
     * @created 2016-1-12 上午9:18:21
     * @return
     */
    public String queryNearbyPro(Map<String, Object> val)
    {
        List<String> list = (List<String>) val.get("pro");
        String pro = "";
        for (int i = 0; i < list.size(); i++)
        {
            pro = "where c.ht_id in (select ht_id from h_house_type_property where  pro_id in ("
                    + list.get(i) + ",";

        }
        if (!pro.equals(""))
        {

            pro = pro.substring(0, pro.length() - 1);
            pro += "))";
        }
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select c.name,c.ht_name ,c.gps_lo,c.gps_la,c.ranges,d.* from   (select  a.name, a.gps_lo,a.gps_la, b.*, ROUND(6378.138*2*ASIN(SQRT(POW(SIN((#{map.lat}*PI()/180-a.gps_la*PI()/180)/2),2)+COS(#{map.lat}*PI()/180)*COS(a.gps_la*PI()/180)*POW(SIN((#{map.lon}*PI()/180-a.gps_lo*PI()/180)/2),2)))*1000) as ranges  from  m_hotel a right  JOIN  h_house_type b on a.id=b.hotel_id  where a.state=1 and b.state=1  ) c ")
                .append(" left  join  (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime ")
                .append("  union all select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2  group by ht_id ) d on c.ht_id= d.ht_id  ")
                .append(pro)
                .append("  order by c.ranges,d.ht_id limit #{beginNum},#{ps}");

        return sql.toString();

    }

    // 查询数量
    public String queryNearbyProNum(Map<String, Object> val)
    {
        List<String> list = (List<String>) val.get("pro");
        String pro = "";
        for (int i = 0; i < list.size(); i++)
        {
            pro = "where c.ht_id in (select ht_id from h_house_type_property where  pro_id in ("
                    + list.get(i) + ",";

        }
        if (!pro.equals(""))
        {

            pro = pro.substring(0, pro.length() - 1);
            pro += "))";
        }
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select  count(d.ht_id)  from   (select  a.name, a.gps_lo,a.gps_la, b.*, ROUND(6378.138*2*ASIN(SQRT(POW(SIN((#{map.lat}*PI()/180-a.gps_la*PI()/180)/2),2)+COS(#{map.lat}*PI()/180)*COS(a.gps_la*PI()/180)*POW(SIN((#{map.lon}*PI()/180-a.gps_lo*PI()/180)/2),2)))*1000) as ranges  from  m_hotel a right  JOIN  h_house_type b on a.id=b.hotel_id  where a.state=1 and b.state=1  ) c ")
                .append(" left  join  (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime ")
                .append("  union all select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2  group by ht_id ) d on c.ht_id= d.ht_id  ")
                .append(pro).append("  order by c.ranges,d.ht_id ");

        return sql.toString();

    }

    public String queryNearbyProAndTag(Map<String, Object> val)
    {
        List<String> list = (List<String>) val.get("pro");
        String pro = "";
        for (int i = 0; i < list.size(); i++)
        {
            pro = "where c.ht_id in (select ht_id from h_house_type_property where  pro_id in ("
                    + list.get(i) + ",";

        }
        if (!pro.equals(""))
        {

            pro = pro.substring(0, pro.length() - 1);
            pro += "))";
        }
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select c.name,c.ht_name ,c.gps_lo,c.gps_la,c.ranges,d.* from   (select  a.name, a.gps_lo,a.gps_la, b.*, ROUND(6378.138*2*ASIN(SQRT(POW(SIN((#{map.lat}*PI()/180-a.gps_la*PI()/180)/2),2)+COS(#{map.lat}*PI()/180)*COS(a.gps_la*PI()/180)*POW(SIN((#{map.lon}*PI()/180-a.gps_lo*PI()/180)/2),2)))*1000) as ranges  from  m_hotel a right  JOIN  h_house_type b on a.id=b.hotel_id  where a.state=1 and b.state=1 and a.id in (select distinct hotel_id from h_hotel_tag where tag_id=#{tagid} and state= 1) ) c ")
                .append(" left  join  (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime ")
                .append("  union all select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2  group by ht_id ) d on c.ht_id= d.ht_id  ")
                .append(pro)
                .append("  order by c.ranges,d.ht_id limit #{beginNum},#{ps}");

        return sql.toString();

    }

    public String queryNearbyProAndTagNum(Map<String, Object> val)
    {
        List<String> list = (List<String>) val.get("pro");
        String pro = "";
        for (int i = 0; i < list.size(); i++)
        {
            pro = "where c.ht_id in (select ht_id from h_house_type_property where  pro_id in ("
                    + list.get(i) + ",";

        }
        if (!pro.equals(""))
        {

            pro = pro.substring(0, pro.length() - 1);
            pro += "))";
        }
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select count(d.ht_id) from   (select  a.name, a.gps_lo,a.gps_la, b.*, ROUND(6378.138*2*ASIN(SQRT(POW(SIN((#{map.lat}*PI()/180-a.gps_la*PI()/180)/2),2)+COS(#{map.lat}*PI()/180)*COS(a.gps_la*PI()/180)*POW(SIN((#{map.lon}*PI()/180-a.gps_lo*PI()/180)/2),2)))*1000) as ranges  from  m_hotel a right  JOIN  h_house_type b on a.id=b.hotel_id  where a.state=1 and b.state=1 and a.id in (select distinct hotel_id from h_hotel_tag where tag_id=#{tagid} and state= 1) ) c ")
                .append(" left  join  (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime ")
                .append("  union all select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2  group by ht_id ) d on c.ht_id= d.ht_id  ")
                .append(pro).append("  order by c.ranges,d.ht_id");

        return sql.toString();

    }

    /**
     * @discription 查询商品
     * @author 刘正义
     * @created 2016-1-12 下午6:42:30
     * @param map
     * @return
     */
    public String queryNearbyPrice(Map<String, Object> val)
    {

        List<String> list = (List<String>) val.get("pro");
        String pro = "";
        for (int i = 0; i < list.size(); i++)
        {
            pro = "where c.ht_id in (select ht_id from h_house_type_property where  pro_id in ("
                    + list.get(i) + ",";

        }

        String price = (String) val.get("price");
        String pricesql = "";
        if (price.equals("2"))
        {

            pricesql = "  select ht_id from h_price where  single_price<20000  ";
        }
        else if (price.equals("3"))
        {

            pricesql = "  select ht_id from h_price where  single_price>=20000 and single_price<=30000  ";
        }
        else if (price.equals("4"))
        {

            pricesql = " select ht_id from h_price where  single_price>30000 and single_price<=40000  ";

        }
        else if (price.equals("5"))
        {

            pricesql = " select ht_id from h_price where  single_price>40000 and single_price<=50000  ";
        }
        else if (price.equals("6"))
        {

            pricesql = " select ht_id from h_price where  single_price>50000 and single_price<=60000  ";
        }
        else if (price.equals("7"))
        {

            pricesql = "   select  ht_id  from  h_price  where  single_price>60000 and single_price<=80000  ";

        }
        else if (price.equals("8"))
        {

            pricesql = "   select  ht_id  from  h_price  where  single_price>80000";
        }

        String tagid = (String) val.get("tagid");
        String tag = "";
        if (!tagid.equals(""))
        {

            tag = "and a.id in (select distinct hotel_id from h_hotel_tag where tag_id=#{tagid} and state= 1)";

        }

        if (!pro.trim().equals(""))
        {

            pro = pro.substring(0, pro.length() - 1);
            pro += ") union all " + pricesql + ")";
        }
        else
        {
            if (!pricesql.equals(""))
            {
                pro = " where c.ht_id in ( " + pricesql + ")";
            }

        }
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select c.name,c.ht_name ,c.gps_lo,c.gps_la,c.ranges,d.* from   (select  a.name, a.gps_lo,a.gps_la, b.*, ROUND(6378.138*2*ASIN(SQRT(POW(SIN((#{map.lat}*PI()/180-a.gps_la*PI()/180)/2),2)+COS(#{map.lat}*PI()/180)*COS(a.gps_la*PI()/180)*POW(SIN((#{map.lon}*PI()/180-a.gps_lo*PI()/180)/2),2)))*1000) as ranges  from  m_hotel a right  JOIN  h_house_type b on a.id=b.hotel_id  where a.state=1 and b.state=1")
                .append(" ) c ")
                .append(" left  join  (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime ")
                .append("  union all select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2  group by ht_id ) d on c.ht_id= d.ht_id  ")
                .append(pro)
                .append("  order by c.ranges,d.ht_id limit #{beginNum},#{ps}");

        return sql.toString();
    }

    // 查询数量
    public String queryNearbyPriceNum(Map<String, Object> val)
    {

        List<String> list = (List<String>) val.get("pro");
        String pro = "";
        for (int i = 0; i < list.size(); i++)
        {
            pro = "where c.ht_id in (select ht_id from h_house_type_property where  pro_id in ("
                    + list.get(i) + ",";

        }

        String price = (String) val.get("price");
        String pricesql = "";
        if (price.equals("2"))
        {

            pricesql = "  select ht_id from h_price where  single_price<20000  ";
        }
        else if (price.equals("3"))
        {

            pricesql = "  select ht_id from h_price where  single_price>=20000 and single_price<=30000  ";
        }
        else if (price.equals("4"))
        {

            pricesql = " select ht_id from h_price where  single_price>30000 and single_price<=40000  ";

        }
        else if (price.equals("5"))
        {

            pricesql = " select ht_id from h_price where  single_price>40000 and single_price<=50000  ";
        }
        else if (price.equals("6"))
        {

            pricesql = " select ht_id from h_price where  single_price>50000 and single_price<=60000  ";
        }
        else if (price.equals("7"))
        {

            pricesql = "   select  ht_id  from  h_price  where  single_price>60000 and single_price<=80000  ";

        }
        else if (price.equals("8"))
        {

            pricesql = "   select  ht_id  from  h_price  where  single_price>80000";
        }

        String tagid = (String) val.get("tagid");
        String tag = "";
        if (!tagid.equals(""))
        {

            tag = "and a.id in (select distinct hotel_id from h_hotel_tag where tag_id=#{tagid} and state= 1)";

        }

        if (!pro.trim().equals(""))
        {

            pro = pro.substring(0, pro.length() - 1);
            pro += ") union all " + pricesql + ")";
        }
        else
        {
            if (!pricesql.equals(""))
            {
                pro = " where c.ht_id in ( " + pricesql + ")";
            }

        }
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select count(d.ht_id) from   (select  a.name, a.gps_lo,a.gps_la, b.*, ROUND(6378.138*2*ASIN(SQRT(POW(SIN((#{map.lat}*PI()/180-a.gps_la*PI()/180)/2),2)+COS(#{map.lat}*PI()/180)*COS(a.gps_la*PI()/180)*POW(SIN((#{map.lon}*PI()/180-a.gps_lo*PI()/180)/2),2)))*1000) as ranges  from  m_hotel a right  JOIN  h_house_type b on a.id=b.hotel_id  where a.state=1 and b.state=1")
                .append(" ) c ")
                .append(" left  join  (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{times.endtime} < starttime or #{times.begintime}>endtime ")
                .append("  union all select house_id from  h_house WHERE  house_id  not in (select house_id from h_house_schedule )) and  a.house_state=2  group by ht_id ) d on c.ht_id= d.ht_id  ")
                .append(pro).append("  order by c.ranges,d.ht_id ");

        return sql.toString();

    }
}
