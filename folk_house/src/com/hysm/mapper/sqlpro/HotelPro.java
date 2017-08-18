/**
 * 
 */
package com.hysm.mapper.sqlpro;

/**
 * 宾馆操作底层实现类
 * 
 * @author john
 * 
 */
/**
 * @author john
 *
 */
/**
 * @author john
 * 
 */
public class HotelPro
{

    // 添加宾馆服务
    public String addService()
    {

        String sql = "insert into h_service (ht_id,hs_name ) values (#{ht_id},#{hs_name})";

        return sql;
    }

    // 添加服务员
    public String addServicer()
    {

        String sql = "insert into  m_hotel_servicer (SERVICETYPE,NAME,CARDID,MHID) values(#{servicetype},#{name},#{cardid},#{mhid})";

        return sql;
    }

    // 修改服务员
    public String reviseServicer()
    {
        String sql = "update m_hotel_servicer set CARDID=#{cardid},name=#{name},SERVICETYPE=#{servicetype}  where  id=#{ID}";

        return sql;
    }

    // 冻结服务员
    public String FreezyServicer()
    {
        String sql = "update m_hotel_servicer   set   state =2    where  id=#{id}";

        return sql;
    }

    // 激活服务员
    public String activateServicer()
    {
        String sql = "update  m_hotel_servicer  set   state=1     where  id=#{id}";

        return sql;
    }

    // 根据宾馆id 查询服务员
    public String queryServicerById()
    {

        String sql = "select * from m_hotel_servicer where mhid=#{mhid}";

        return sql;
    }

    // 根据服务员id 查询服务员
    public String queryServicerBySid()
    {
        String sql = "select  *  from  m_hotel_servicer where id=#{id}";

        return sql;
    }

    // 根据服务员id 修改服务员信息
    public String resiveServicerBySid()
    {

        String sql = "update m_hotel_servicer set name=#{name},cardid=#{cardid},servicetype=#{servicetype} where id=#{id}";

        return sql;
    }

    /**
     * @discription 根据服务员查询服务房间
     * @author 刘正义
     * @created 2016-3-12 上午10:17:41
     * @return
     */
    public String queryHouseBySeriverId()
    {
        String sql = "select house_num from m_house_cleaner a ,h_house b  where  a.houseid =b.house_id  and a.SERVICERID=#{sid}";

        return sql;
    }

    /**
     * @discription 根据宾馆id查询清洁员
     * @author 刘正义
     * @created 2016-3-12 下午2:55:53
     * @return
     */
    public String queryClear()
    {
        String sql = "select  *  from m_hotel_servicer where mhid=#{houseid} and state=1 and  SERVICETYPE=1";

        return sql;

    }

    /**
     * @discription 给房间添加清洁员
     * @author 刘正义
     * @created 2016-3-12 下午4:54:28
     * @return
     */
    public String addClear()
    {

        String sql = "insert into m_house_cleaner (HOUSEID,SERVICERID) values (#{hid},#{servicerid})";

        return sql;
    }

}
