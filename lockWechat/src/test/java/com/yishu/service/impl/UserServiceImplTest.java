package com.yishu.service.impl;

import static org.junit.Assert.*;
import com.yishu.dao.SpringJUnit4ClassRunnerTemplate;
import com.yishu.pojo.User;
import com.yishu.service.IUserService;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* UserServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>九月 6, 2017</pre> 
* @version 1.0 
*/ 
public class UserServiceImplTest extends SpringJUnit4ClassRunnerTemplate {

@Autowired
private IUserService userService;

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
    User user = userService.findUserByName(userName);
    assertNotNull(userName+" 帐号不存在",user);
} 


}