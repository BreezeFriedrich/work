/**
 * 
 */
package com.hysm.domain;

import java.util.List;

/**
 * 特色
 * 
 * @author john
 * 
 */
public class Characteristic
{

    private int charac_id;
    private String chara_name;
    private int state;
    private List<CharacteristicValue> cvlist;

    public int getCharac_id()
    {
        return charac_id;
    }

    public void setCharac_id(int charac_id)
    {
        this.charac_id = charac_id;
    }

    public String getChara_name()
    {
        return chara_name;
    }

    public void setChara_name(String chara_name)
    {
        this.chara_name = chara_name;
    }

    public int getState()
    {
        return state;
    }

    public List<CharacteristicValue> getCvlist()
    {
        return cvlist;
    }

    public void setCvlist(List<CharacteristicValue> cvlist)
    {
        this.cvlist = cvlist;
    }

    public void setState(int state)
    {
        this.state = state;
    }

}
