package com.yishu.shiro.impl;

import com.yishu.shiro.model.User;
import com.yishu.shiro.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liwei on 16/9/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring/spring-service.xml",
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-shiro.xml"})
public class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private IUserService userService;


    @Test
    public void add() throws Exception {
        User user = new User();
        user.setUsername("zhouguang");
        user.setPassword("666666");
        user.setNickname("周光1");
        user.setStatus(1);
        userService.add(user);
        logger.debug("返回自增长的主键：" + user.getId());
    }

}