package com.yishu.service.impl; 

import com.yishu.dao.UserDao;
import com.yishu.model.User;
import com.yishu.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/** 
* UserService Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 19, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserServiceTest {
    private static final Logger LOG= LoggerFactory.getLogger("UserServiceTest");

    @Autowired
    private UserDao userDao;
    @Test
    public void testUserDao(){
        List<User> userList=userDao.listUser();
        LOG.info("userList:"+userList);
    }

    @Autowired
    private IUserService userService;
    @Test
    public void testIUserService() throws Exception {
        List userList=userService.listUser();
        LOG.info("userList:"+userList);
    }
} 
