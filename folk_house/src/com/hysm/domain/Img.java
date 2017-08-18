/**     
 * @discription 房间图片表
 * @author   刘正义         
 * @created 2016-1-8 下午2:59:07    
 * tags     
 * see_to_target     
 */

package com.hysm.domain;

public class Img
{

    private int img_id;// 图片id
    private String img_url;// 图片路径
    private int ht_id;// 房间类型
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

    public int getHt_id()
    {
        return ht_id;
    }

    public void setHt_id(int ht_id)
    {
        this.ht_id = ht_id;
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
