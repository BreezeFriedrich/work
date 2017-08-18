/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author   刘正义         
 * @created 2016-1-9 上午9:06:23    
 * tags     
 * see_to_target     
 */

package com.hysm.domain;

public class Hotel extends Price
{

    private int id;
    private String name,name1,name2;// 名字
    private String province;// 省
    private String city;// 市
    private String county;// 县
    private String address;// 街道
    private String gps_lo;// 纬度
    private String gps_la;// 经度
    private int housenum;// 民居数量
    private String desc;// 宾馆描述
    private int mngtype;// 管理类型
    private int state;// 状态
    private String adminpwd;// 密码
    private String safeguard;// 保障
    private String rule;// 规则
    private String starttime;// 开始入住时间
    private String endtime;// 结束入住时间
    private int cash_pledge;// 押金
    private int merchants_id;// 商户表id
    private int ranges;// 距离

    public int getRanges()
    {
        return ranges;
    }

    public void setRanges(int ranges)
    {
        this.ranges = ranges;
    }

    
    
    public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCounty()
    {
        return county;
    }

    public void setCounty(String county)
    {
        this.county = county;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getAdminpwd()
    {
        return adminpwd;
    }

    public void setAdminpwd(String adminpwd)
    {
        this.adminpwd = adminpwd;
    }

    public String getSafeguard()
    {
        return safeguard;
    }

    public void setSafeguard(String safeguard)
    {
        this.safeguard = safeguard;
    }

    public String getRule()
    {
        return rule;
    }

    public void setRule(String rule)
    {
        this.rule = rule;
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

    public int getCash_pledge()
    {
        return cash_pledge;
    }

    public void setCash_pledge(int cash_pledge)
    {
        this.cash_pledge = cash_pledge;
    }

    public int getMerchants_id()
    {
        return merchants_id;
    }

    public void setMerchants_id(int merchants_id)
    {
        this.merchants_id = merchants_id;
    }

    public String getGps_lo()
    {
        return gps_lo;
    }

    public void setGps_lo(String gps_lo)
    {
        this.gps_lo = gps_lo;
    }

    public String getGps_la()
    {
        return gps_la;
    }

    public void setGps_la(String gps_la)
    {
        this.gps_la = gps_la;
    }

    public int getHousenum()
    {
        return housenum;
    }

    public void setHousenum(int housenum)
    {
        this.housenum = housenum;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public int getMngtype()
    {
        return mngtype;
    }

    public void setMngtype(int mngtype)
    {
        this.mngtype = mngtype;
    }

}
