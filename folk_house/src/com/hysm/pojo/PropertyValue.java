/**     
 * @discription 属性值表
 * @author   刘正义         
 * @created 2015-12-23 上午9:57:58    
 * tags     
 * see_to_target     
 */

package com.hysm.pojo;

public class PropertyValue extends Property
{

    private int v_id, pro_id, state;
    private String name,data,pro_name;

    
    
    public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

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

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
