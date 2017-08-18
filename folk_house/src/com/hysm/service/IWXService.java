/**
 * 
 */
package com.hysm.service;

import com.hysm.domain.User;

/**
 * 微信处理类
 * 
 * @author john
 * 
 */
public interface IWXService
{
	public static String SESSION_USER="USERSESSION";
	
    public int addSubscribe(User user);

    public int UnSubscribe(User user);

	public User findUserByopenid(String openid);

	public int UnSubscribe2(User user);

}
