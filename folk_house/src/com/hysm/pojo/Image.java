/**     
 * @discription 房间图片
 * @author   刘正义         
 * @created 2015-12-29 上午9:14:27    
 * tags     
 * see_to_target     
 */

package com.hysm.pojo;

public class Image
{
    private int img_id;// 图片id
    private String img_url;// 图片路径
    private int house_id;// 房间 id
    private int state;// 状态

    public int getImg_id()
    {
        return img_id;
    }

    public void setImg_id(int img_id)
    {
        this.img_id = img_id;
    }

    public String getImg_url()
    {
        return img_url;
    }

    public void setImg_url(String img_url)
    {
        this.img_url = img_url;
    }

    public int getHouse_id()
    {
        return house_id;
    }

    public void setHouse_id(int house_id)
    {
        this.house_id = house_id;
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
