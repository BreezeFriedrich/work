package com.hysm.mapper.sqlpro;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.swing.text.MaskFormatter;

import org.apache.ibatis.jdbc.SqlBuilder;

import com.hysm.pojo.House;
import com.hysm.pojo.Price;
import com.hysm.pojo.PropertyValue;

public class HousePro
{

    public String get_user()
    {
        String sql = "select username from user ";
        return sql;
    }

    /**
     * @discription 店铺登录
     * @author 刘正义
     * @created 2015-12-26 下午3:27:20
     * @return
     */
    public String login()
    {
        String sql = "select * from h_manager where name=#{name} and pwd=#{pwd} and Manager_state=1";
        return sql;
    }

    /**
     * @discription 生成根据城市查询 城市 id
     * @author 刘正义
     * @created 2015-12-22 下午4:17:22
     * @return
     */
    public String queryCityIdByCityName()
    {
        String sql = "select id from  h_domains where name=#{city}";
        return sql;
    }

    /**
     * @discription 根据城市id 查询 标签
     * @author 刘正义
     * @created 2015-12-22 下午4:32:31
     * @return
     */

    public String queryTagListByCityId()
    {
        String sql = "select  *  from h_tag where domains_id in((select id from h_domains where name=#{city}),0) and state=1";
        System.out.println(sql + "**************sql");
        return sql;
    }

    /**
     * @discription 查询所有的配套设置sql
     * @author 刘正义
     * @created 2015-12-23 上午9:32:06
     * @return
     */
    public String queryAllSupporting()
    {
        String sql = "select * from h_supporting where state=1 ";
        return sql;
    }

    /**
     * @discription 查询属性
     * @author 刘正义
     * @created 2015-12-23 上午9:41:41
     * @return
     */
    public String queryAllProperty()
    {
        String sql = "select * from h_property where state=1";
        return sql;
    }

    /**
     * @discription 根据属性id 查询属性值
     * @author 刘正义
     * @created 2015-12-23 上午10:04:49
     * @return
     */
    public String queryPropertyValueByPropertyId()
    {
        String sql = "select * from h_property_value where state=1 and pro_id=#{propertyid}";
        return sql;
    }

    /**
     * @discription 添加民居
     * @author 刘正义
     * @created 2015-12-24 下午2:37:59
     * @return
     */
    public String addHouse(Map<String, Object> map)
    {
        House house = (House) map.get("house");
        SqlBuilder.BEGIN();

        SqlBuilder.INSERT_INTO("h_house");
        if (house.getHouse_name() != null)
        {
            SqlBuilder.VALUES("house_num", "#{house_num}");
        }
        if (house.getPrice_id() != 0)
        {
            SqlBuilder.VALUES("price_id", "#{price_id}");
        }
        if (house.getKeynum() != null)
        {
            SqlBuilder.VALUES("keynum", "#{keynum}");
        }
        if (house.getMerchants_id() != 0)
        {
            SqlBuilder.VALUES("merchants_id", "#{merchants_id}");
        }
        if (house.getHouse_name() != null)
        {
            SqlBuilder.VALUES("house_name", "#{house_name}");
        }
        if (house.getSku_id() != 0)
        {
            SqlBuilder.VALUES("sku_id", "#{sku_id}");
        }
        if (house.getIntroduce() != null)
        {
            SqlBuilder.VALUES("introduce", "#{introduce}");
        }
        if (house.getGeo_position() != null)
        {
            SqlBuilder.VALUES("geo_position", "#{geo_position}");
        }
        if (house.getGeo_indication() != null)
        {
            SqlBuilder.VALUES("geo_indication", "#{geo_indication}");
        }
        if (house.getResult() != null)
        {
            SqlBuilder.VALUES("result", "#{result}");
        }
        if (house.getGuarantee() != null)
        {
            SqlBuilder.VALUES("guarantee", "#{guarantee}");
        }
        if (house.getHouse_state() != 0)
        {
            SqlBuilder.VALUES("house_state", "#{house_state}");
        }
        if (house.getHouse_ctime() != null)
        {
            SqlBuilder.VALUES("house_ctime", "#{house_ctime}");
        }
        if (house.getManager_id() != 0)
        {
            SqlBuilder.VALUES("manager_id", "#{manager_id}");
        }
        if (house.getCityid() != null)
        {
            SqlBuilder.VALUES("cityid", "#{cityid}");
        }
        if (house.getAreaid() != null)
        {
            SqlBuilder.VALUES("areaid", "#{areaid}");
        }
        if (house.getIs_characteristic() != 0)
        {
            SqlBuilder.VALUES("is_characteristic", "#{is_characteristic}");
        }
        if (house.getIs_choice() != 0)
        {
            SqlBuilder.VALUES("is_choice", "#{is_choice}");
        }
        return SqlBuilder.SQL();
    }

    /**
     * @discription 添加民居sku
     * @author 刘正义
     * @created 2015-12-24 下午2:38:57
     * @return
     */
    public String addSku()
    {
        String sql = "insert into h_sku  (Sku_ctime,manager_id,category_id) values(#{sku_ctime},#{manager_id},#{category_id})";
        return sql;
    }

    /**
     * @discription 批量添加民居标签
     * @author 刘正义
     * @created 2015-12-26 上午11:24:41
     * @param map
     * @return
     */
    public String addHouseTag(Map map)
    {
        List<Integer> tags = (List<Integer>) map.get("tags");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(" insert into h_house_tag(house_id,tag_id ) values");
        MessageFormat mf = new MessageFormat(" #'{'tags[{0}]})");
        for (int i = 0; i < tags.size(); i++)
        {
            sqlBuilder.append("(#{houseid},");
            sqlBuilder.append(mf.format(new Integer[]{ i }));
            sqlBuilder.append(",");

        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        return sqlBuilder.toString();
    }

    /**
     * @discription 添加属性值
     * @author 刘正义
     * @created 2015-12-26 上午11:27:18
     * @param map
     * @return
     */
    public String addPropertyValue()
    {
        // List<PropertyValue> provalue = (List<PropertyValue>) map
        // .get("provalue");
        // StringBuilder sqlBuilder = new StringBuilder();
        // sqlBuilder.append(" insert into h_property_value(pro_id,name) values");
        // MessageFormat mf = new MessageFormat(
        // "(#'{'provalue[{0}].pro_id},#'{'provalue[{0}].name})");
        // for (int i = 0; i < provalue.size(); i++)
        // {
        // sqlBuilder.append(mf.format(new Integer[]
        // { i }));
        // sqlBuilder.append(",");
        // }
        // sqlBuilder.setLength(sqlBuilder.length() - 1);
        // System.out.println(sqlBuilder.toString());
        // return sqlBuilder.toString();

        String sql = "insert into h_property_value (pro_id,name) values(#{pro_id},#{name})";
        return sql;
    }

    /**
     * @discription 添加民居的属性值
     * @author 刘正义
     * @created 2015-12-26 下午2:15:02
     * @param map
     * @return
     */
    public String insertSkuPro(Map map)
    {
        List<Integer> pro = (List<Integer>) map.get("pro");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into h_sku_property_value(sku_id,v_id) values");
        MessageFormat mf = new MessageFormat(" #'{'pro[{0}]})");
        for (int i = 0; i < pro.size(); i++)
        {
            sqlBuilder.append("(#{skuid},");
            sqlBuilder.append(mf.format(new Integer[]{ i }));
            sqlBuilder.append(",");
        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        return sqlBuilder.toString();
    }

    /**
     * @discription 添加房间配套设置
     * @author 刘正义
     * @created 2015-12-29 下午3:27:25
     * @param map
     * @return
     */
    public String insertHouseSupp(Map<String, Object> map)
    {
        List<Integer> list = (List<Integer>) map.get("list");
        StringBuffer sql = new StringBuffer();
        sql.append("insert into h_house_supporting(house_id,sup_id) values ");
        MessageFormat mf = new MessageFormat("#'{'list[{0}]})");
        for (int i = 0; i < list.size(); i++)
        {
            sql.append("(#{houseid},");
            sql.append(mf.format(new Integer[]{ i }));
            sql.append(",");
        }

        sql.setLength(sql.length() - 1);
        return sql.toString();
    }

    /**
     * @discription 添加图片
     * @author 刘正义
     * @created 2015-12-28 下午3:23:33
     * @param map
     * @return
     */
    public String insertImage(Map map)
    {
        List<String> list = (List<String>) map.get("list");
        StringBuilder sqlBulider = new StringBuilder();
        sqlBulider.append("insert into h_img (house_id, img_url) values");
        MessageFormat mf = new MessageFormat(" #'{'list[{0}]})");
        for (int i = 0; i < list.size(); i++)
        {
            sqlBulider.append("(#{houseid},");
            sqlBulider.append(mf.format(new Integer[]{ i }));
            sqlBulider.append(",");
        }
        sqlBulider.setLength(sqlBulider.length() - 1);
        return sqlBulider.toString();
    }

    /**
     * @discription 添加价格
     * @author 刘正义
     * @created 2015-12-28 下午4:44:46
     * @return
     */
    public String insertPrice()
    {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into h_price(pricing,single_price,week_price,month_price,three_price,five_price,price_ctime,manager_id,bTime,etime,house_id)");
        sqlBuilder.append("values(#{pricing},#{single_price},#{week_price},#{month_price},#{three_price},#{five_price},#{price_ctime},#{manager_id},#{btime},#{etime},#{house_id})");
        return sqlBuilder.toString();
    }

    /**
     * @discription 根据id修改民居
     * @author 刘正义
     * @created 2015-12-28 下午4:45:02
     * @param map
     * @return
     */
    public String updateHouse(Map<String, Object> map)

    {
        House house = (House) map.get("house");
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("update h_house  set ");
        if (house.getGeo_position() != null)
        {
            sqlBuilder.append("geo_position=#{house.geo_position},");
        }
        // 房间编号
        if (house.getHouse_num() != null)
        {
            sqlBuilder.append("house_num=#{house.house_num},");
        }
        // 所编号
        if (house.getKeynum() != null)
        {
            sqlBuilder.append("keynum=#{house.keynum},");
        }
        // 锁类型
        if (house.getLock_id() != 0)
        {
            sqlBuilder.append("lock_id=#{lock_id},");
        }
        if (house.getHouse_name() != null)
        {
            sqlBuilder.append("house_name=#{house.house_name},");
        }
        if (house.getIntroduce() != null)
        {
            sqlBuilder.append("Introduce=#{house.introduce},");
        }
        if (house.getGeo_position() != null)
        {
            sqlBuilder.append("geo_position=#{house.geo_position},");
        }

        if (house.getGeo_indication() != null)
        {
            sqlBuilder.append("geo_indication=#{house.geo_indication},");
        }
        if (house.getResult() != null)
        {
            sqlBuilder.append("result=#{house.result},");
        }
        if (house.getGuarantee() != null)
        {
            sqlBuilder.append("guarantee=#{house.guarantee},");
        }
        if (house.getHouse_state() != 0)
        {
            sqlBuilder.append("house_state=#{house.house_state},");
        }
        if (house.getCityid() != null)
        {
            sqlBuilder.append("cityid=#{house.cityid},");
        }
        if (house.getAreaid() != null)
        {
            sqlBuilder.append("areaid=#{house.areaid},");
        }
        if (house.getIs_characteristic() != 0)
        {
            sqlBuilder.append("is_characteristic=#{house.is_characteristic},");
        }
        if (house.getIs_choice() != 0)
        {
            sqlBuilder.append(" is_choice=#{house.is_choice},");
        }
        if (house.getCityname() != null)
        {
            sqlBuilder.append(" cityname=#{house.cityname},");
        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        sqlBuilder.append(" where house_id=#{house.house_id}");
        return sqlBuilder.toString();
    }

    /**
     * @discription 根据merchants_id 分页 查询 house merchants_id=0则为 查询平台
     * @author 刘正义
     * @created 2015-12-28 下午5:13:29
     * @return
     */
    public String queryHouse(Map<String, Object> map)
    {
        int merchantsid = (Integer) map.get("merchantsid");
        StringBuffer sql = new StringBuffer();
        if (merchantsid == 0)
        {
            sql.append("select * from h_house limit #{beginNum},#{ps}");
        }
        else
        {
            sql.append("select * from h_house where Merchants_id=#{merchantsid} limit #{beginNum},#{ps}");
        }
        return sql.toString();
    }

    public String queryHouse1(Map<String, Object> map)
    {
        int merchantsid = (Integer) map.get("merchantsid");
        String stime = (String) map.get("stime");
        String etime = (String) map.get("etime");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from h_house where 1=1 ");
        if (merchantsid != 0)
        {
            sql.append(" and Merchants_id=" + merchantsid);
        }
        if (stime != null && !stime.equals("") && !stime.equals("-99"))
        {
            sql.append(" and house_ctime='" + stime + "'");
        }
        if (etime != null && !etime.equals("") && !etime.equals("-99"))
        {
            sql.append(" and house_ctime='" + etime + "'");
        }
        sql.append(" order by house_Id desc limit #{beginNum},#{ps}");
        return sql.toString();
    }

    /**
     * @discription 查询房间数量
     * @author 刘正义
     * @created 2015-12-28 下午5:57:32
     * @param map
     * @return
     */
    public String queryHouseNum(Map<String, Object> map)
    {
        int merchantsid = (Integer) map.get("merchantsid");
        String sql = null;
        if (merchantsid == 0)
        {
            sql = "select count(house_id) from h_house";
        }
        else
        {
            sql = "select count(house_id) from h_house where merchants_id=#{merchantsid}";
        }
        return sql;
    }

    /**
     * @discription 根据 house id 查询 house
     * @author 刘正义
     * @created 2015-12-29 上午9:11:32
     * @return
     */
    public String queryHouseById()
    {
        String sql = "select * from h_house where house_id=#{house_id}";
        return sql;
    }

    /**
     * @discription 根据skuid 查询 属性值
     * @author 刘正义
     * @created 2015-12-29 上午9:35:46
     * @return
     */
    public String queryPropertyValueById()
    {
        String sql = "select a.* ,b.*  from  h_property a, h_property_value b where a.pro_id=b.pro_id and b.state=1 and  b.v_id in (select  v_id  from h_sku_property_value where sku_id=#{skuid} and sku_state=1)";
        return sql;
    }

    /**
     * @discription 根据 house id 查询 图片
     * @author 刘正义
     * @created 2015-12-29 上午9:50:34
     * @return
     */
    public String queryImageById()
    {
        String sql = "select * from h_img where House_id=#{house_id}";
        return sql;
    }

    /**
     * @discription 根据 houseid 查询 tag
     * @author 刘正义
     * @created 2015-12-29 上午10:09:48
     * @return
     */
    public String queryTagById()
    {
        String sql = "select * from h_tag a ,h_house_tag b where a.tag_id =b.tag_id and b.house_id=#{house_id} and b.state=1 and a.state =1";
        return sql;
    }

    /**
     * @discription 根据houseid查询 价格
     * @author 刘正义
     * @created 2015-12-29 上午10:18:34
     * @return
     */
    public String queryPriceByHouseid()
    {
        String sql = "select *  from h_price where  house_id=#{houseid}";
        return sql;
    }

    /**
     * @discription 查询房间配置信息
     * @author 刘正义
     * @created 2015-12-29 下午4:42:34
     * @return
     */
    public String querySupportingById()
    {
        String sql = "select * from h_supporting a,h_house_supporting b where a.sup_id=b.sup_id and b.house_id=#{houseid} and a.state=1 and b.state =1";
        return sql;
    }

    /**
     * @discription 根据skuid 删除 房屋属性值
     * @author 刘正义
     * @created 2015-12-30 下午3:19:58
     * @return
     */
    public String DeleteProValueBySkuId()
    {
        String sql = "delete from h_sku_property_value where sku_id=#{skuid}";
        return sql;
    }

    /**
     * @discription 根据houseid删除 房屋配置信息
     * @author 刘正义
     * @created 2015-12-30 下午4:15:08
     * @return
     */
    public String deleteSuppByHouseId()
    {
        String sql = "delete from h_house_supporting where house_id=#{houseid}";
        return sql;
    }

    /**
     * @discription 修改价格
     * @author 刘正义
     * @created 2015-12-31 上午9:10:25
     * @param map
     * @return
     */
    public String updatePrice(Map<String, Object> map)
    {
        Price price = (Price) map.get("price");
        StringBuffer sql = new StringBuffer();
        sql.append("update h_price set ");
        if (price.getPricing() != 0)
        {
            sql.append(" pricing=#{price.pricing} ,");
        }
        if (price.getSingle_price() != 0)
        {
            sql.append("single_price=#{price.single_price},");
        }
        if (price.getWeek_price() != 0)
        {
            sql.append("week_price=#{price.week_price},");
        }
        if (price.getMonth_price() != 0)
        {
            sql.append("month_price=#{price.month_price},");
        }
        if (price.getThree_price() != 0)
        {
            sql.append("three_price=#{price.three_price},");
        }
        if (price.getFive_price() != 0)
        {
            sql.append("five_price=#{price.five_price},");
        }
        if (price.getBtime() != null)
        {
            sql.append("btime=#{price.btime},");
        }
        if (price.getEtime() != null)
        {
            sql.append("etime=#{price.etime},");
        }
        if (price.getState() != 0)
        {
            sql.append("state=#{price.state},");
        }
        sql.setLength(sql.length() - 1);
        sql.append("where price_id =#{price.price_id}");
        return sql.toString();
    }

    /**
     * @discription 删除房屋的标签
     * @author 刘正义
     * @created 2015-12-31 下午2:16:48
     * @return
     */
    public String deleteHouseTag()
    {
        String sql = "delete from h_house_tag where house_id=#{houseid}";
        return sql;
    }

    public String queryHouse1()
    {
        return null;
    }

    public String Test()
    {
        SqlBuilder sql = new SqlBuilder();
        return null;
    }
}