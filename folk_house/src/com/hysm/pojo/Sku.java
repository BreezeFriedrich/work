/**     
 * @discription 民居
 * @author   刘正义         
 * @created 2015-12-24 下午2:45:52    
 * tags     
 * see_to_target     
 */

package com.hysm.pojo;

public class Sku
{
    private int sku_id;
    private int sku_click;
    private String sku_ctime;
    private int manager_id;
    private int sku_state;
    private int category_id;

    public int getSku_id()
    {
        return sku_id;
    }

    public void setSku_id(int sku_id)
    {
        this.sku_id = sku_id;
    }

    public int getSku_click()
    {
        return sku_click;
    }

    public void setSku_click(int sku_click)
    {
        this.sku_click = sku_click;
    }

    public String getSku_ctime()
    {
        return sku_ctime;
    }

    public void setSku_ctime(String sku_ctime)
    {
        this.sku_ctime = sku_ctime;
    }

    public int getManager_id()
    {
        return manager_id;
    }

    public void setManager_id(int manager_id)
    {
        this.manager_id = manager_id;
    }

    public int getSku_state()
    {
        return sku_state;
    }

    public void setSku_state(int sku_state)
    {
        this.sku_state = sku_state;
    }

    public int getCategory_id()
    {
        return category_id;
    }

    public void setCategory_id(int category_id)
    {
        this.category_id = category_id;
    }
}
