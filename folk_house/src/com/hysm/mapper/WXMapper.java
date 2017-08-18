/**
 * 
 */
package com.hysm.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hysm.domain.User;
import com.hysm.mapper.sqlpro.WXPro;

/**
 * @author john
 * 
 */
public interface WXMapper
{

    // 添加关注着信息
    @InsertProvider(method = "addSubscribe", type = WXPro.class)
    public int addSubscribe(User user);

    @UpdateProvider(method = "UnSubscribe", type = WXPro.class)
    public int UnSubscribe(User user);

    
    @SelectProvider(method = "findUserByopenid", type = WXPro.class)
	public User findUserByopenid(@Param("openid")String openid);

    @UpdateProvider(method = "UnSubscribe2", type = WXPro.class)
	public int UnSubscribe2(User user);
}
