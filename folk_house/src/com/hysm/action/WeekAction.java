package com.hysm.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hysm.domain.HouseType;
import com.hysm.domain.Price;
import com.hysm.pojo.PageBean;
import com.hysm.service.IWeekService;
import com.hysm.util.DataUtil;
import com.hysm.util.StringTools;

/**
 * @author john 周租月租
 * 
 */

public class WeekAction extends BaseAction
{

    @Autowired
    private IWeekService weekservice;

    /**
     * @discription 周租月租首页房屋信息
     * @author 刘正义
     * @created 2016-1-13 上午9:30:48
     * @return
     */
    public String indexWeek()
    {
        pn = 1;
        city_name = "南京市";
        stime = DataUtil.fromDateY() + " 12:00:01";
        etime = DataUtil.DateAfter(new Date(), 1) + " 11:59:59";
        pb = weekservice.queryIndexWeek(pn, city_name, stime, etime);
        housetype = pb.getList();
        Map<String, Object> map = pb.getMap();
        price = (List<Price>) map.get("price");
        return "weekindex";
    }

    /**
     * @discription 周租月租按城市搜索
     * @author 刘正义
     * @created 2016-1-13 下午4:36:50
     * @return
     */
    public String weekcity()
    {

        if (pn == 0)
        {

            pn = 1;
        }
        if (stime == null || stime.equals(""))
        {
            stime = DataUtil.fromDateY();
        }
        else
        {

            stime = stime;

        }
        if (etime == null || etime.equals(""))
        {

            etime = DataUtil.DateAfter(new Date(), 1);
        }
        else
        {

            etime = etime;

        }
        if (city_name == null || city_name.trim().equals(""))
        {

            city_name = "南京市";

        }
        int index = city_name.indexOf("市");
        if (index > 0)
        {

            city = city_name.substring(0, index);
        }

        pb = weekservice.queryWeekByCity(pn, city_name, stime + " 12:00:01",
                etime + " 11:59:59", pricenum);

        housetype = pb.getList();
        Map<String, Object> map = pb.getMap();
        if (map != null)
        {
            price = (List<Price>) map.get("price");
        }
        return "weekcity";
    }

    /**
     * 多条件查询民居
     * 
     * @return
     */
    public String weekByTag()
    {
        if (pn == 0)
        {
            pn = 1;
        }
        if (stime == null || stime.equals(""))
        {
            stime = DataUtil.fromDateY() + " 12:00:01";
        }
        else
        {

            stime = stime + " 12:00:01";

        }
        if (etime == null || etime.equals(""))
        {

            etime = DataUtil.DateAfter(new Date(), 1) + " 11:59:59";
        }
        else
        {

            etime = etime + " 11:59:59";

        }
        if (city_name == null || city_name.trim().equals(""))
        {

            city_name = "南京市";

        }
        List<String> pro = this.proToList(provalue);
        System.out.println(pn + "pn");
        pb = weekservice.queryWeekByTag(pn, city_name, stime, etime, pricenum,
                tagid, pro);

        housetype = pb.getList();
        Map<String, Object> map = pb.getMap();
        if (map != null)
        {
            price = (List<Price>) map.get("price");
        }
        System.out.println(pb.getPageCount() + "pb");
        return "publicweek";
    }

    private List<String> proToList(String provalue)
    {

        List<String> list = new ArrayList<String>();
        if (provalue != null)
        {
            String[] str = provalue.split(",");
            for (int i = 0; i < str.length; i++)
            {
                if (StringTools.isNum(str[i]))
                {
                    list.add(str[i]);

                }
            }
        }
        return list;
    }

    public String countey_load() throws UnsupportedEncodingException
    {
        if (pn == 0)
        {

            pn = 1;
        }
        if (stime == null || stime.equals(""))
        {
            stime = DataUtil.fromDateY();
        }
       
        if (etime == null || etime.equals(""))
        {

            etime = DataUtil.DateAfter(new Date(), 1);
        }
       

        System.out.println(city_name + "*********countey_load**********"
                + new String(city_name.getBytes("ISO-8859-1"), "utf-8"));

        List<String> pro = this.proToList(provalue);
        System.out.println(pn + "pn");
        pb = weekservice.queryWeekByTag(pn, city_name, stime + " 12:00:01",
                etime + " 11:59:59", pricenum, tagid, pro);

        housetype = pb.getList();
        Map<String, Object> map = pb.getMap();
        if (map != null)
        {
            price = (List<Price>) map.get("price");
        }
        request.setAttribute("pb", pb);
        request.setAttribute("list", housetype);
        request.setAttribute("list_p", price);
        request.setAttribute("city_name", city_name);
        request.setAttribute("stime", stime);
        request.setAttribute("etime", etime);
        request.setAttribute("tag_id", tagid);
        /* request.setAttribute("count", count); */
        request.setAttribute("size", housetype.size());

        return "house_load";
    }

    public String countey_search() throws UnsupportedEncodingException
    {
        if (pn == 0)
        {

            pn = 1;
        }
        if (stime == null || stime.equals(""))
        {
            stime = DataUtil.fromDateY();
        }
       
        if (etime == null || etime.equals(""))
        {

            etime = DataUtil.DateAfter(new Date(), 1);
        }
       
        System.out.println(city_name + "*********countey_search**********"
                + new String(city_name.getBytes("ISO-8859-1"), "utf-8"));

        List<String> pro = this.proToList(provalue);
        System.out.println(pn + "pn");
        pb = weekservice.countey_search(pn, city_name, stime+ " 12:00:01", etime+ " 11:59:59", tag_id);
        housetype = pb.getList();
        Map<String, Object> map = pb.getMap();
        if (map != null)
        {
            price = (List<Price>) map.get("price");
        }
        if (housetype != null)
        {
            request.setAttribute("pb", pb);
            request.setAttribute("list", housetype);
            request.setAttribute("list_p", price);
            request.setAttribute("city_name", city_name);
            request.setAttribute("stime", stime);
            request.setAttribute("etime", etime);
            request.setAttribute("tag_id", tagid);
            /* request.setAttribute("count", count); */
            request.setAttribute("size", housetype.size());
        }
        return "country_house";
    }

    private int pn;// 页码
    private String stime; // 开始时间
    private String etime; // 束时间
    private String city_name; // 城市
    private String pricenum;// 价格区间
    private PageBean<HouseType> pb;// 分页数据
    private String city;
    private int tagid;
    private int tag_id;
    private String tag_name;
    private String provalue;

    private List<HouseType> housetype;

    private List<Price> price;

    public String getPricenum()
    {
        return pricenum;
    }

    public int getTagid()
    {
        return tagid;
    }

    public void setTagid(int tagid)
    {
        this.tagid = tagid;
    }

    public String getProvalue()
    {
        return provalue;
    }

    public int getTag_id()
    {
        return tag_id;
    }

    public void setTag_id(int tag_id)
    {
        this.tag_id = tag_id;
    }

    public String getTag_name()
    {
        return tag_name;
    }

    public void setTag_name(String tag_name)
    {
        this.tag_name = tag_name;
    }

    public void setProvalue(String provalue)
    {
        this.provalue = provalue;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setPricenum(String pricenum)
    {
        this.pricenum = pricenum;
    }

    public List<HouseType> getHousetype()
    {
        return housetype;
    }

    public void setHousetype(List<HouseType> housetype)
    {
        this.housetype = housetype;
    }

    public List<Price> getPrice()
    {
        return price;
    }

    public void setPrice(List<Price> price)
    {
        this.price = price;
    }

    public PageBean<HouseType> getPb()
    {
        return pb;
    }

    public void setPb(PageBean<HouseType> pb)
    {
        this.pb = pb;
    }

    public int getPn()
    {
        return pn;
    }

    public void setPn(int pn)
    {
        this.pn = pn;
    }

    public String getStime()
    {
        return stime;
    }

    public void setStime(String stime)
    {
        this.stime = stime;
    }

    public String getEtime()
    {
        return etime;
    }

    public void setEtime(String etime)
    {
        this.etime = etime;
    }

    public String getCity_name()
    {
        return city_name;
    }

    public void setCity_name(String city_name)
    {
        this.city_name = city_name;
    }

}
