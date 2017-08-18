/**     
 * @discription 房间日志表
 * @author   刘正义         
 * @created 2016-1-8 下午2:53:21    
 * tags     
 * see_to_target     
 */

package com.hysm.domain;

public class Schedule
{
    private int sch_id;//
    private int house_id;// 房间id
    private String starttime;// 入住时间
    private String endtime;// 结束时间
    private int orderid;// 订单id
    private int state;// 状态

    private String house_num; // 房间号码

    public String getHouse_num()
    {
        return house_num;
    }

    public void setHouse_num(String house_num)
    {
        this.house_num = house_num;
    }

    public int getSch_id()
    {
        return sch_id;
    }

    public void setSch_id(int sch_id)
    {
        this.sch_id = sch_id;
    }

    public int getHouse_id()
    {
        return house_id;
    }

    public void setHouse_id(int house_id)
    {
        this.house_id = house_id;
    }

    public String getStarttime()
    {
        return starttime;
    }

    public void setStarttime(String starttime)
    {
        this.starttime = starttime;
    }

    public String getEndtime()
    {
        return endtime;
    }

    public void setEndtime(String endtime)
    {
        this.endtime = endtime;
    }

    public int getOrderid()
    {
        return orderid;
    }

    public void setOrderid(int orderid)
    {
        this.orderid = orderid;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

}
