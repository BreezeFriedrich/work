package com.hysm.domain;

import java.util.List;

public class Property
{
    private int pro_id, ptype, state, v_id;
    private String pro_name, name;

    private List<PropertyValue> pvlist;

    public int getPro_id()
    {
        return pro_id;
    }

    public void setPro_id(int pro_id)
    {
        this.pro_id = pro_id;
    }

    public int getPtype()
    {
        return ptype;
    }

    public List<PropertyValue> getPvlist()
    {
        return pvlist;
    }

    public void setPvlist(List<PropertyValue> pvlist)
    {
        this.pvlist = pvlist;
    }

    public void setPtype(int ptype)
    {
        this.ptype = ptype;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getPro_name()
    {
        return pro_name;
    }

    public void setPro_name(String pro_name)
    {
        this.pro_name = pro_name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getV_id()
    {
        return v_id;
    }

    public void setV_id(int v_id)
    {
        this.v_id = v_id;
    }

}
