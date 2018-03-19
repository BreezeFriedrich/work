package com.yishu.service;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-19 14:24 admin
 * @since JDK1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class IUserServiceTest {
    private static final Logger LOG=LoggerFactory.getLogger(IUserServiceTest.class);
    static{
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.reset();
        try {
            configurator.doConfigure("E:/workspace/gitYishu/demo-springtest/src/main/resources/logback.xml");//加载logback配置文件
        } catch (JoranException e) {
            e.printStackTrace();
        }
        //PropertyConfigurator.configure("/log4j.properties");//加载logj配置文件
    }

    @Autowired
    private IUserService userService;
    @Test
    public void testIUserService() throws Exception {
        List userList=userService.listUser();
        LOG.info("userList:"+userList);
    }
}
