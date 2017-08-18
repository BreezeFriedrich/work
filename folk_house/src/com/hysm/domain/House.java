/**     
 * @discription 民居实体类
 * @author   刘正义         
 * @created 2016-1-8 下午2:37:17    
 * tags     
 * see_to_target     
 */

package com.hysm.domain;

public class House
{
    private int house_id;
    private String house_num;
    private String keynum;
    private int house_state;
    private String house_ctime;
    private int manager_id;
    private int is_characteristic;
    private int is_choice;
    private int ht_id;
    private int charav_id;

    private String mangername; // MANGERNAME 添加房间管理员名字

    private String charav_name; // 特色值

    public String getCharav_name()
    {
        return charav_name;
    }

    public void setCharav_name(String charav_name)
    {
        this.charav_name = charav_name;
    }

    public String getMangername()
    {
        return mangername;
    }

    public void setMangername(String mangername)
    {
        this.mangername = mangername;
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

    public String getKeynum()
    {
        return keynum;
    }

    public void setKeynum(String keynum)
    {
        this.keynum = keynum;
    }

    public int getHouse_state()
    {
        return house_state;
    }

    public void setHouse_state(int house_state)
    {
        this.house_state = house_state;
    }

    public String getHouse_ctime()
    {
        return house_ctime;
    }

    public void setHouse_ctime(String house_ctime)
    {
        this.house_ctime = house_ctime;
    }

    public int getManager_id()
    {
        return manager_id;
    }

    public void setManager_id(int manager_id)
    {
        this.manager_id = manager_id;
    }

    public int getIs_characteristic()
    {
        return is_characteristic;
    }

    public void setIs_characteristic(int is_characteristic)
    {
        this.is_characteristic = is_characteristic;
    }

    public int getIs_choice()
    {
        return is_choice;
    }

    public void setIs_choice(int is_choice)
    {
        this.is_choice = is_choice;
    }

    public int getHt_id()
    {
        return ht_id;
    }

    public void setHt_id(int ht_id)
    {
        this.ht_id = ht_id;
    }

    public int getCharav_id()
    {
        return charav_id;
    }

    public void setCharav_id(int charav_id)
    {
        this.charav_id = charav_id;
    }

}
