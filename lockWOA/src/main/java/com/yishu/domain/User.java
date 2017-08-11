/**
 * 
 */
package com.yishu.domain;

/**
 * @author
 * 
 */
public class User
{
    private int user_id;
    private String openid;
    private String createtime;
    private String nickname;
    private String imgurl;
    private String untime;

    public int getUser_id()
    {
        return user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public String getOpenid()
    {
        return openid;
    }

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }

    public String getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getImgurl() {
		return imgurl;
	}

    public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

    public String getUntime()
    {
        return untime;
    }

    public void setUntime(String untime)
    {
        this.untime = untime;
    }

}