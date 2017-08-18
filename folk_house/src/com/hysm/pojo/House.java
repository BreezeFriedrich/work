/**     
 * @discription 民居类
 * @author   刘正义         
 * @created 2015-12-24 下午2:01:06    
 * tags     
 * see_to_target     
 */

package com.hysm.pojo;

import java.util.List;

public class House
{
    private int house_id;
    private String house_num;
    private int price_id;
    private String keynum;
    private int merchants_id;
    private String house_name;
    private int sku_id;
    private String introduce;// 介绍
    private String geo_position;// 地理位置
    private String geo_indication;// 经纬度
    private String result;// 规则
    private String guarantee;// 保障
    private int house_state;// 状态
    private String house_ctime;
    private int manager_id;
    private String cityid;
    private String areaid;

    private int is_characteristic;// 是否特色
    private int is_choice;// 是否精选
    private String cityname;// 城市名
    private int lock_id;// 锁类型

    public String getCityname()
    {
        return cityname;
    }

    public void setCityname(String cityname)
    {
        this.cityname = cityname;
    }

    private List<Image> limage;// 图片列表
    private List<Tag> ltag;// 标签列表
    private List<PropertyValue> pvaluelist;// 属性值列表
    private List<Price> pricelist; // 价格
    private List<Supporting> supportinglist;// 配套设置

    public List<Supporting> getSupportinglist()
    {
        return supportinglist;
    }

    public void setSupportinglist(List<Supporting> supportinglist)
    {
        this.supportinglist = supportinglist;
    }

    public int getLock_id()
    {
        return lock_id;
    }

    public void setLock_id(int lock_id)
    {
        this.lock_id = lock_id;
    }

    public List<Price> getPricelist()
    {
        return pricelist;
    }

    public void setPricelist(List<Price> pricelist)
    {
        this.pricelist = pricelist;
    }

    public List<PropertyValue> getPvaluelist()
    {
        return pvaluelist;
    }

    public void setPvaluelist(List<PropertyValue> pvaluelist)
    {
        this.pvaluelist = pvaluelist;
    }

    public List<Tag> getLtag()
    {
        return ltag;
    }

    public void setLtag(List<Tag> ltag)
    {
        this.ltag = ltag;
    }

    public List<Image> getLimage()
    {
        return limage;
    }

    public void setLimage(List<Image> limage)
    {
        this.limage = limage;
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

    public int getPrice_id()
    {
        return price_id;
    }

    public void setPrice_id(int price_id)
    {
        this.price_id = price_id;
    }

    public String getKeynum()
    {
        return keynum;
    }

    public void setKeynum(String keynum)
    {
        this.keynum = keynum;
    }

    public int getMerchants_id()
    {
        return merchants_id;
    }

    public void setMerchants_id(int merchants_id)
    {
        this.merchants_id = merchants_id;
    }

    public String getHouse_name()
    {
        return house_name;
    }

    public void setHouse_name(String house_name)
    {
        this.house_name = house_name;
    }

    public int getSku_id()
    {
        return sku_id;
    }

    public void setSku_id(int sku_id)
    {
        this.sku_id = sku_id;
    }

    public String getIntroduce()
    {
        return introduce;
    }

    public void setIntroduce(String introduce)
    {
        this.introduce = introduce;
    }

    public String getGeo_position()
    {
        return geo_position;
    }

    public void setGeo_position(String geo_position)
    {
        this.geo_position = geo_position;
    }

    public String getGeo_indication()
    {
        return geo_indication;
    }

    public void setGeo_indication(String geo_indication)
    {
        this.geo_indication = geo_indication;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    public String getGuarantee()
    {
        return guarantee;
    }

    public void setGuarantee(String guarantee)
    {
        this.guarantee = guarantee;
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

    public String getCityid()
    {
        return cityid;
    }

    public void setCityid(String cityid)
    {
        this.cityid = cityid;
    }

    public String getAreaid()
    {
        return areaid;
    }

    public void setAreaid(String areaid)
    {
        this.areaid = areaid;
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

}
