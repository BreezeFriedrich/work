/**
 * 
 */
package com.hysm.service;

import com.hysm.domain.HouseType;
import com.hysm.pojo.PageBean;

/**
 * 特色民居
 * 
 * @author john
 * 
 */
public interface IFeatrueService
{

    public PageBean<HouseType> queryFeatrue(String chara_id, String city_name,
            String stime, String etime, int pn);

    public PageBean<HouseType> queryHouseTypeByfeatid(String featureid,
            String city_name, String stime, String etime, int pn);
}
