/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.dao;

import com.yishu.pojo.User;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
* UserDaoTest Tester.
* 
* @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a> 
* @since JDK1.7 
* @version 1.0.0.0 2017-09-06 15:11 admin 
*/
//DAO层测试可以添加@Transactional,用来回滚而不影响数据库.
@Transactional
public class UserDaoTest extends SpringJUnit4ClassRunnerTemplate{
    @Autowired
    private UserDao userDao;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: findUserByName(String username)
     *
     */
    @Test
    public void testFindUserByName() throws Exception {
        String userName="haha";
        User user=userDao.findUserByName(userName);
        //user为notNull时表示成功通过断言无输出,并继续测试
        //user为null时测试失败,中断测试并抛出Exception 和信息 message
        assertNotNull(userName+" 帐号不存在",user);
    }
}