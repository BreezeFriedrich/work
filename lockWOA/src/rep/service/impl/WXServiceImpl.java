/**
 * 
 */
package com.yishu.service.impl;

import com.yishu.domain.User;
import mapper.WXMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信处理
 * 
 * @author
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