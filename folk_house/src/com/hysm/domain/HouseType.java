/**     
 * @discription 房型表
 * @author   刘正义         
 * @created 2016-1-8 下午2:49:34    
 * tags     
 * see_to_target     
 */

package com.hysm.domain;

import java.util.List;

/**
 * @author john
 * 
 */
public class HouseType extends Hotel
{
    private int ht_id;
    private String ht_name;// 名字
    private int state;// 状态
    private int coll_state;// 收藏表状态
    private int coll_id;// 收藏表id
    private int hotel_id; // 宾馆id
    private int num; // 房间数量
    private int num_day;// 入住与当前时间相差天数
    private int num1;// 入住与离店相差天数
    private List<Price> plist;// 价格列表

    private List<Img> ilist;// 图片列表

    private List<House> hlist;// 民居列表

    private int house_id;
    private String house_num, keyNum, m_tel;
    
    private int cash_pledge;//宾馆押金
    

    public int getCash_pledge() {
		return cash_pledge;
	}

	public void setCash_pledge(int cash_pledge) {
		this.cash_pledge = cash_pledge;
	}

	public int getNum1()
    {
        return num1;
    }

    public void setNum1(int num1)
    {
        this.num1 = num1;
    }

    public int getNum_day()
    {
        return num_day;
    }

    public void setNum_day(int num_day)
    {
        this.num_day = num_day;
    }

    public String getM_tel()
    {
        return m_tel;
    }

    public int getColl_state()
    {
        return coll_state;
    }

    public void setColl_state(int coll_state)
    {
        this.coll_state = coll_state;
    }

    public int getColl_id()
    {
        return coll_id;
    }

    public void setColl_id(int coll_id)
    {
        this.coll_id = coll_id;
    }

    public void setM_tel(String m_tel)
    {
        this.m_tel = m_tel;
    }

    public int getHouse_id()
    {
        return house_id;
    }

    public void setHouse_id(int house_id)
    {
        this.house_id = house_id;
    }

    public String getHouse_num()
    {
        return house_num;
    }

    public void setHouse_num(String house_num)
    {
        this.house_num = house_num;
    }

    public String getKeyNum()
    {
        return keyNum;
    }

    public void setKeyNum(String keyNum)
    {
        this.keyNum = keyNum;
    }

    public List<House> getHlist()
    {
        return hlist;
    }

    public void setHlist(List<House> hlist)
    {
        this.hlist = hlist;
    }

    public int getHt_id()
    {
        return ht_id;
    }

    public List<Price> getPlist()
    {
        return plist;
    }

    public void setPlist(List<Price> plist)
    {
        this.plist = plist;
    }

    public List<Img> getIlist()
    {
        return ilist;
    }

    public void setIlist(List<Img> ilist)
    {
        this.ilist = ilist;
    }

    public void setHt_id(int ht_id)
    {
        this.ht_id = ht_id;
    }

    public String getHt_name()
    {
        return ht_name;
    }

    public void setHt_name(String ht_name)
    {
        this.ht_name = ht_name;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public int getHotel_id()
    {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id)
    {
        this.hotel_id = hotel_id;
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

}
