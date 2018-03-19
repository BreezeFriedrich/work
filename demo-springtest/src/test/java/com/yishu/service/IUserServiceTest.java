package com.yishu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-19 14:24 admin
 * @since JDK1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class IUserServiceTest {
    private static final Logger LOG=LoggerFactory.getLogger("IUserServiceTest");

    @Autowired
    private IUserService userService;
    @Test
    public void testIUserService() throws Exception {
        List userList=userService.listUser();
        LOG.info("userList:"+userList);
    }
}
