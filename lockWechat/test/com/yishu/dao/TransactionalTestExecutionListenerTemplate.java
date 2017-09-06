/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.dao;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
* Dao层的test case 的 基类,用来引入spring context 和 类型注解.
* 
* @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a> 
* @since JDK1.7 
* @version 1.0.0.0 2017-09-06 14:41 admin 
*/
@ContextConfiguration(locations = {"classpath:spring/spring.xml"})
public class TransactionalTestExecutionListenerTemplate extends AbstractTransactionalJUnit4SpringContextTests{
}

/*同于下:
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
@ContextConfiguration(locations = {"classpath:spring/spring.xml"})
public class BasicTest extends AbstractJUnit4SpringContextTests{
}
 */