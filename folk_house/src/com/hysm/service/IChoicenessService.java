/**
 * 
 */
package com.hysm.service;

import com.hysm.domain.HouseType;
import com.hysm.pojo.PageBean;

/**
 * @author john
 * 
 */
public interface IChoicenessService
{

    public PageBean<HouseType> queryChoicenessIndex(int pn, String city_name,
            String stime, String etime);

    public PageBean<HouseType> queryChoicenessByPro(int pn, String city_name,
            String stime, String etime, String pro);

}
