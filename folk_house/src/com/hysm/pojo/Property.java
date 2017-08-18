/**     
 * @discription 民居属性表
 * @author   刘正义         
 * @created 2015-12-23 上午9:35:32    
 * tags     
 * see_to_target     
 */

package com.hysm.pojo;

import java.util.List;

public class Property
{

    private String pro_name,data;
    private int pro_id, ptype, state, selectType;

    
    
    

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	private List<PropertyValue> pvlist;

    public List<PropertyValue> getPvlist()
    {
        return pvlist;
    }

    public void setPvlist(List<PropertyValue> pvlist)
    {
        this.pvlist = pvlist;
    }

    public String getPro_name()
    {
        return pro_name;
    }

    public int getSelectType()
    {
        return selectType;
    }

    public void setSelectType(int selectType)
    {
        this.selectType = selectType;
    }

    public void setPro_name(String pro_name)
    {
        this.pro_name = pro_name;
    }

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

}
