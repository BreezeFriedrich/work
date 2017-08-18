/**     
 * @discription 房型价格类
 * @author   刘正义         
 * @created 2016-1-8 下午3:02:50    
 * tags     
 * see_to_target     
 */

package com.hysm.domain;

public class Price extends Img
{

    private int price_id;// 价格 id

    private int pricing;// 标价
    private int single_price;// 单租价格
    private int week_price;// 周租价格
    private int month_price;// 月租价格
    private int three_price;// 提前三天预定
    private int five_price;// 提前五天预定

    private int state; // 状态
    private String price_ctime; // 创建时间
    private int manager_id; // 创建人
    private String btime; // 开始时间
    private String etime; // 结束时间

    private int ht_id; // 房间类型

    private int holiday_id;// 节假日价格 id

    private String begintime;// 节假日加价开始时间

    private String endtime; // 节假日加价结束时间
    private int addprice; // 节假日价格 加价

    public int getHoliday_id()
    {
        return holiday_id;
    }

    public String getBegintime()
    {
        return begintime;
    }

    public void setBegintime(String begintime)
    {
        this.begintime = begintime;
    }

    public String getEndtime()
    {
        return endtime;
    }

    public void setEndtime(String endtime)
    {
        this.endtime = endtime;
    }

    public void setHoliday_id(int holiday_id)
    {
        this.holiday_id = holiday_id;
    }

    public int getAddprice()
    {
        return addprice;
    }

    public void setAddprice(int addprice)
    {
        this.addprice = addprice;
    }

    public int getPrice_id()
    {
        return price_id;
    }

    public void setPrice_id(int price_id)
    {
        this.price_id = price_id;
    }

    public int getPricing()
    {
        return pricing;
    }

    public void setPricing(int pricing)
    {
        this.pricing = pricing;
    }

    public int getSingle_price()
    {
        return single_price;
    }

    public void setSingle_price(int single_price)
    {
        this.single_price = single_price;
    }

    public int getWeek_price()
    {
        return week_price;
    }

    public void setWeek_price(int week_price)
    {
        this.week_price = week_price;
    }

    public int getMonth_price()
    {
        return month_price;
    }

    public void setMonth_price(int month_price)
    {
        this.month_price = month_price;
    }

    public int getThree_price()
    {
        return three_price;
    }

    public void setThree_price(int three_price)
    {
        this.three_price = three_price;
    }

    public int getFive_price()
    {
        return five_price;
    }

    public void setFive_price(int five_price)
    {
        this.five_price = five_price;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getPrice_ctime()
    {
        return price_ctime;
    }

    public void setPrice_ctime(String price_ctime)
    {
        this.price_ctime = price_ctime;
    }

    public int getManager_id()
    {
        return manager_id;
    }

    public void setManager_id(int manager_id)
    {
        this.manager_id = manager_id;
    }

    public String getBtime()
    {
        return btime;
    }

    public void setBtime(String btime)
    {
        this.btime = btime;
    }

    public String getEtime()
    {
        return etime;
    }

    public void setEtime(String etime)
    {
        this.etime = etime;
    }

    public int getHt_id()
    {
        return ht_id;
    }

    public void setHt_id(int ht_id)
    {
        this.ht_id = ht_id;
    }

}
