/**
 * 
 */
package com.hysm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hysm.domain.User;
import com.hysm.mapper.WXMapper;

import com.hysm.service.IWXService;

/**
 * 微信处理
 * 
 * @author john
 * 
 */
@Service("wxservice")
public class WXServiceImpl implements IWXService
{

    @Autowired
    private WXMapper wxmapper;

    @Override
    public int addSubscribe(User user)
    {

        int val = wxmapper.addSubscribe(user);

        return val;
    }

    @Override
    public int UnSubscribe(User user)
    {
        int val = wxmapper.UnSubscribe(user);

        return val;
    }

	@Override
	public User findUserByopenid(String openid) {
		
		return wxmapper.findUserByopenid(openid);
	}

	@Override
	public int UnSubscribe2(User user) {
		// TODO Auto-generated method stub
		return wxmapper.UnSubscribe2(user);
	}

}
