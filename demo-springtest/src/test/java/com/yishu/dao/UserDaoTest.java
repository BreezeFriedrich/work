package com.yishu.dao;

import com.yishu.model.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-19 16:06 admin
 * @since JDK1.7
 */
//@Transactional
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class UserDaoTest {
    private static final org.slf4j.Logger LOG= LoggerFactory.getLogger("UserDaoTest");

    @Autowired
    private UserDao userDao;
//    @Test
//    public void testUserDao(){
//        List<User> userList=userDao.listUser();
//        LOG.info("Hi! userList:"+userList);
//    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAdd(){
        User user=new User();
        user.setNickname("二弟");
        user.setUsername("dandan");
        user.setPassword("123456");
        user.setStatus(0);
        int result=userDao.add(user);
        LOG.info("add result:"+result);
    }

    @Ignore//忽略测试
    @Test
    public void testDeleteByUserName(){
        int result=userDao.deleteByUserName("dandan");
        LOG.info("deleteByUserName result:"+result);
    }
}
