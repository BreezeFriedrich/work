/**
 * 
 */
package com.hysm.domain;

/**
 * 服务员实体类
 * 
 * @author john
 * 
 */
public class Servicer
{

    private int id;
    private int servicetype;//
    private String name;//
    private String cardid;// 身份id
    private String openid;// 用户微信信息
    private int state;// 状态
    private int mhid;// 宾馆id

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getServicetype()
    {
        return servicetype;
    }

    public void setServicetype(int servicetype)
    {
        this.servicetype = servicetype;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCardid()
    {
        return cardid;
    }

    public void setCardid(String cardid)
    {
        this.cardid = cardid;
    }

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public int getMhid()
    {
        return mhid;
    }

    public void setMhid(int mhid)
    {
        this.mhid = mhid;
    }

}
