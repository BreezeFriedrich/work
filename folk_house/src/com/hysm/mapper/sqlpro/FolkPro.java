/**
 * 
 */
package com.hysm.mapper.sqlpro;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SqlBuilder;

import com.hysm.domain.House;
import com.hysm.domain.HouseType;
import com.hysm.domain.Img;

/**
 * @author john
 * 
 */
public class FolkPro
{

    // 添加房屋价格
    public String savePrice()
    {

        String sql = "insert into h_price (pricing,single_price,week_price,month_price,three_price,five_price,state,price_ctime,manager_id,ht_id)"
                + " values (#{pricing},#{single_price},#{single_price}-#{week_price},#{single_price}-#{month_price},#{single_price}-#{three_price},#{single_price}-#{five_price},1,#{price_ctime},#{manager_id},#{ht_id})";

        return sql;
    }

    // 添加房屋类型
    public String saveHouseType()
    {

        String sql = "insert into  h_house_type(ht_name,state,hotel_id,num) values (#{ht_name},1,#{hotel_id},#{num})";

        return sql;
    }

    // 添加房屋的图片
    public String saveImg(Map<String, Object> map)
    {
        List<String> list = (List<String>) map.get("imgs");

        StringBuffer sb = new StringBuffer();
        sb.append(" insert into h_img(ht_id,img_url,state ) values");

        MessageFormat mf1 = new MessageFormat(" #'{'imgs[{0}]}");
        for (int i = 0; i < list.size(); i++)
        {
            sb.append("(#{ht_id},");
            sb.append(mf1.format(new Integer[]
            { i }));
            sb.append(",1),");

        }
        sb.setLength(sb.length() - 1);

        return sb.toString();
    }

    // 查询属性
    public String queryPro()
    {

        String sql = "select *  from  h_property  where  state =1  order  by   pro_id";

        return sql;

    }

    // 查询属性值

    public String queryProValue()
    {

        String sql = "select * from  h_property_value where  pro_id=#{proid} and  state=1";

        return sql;
    }

    // 根据宾馆id查询 民居类型
    public String queryHouseType()
    {

        String sql = "select * from h_house_type where hotel_id=#{hotelid}  limit  #{beginNum}, #{ps}";

        return sql;
    }

    public String queryHousePrice()
    {

        String sql = "select  *  from  h_price a left join (select * from  h_holiday_price where state=1 ) b on a.ht_id=b.ht_id where a.ht_id=#{htid}";

        return sql;
    }

    // 查询类型
    public String queryHouseTypeByhtid()
    {

        String sql = "select   *   from  h_house_type  where   ht_id=#{htid}";

        return sql;
    }

    // 查询 图片

    public String queryImgByid()
    {

        String sql = "select    *   from   h_img  where  ht_id=#{htid}  and  state=1";

        return sql;
    }

    // 查询同一宾馆的房型名字是否重复
    public String queryechoHouseType()
    {

        String sql = "select count(ht_id) from h_house_type where hotel_id=#{hotel_id}  and  ht_name=#{ht_name}";

        return sql;
    }

    // 修改房型
    public String alertHouseTypeByid(Map<String, Object> map)
    {
        HouseType housetype = (HouseType) map.get("housetype");

        String sql = "update  h_house_type  set ";
        if (housetype.getHt_name() != null
                && !housetype.getHt_name().equals(""))
        {
            sql += "ht_name=#{housetype.ht_name},";
        }
        sql += " num=#{housetype.num} where  ht_id=#{housetype.ht_id}";

        return sql;
    }

    // 添加房型下的房间
    public String saveHouse(Map<String, Object> map)
    {
        House house = (House) map.get("house");
        SqlBuilder.BEGIN();
        SqlBuilder.INSERT_INTO("h_house");

        if (house.getHouse_num() != null)
        {

            SqlBuilder.VALUES("house_num", "#{house.house_num}");
        }

        if (house.getKeynum() != null)
        {

            SqlBuilder.VALUES("keynum", "#{house.keynum}");
        }

        if (house.getIs_choice() != 0)
        {

            SqlBuilder.VALUES("is_choice", "#{house.is_choice}");
        }

        if (house.getIs_characteristic() != 0)
        {
            SqlBuilder
                    .VALUES("is_characteristic", "#{house.is_characteristic}");

        }
        if (house.getHt_id() != 0)
        {
            SqlBuilder.VALUES("ht_id", "#{house.ht_id}");

        }
        if (house.getHouse_ctime() != null)
        {

            SqlBuilder.VALUES("house_ctime", "#{house.house_ctime}");
        }

        if (house.getManager_id() != 0)
        {

            SqlBuilder.VALUES("manager_id", "#{house.manager_id}");
        }
        if (house.getCharav_id() != 0)
        {

            SqlBuilder.VALUES("charav_id", "#{house.charav_id}");
        }

        return SqlBuilder.SQL();
    }

    // 查询宾馆下的房型下的房间
    public String queryHouseByHtid()
    {

        String sql = "select  a.*, b.MANGERNAME  from  h_house a left join m_hotel_managers b on a.manager_id=b.id  where ht_id=#{htid}";

        return sql;
    }

    // 根据id修改房间信息
    public String alertHouseById()
    {
        String sql = "update h_house set house_num=#{house.house_num},keyNum=#{house.keynum},is_characteristic=#{house.is_characteristic},is_choice=#{house.is_choice},charav_id=#{house.charav_id}  where house_Id=#{house.house_id}";

        return sql;

    }

    /**
     * @discription 根据房间id 查询房间日程
     * @author 刘正义
     * @created 2016-3-8 下午7:42:57
     * @return
     */
    public String querySchedule()
    {

        String sql = "select a.*,b.house_num from h_house_schedule a ,h_house b  where  a.house_id=b.house_id and b.house_id=#{house_id} order by ENDTIME desc ";

        return sql;
    }

    /**
     * @discription 查询宾馆所有房间
     * @author 刘正义
     * @created 2016-3-8 下午7:58:31
     * @return
     */
    public String queryHouseAll()
    {
        String sql = "select *  from h_house where ht_id in ( select ht_id  from  h_house_type where hotel_id=#{hotel_id}  ) ";

        return sql;
    }

}
