/**
 * 
 */
package com.hysm.mapper.sqlpro;

/**
 * @author john
 * 
 */
public class ChoicenessPro
{
    public String queryChoicenessIndex()
    {
        StringBuilder sql = new StringBuilder();

        sql.append(
                "select a.name,a.address, c.*,b.ht_name from m_hotel  a, h_house_type  b,h_domains d, (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{etime} < starttime or #{stime}>endtime ")
                .append("  union all select house_id  from  h_house WHERE is_choice=1 and  house_id  not in (select house_id from h_house_schedule )) group by ht_id) c where a.id=b.hotel_id  and b.ht_id=c.ht_id and d.name=#{city_name} and a.city=d.id ")
                .append(" order BY b.ht_id  limit #{beginNum},#{ps}");

        return sql.toString();

    }

    public String queryChoicenessIndexNum()
    {
        StringBuilder sql = new StringBuilder();

        sql.append(
                "select count(b.ht_id) from m_hotel  a, h_house_type  b,h_domains d, (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{etime} < starttime or #{stime}>endtime ")
                .append("  union all select house_id  from  h_house WHERE is_choice=1 and  house_id  not in (select house_id from h_house_schedule )) group by ht_id) c where a.id=b.hotel_id  and b.ht_id=c.ht_id and d.name=#{city_name} and a.city=d.id ");

        return sql.toString();

    }

    public String queryChoicenessByPro()
    {

        StringBuilder sql = new StringBuilder();

        sql.append(
                "select a.name,a.address, c.*,b.ht_name from m_hotel  a, h_house_type  b,h_domains d, (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{etime} < starttime or #{stime}>endtime ")
                .append("  union all select house_id  from  h_house WHERE is_choice=1 and  house_id  not in (select house_id from h_house_schedule )) group by ht_id) c where a.id=b.hotel_id  and b.ht_id=c.ht_id and d.name=#{city_name} and a.city=d.id ")
                .append("and b.ht_id in (select ht_id from h_house_type_property where pro_id in(#{pro}))")
                .append(" order BY b.ht_id limit #{beginNum},#{ps}");

        return sql.toString();

    }

    public String queryChoicenessByProNum()
    {

        StringBuilder sql = new StringBuilder();

        sql.append(
                "select count(b.ht_id) from m_hotel  a, h_house_type  b,h_domains d, (select ht_id,count(house_id) as num from  h_house a where a.house_id in ")
                .append(" (select house_id from h_house_schedule where #{etime} < starttime or #{stime}>endtime ")
                .append("  union all select house_id  from  h_house WHERE is_choice=1 and  house_id  not in (select house_id from h_house_schedule )) group by ht_id) c where a.id=b.hotel_id  and b.ht_id=c.ht_id and d.name=#{city_name} and a.city=d.id ")
                .append("and b.ht_id in (select ht_id from h_house_type_property where pro_id in(#{pro}))");

        return sql.toString();

    }
}
