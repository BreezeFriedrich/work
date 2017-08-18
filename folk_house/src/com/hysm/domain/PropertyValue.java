/**
 * 
 */
package com.hysm.domain;

/**
 * 房屋属性值表
 * 
 * @author john
 * 
 */
public class PropertyValue
{

    private int v_id;
    private int pro_id;// 属性名字
    private String name;// 属性值名字
    private int state;// 状态

    public int getV_id()
    {
        return v_id;
    }

    public void setV_id(int v_id)
    {
        this.v_id = v_id;
    }

    public int getPro_id()
    {
        return pro_id;
    }

    public void setPro_id(int pro_id)
    {
        this.pro_id = pro_id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
