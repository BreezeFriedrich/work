/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.service.impl;

import com.yishu.dao.LockUserDao;
import com.yishu.dao.WechatUserDao;
import com.yishu.domain.WechatUser;
import com.yishu.pojo.LockUser;
import com.yishu.service.IWechatService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-10-12 17:27 admin
 * @since JDK1.7
 */
@Service("wechatService")
public class WechatServiceImpl implements IWechatService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("WechatServiceImpl");
    @Autowired
    private WechatUserDao wechatUserDao;
    @Autowired
    private LockUserDao lockUserDao;

    //根据wechatUser.openid和lockUser.phonenumber注册用户.
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    @Override
    public int addSubscribe(WechatUser wechatUser,String phonenumber) {
        if (null==wechatUser.getOpenid()){
            logger.warn("wechatUser.openid为空.");
            return 0;
        }
        WechatUser wechatUserTemp=wechatUserDao.findWechatUserByOpenid(wechatUser.getOpenid());
        if (null != wechatUserTemp){
            logger.warn("数据库中已存在此wechatUser,不能addSubscribe注册此次微信用户信息.");
            return 0;
        }
        List<LockUser> lockUserList=lockUserDao.listAllByPhone(phonenumber);
        if(lockUserList.size()>1){
            logger.error("存在多个"+phonenumber+"开锁用户");
            return 0;
        }
        //lockUserDao.listAllByPhone(phonenumber)&lockUserDao.add(lockUser)&wechatUserDao.add(wechatUser)有事务要求
        if (lockUserList.size()==0){
            logger.info("开锁用户"+phonenumber+"不存在,现在创建此用户lockUser(对应表ownerinfo)");
            LockUser lockUser=new LockUser();
            lockUser.setPhonenumber(phonenumber);
            lockUserDao.add(lockUser);
            int lockUserIdTemp=lockUserDao.listAllByPhone(phonenumber).get(0).getId();
            wechatUser.setLockUserId(lockUserIdTemp);
            return wechatUserDao.add(wechatUser);
        }
        if(lockUserList.size()==1){
            wechatUser.setLockUserId(lockUserList.get(0).getId());
            return wechatUserDao.add(wechatUser);
        }
        return 0;
    }
    /*
    public int addSubscribe(WechatUser wechatUser,LockUser lockUser) {
        if (null==wechatUser.getOpenid()){
            logger.warn("wechatUser.openid为空.");
            return 0;
        }
        String phonenumber=lockUser.getPhonenumber();
        if (null==phonenumber){
            logger.warn("lockUser.phonenumber为空.");
            return 0;
        }
        WechatUser wechatUserTemp=wechatUserDao.findWechatUserByOpenid(wechatUser.getOpenid());
        if (null != wechatUserTemp){
            logger.warn("数据库中已存在此wechatUser,不能addSubscribe注册此次微信用户信息.");
            return 0;
        }
        List<LockUser> lockUserList=lockUserDao.listAllByPhone(phonenumber);
        if(lockUserList.size()>1){
            logger.error("存在多个"+phonenumber+"开锁用户");
            return 0;
        }
        //lockUserDao.listAllByPhone(phonenumber)&lockUserDao.add(lockUser)&wechatUserDao.add(wechatUser)有事务要求
        if (lockUserList.size()==0){
            logger.info("开锁用户"+phonenumber+"不存在,现在创建此用户lockUser(对应表ownerinfo)");
            lockUserDao.add(lockUser);
            int lockUserIdTemp=lockUserDao.listAllByPhone(phonenumber).get(0).getId();
            wechatUser.setLockUserId(lockUserIdTemp);
            return wechatUserDao.add(wechatUser);
        }
        if(lockUserList.size()==1){
            wechatUser.setLockUserId(lockUserList.get(0).getId());
            return wechatUserDao.add(wechatUser);
        }
        return 0;
    }
    */

    @Override
    public int updateByOpenid(WechatUser wechatUser) {
        return wechatUserDao.updateByOpenid(wechatUser);
    }

    //根据wechatUser.openid取消注册.
    @Override
    public int UnSubscribe(WechatUser wechatUser) {
        String openid=wechatUser.getOpenid();
        if ("".equals(openid) || null==wechatUser.getOpenid()){
            logger.warn("微信用户未设置openid值,不能删除此次微信用户注册信息.");
            return 0;
        }
        return wechatUserDao.deleteByOpenid(openid);
    }

    @Override
    public WechatUser findWechatUserByopenid(String openid) {
        return wechatUserDao.findWechatUserByOpenid(openid);
    }
}