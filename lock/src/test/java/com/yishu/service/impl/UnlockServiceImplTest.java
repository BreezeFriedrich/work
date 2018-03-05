package com.yishu.service.impl; 

import com.yishu.SpringJUnit4ClassRunnerTemplate;
import com.yishu.pojo.UnlockAuthorization;
import com.yishu.service.IUnlockService;
import net.bull.javamelody.internal.common.LOG;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/** 
* UnlockServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>一月 23, 2018</pre> 
* @version 1.0 
*/ 
public class UnlockServiceImplTest extends SpringJUnit4ClassRunnerTemplate {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UnlockServiceImplTest.class);

@Autowired
private IUnlockService unlockService;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getUnlockId(String ownerPhoneNumber, String gatewayCode, String lockCode) 
* 
*/ 
@Test
public void testGetUnlockId() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: authUnlockById(String ownerPhoneNumber, String gatewayCode, String lockCode, String name, String cardNumb, String dnCode, String startTime, String endTime) 
* 
*/ 
@Test
public void testAuthUnlockById() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: prohibitUnlockById(String ownerPhoneNumber, String lockCode, String cardNumb, String serviceNumb) 
* 
*/ 
@Test
public void testProhibitUnlockById() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getUnlockPwd(String ownerPhoneNumber, String gatewayCode, String lockCode) 
* 
*/ 
@Test
public void testGetUnlockPwd() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: authUnlockByPwd(String ownerPhoneNumber, String gatewayCode, String lockCode, String password, String startTime, String endTime) 
* 
*/ 
@Test
public void testAuthUnlockByPwd() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: prohibitUnlockByPwd(String ownerPhoneNumber, String gatewayCode, String lockCode, String serviceNumb) 
* 
*/ 
@Test
public void testProhibitUnlockByPwd() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getUnlockAuthorization(String ownerPhoneNumber, String gatewayCode, String lockCode) 
* 
*/ 
@Test
public void testGetUnlockAuthorization() throws Exception { 
//TODO: Test goes here...
    UnlockAuthorization unlockAuthorization=unlockService.getUnlockAuthorization("18255683932","GWH0081702000003","LCN0011721000001");
    System.out.println("method-getUnlockAuthorization:");
    LOG.warn("warnmsg");
} 

/** 
* 
* Method: getUnlockAuthorizationDailyArr(UnlockAuthorization unlockAuthorization, final String startTime, String endTime) 
* 
*/ 
@Test
public void testGetUnlockAuthorizationDailyArr() throws Exception { 
//TODO: Test goes here...

    String startTime="1516690521839";
    String endTime="1517744770921";
//    unlockService.getUnlockAuthorization();
//    unlockService.getUnlockAuthorizationDailyArr()
//    int indexStart=0;
//    int indexEnd=0;
//    indexStart=1L*startTime/86400000;
//    System.out.println();
} 

/** 
* 
* Method: getMillDiffFromTime(String timeStr) 
* 
*/ 
@Test
public void testGetMillDiffFromTime() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: getServiceNumb(String ownerPhoneNumber, String timetag) 
* 
*/ 
@Test
public void testGetServiceNumb() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = UnlockServiceImpl.getClass().getMethod("getServiceNumb", String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: respFail() 
* 
*/ 
@Test
public void testRespFail() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = UnlockServiceImpl.getClass().getMethod("respFail"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
