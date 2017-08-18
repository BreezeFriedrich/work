/**
 * 
 */
package com.hysm.domain;

/**
 * 特色值
 * 
 * @author john
 * 
 */
public class CharacteristicValue
{
    private int charav_id;
    private String charav_name;
    private int chara_id;
    private int state;

    public int getCharav_id()
    {
        return charav_id;
    }

    public void setCharav_id(int charav_id)
    {
        this.charav_id = charav_id;
    }

    public String getCharav_name()
    {
        return charav_name;
    }

    public void setCharav_name(String charav_name)
    {
        this.charav_name = charav_name;
    }

    public int getChara_id()
    {
        return chara_id;
    }

    public void setChara_id(int chara_id)
    {
        this.chara_id = chara_id;
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
