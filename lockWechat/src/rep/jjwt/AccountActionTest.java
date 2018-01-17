package test.com.yishu.action; 

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionProxy;
import com.yishu.action.AccountAction;
import com.yishu.jwt.LoginPara;
import org.apache.struts2.StrutsSpringTestCase;
import org.apache.struts2.StrutsTestCase;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* LoginAction Tester.
* 
* @author <Authors name> 
* @since <pre>九月 6, 2017</pre> 
* @version 1.0 
*/ 
public class AccountActionTest extends StrutsSpringTestCase {
private ActionProxy proxy;
private AccountAction action;

@Override
protected String[] getContextLocations() {
    return new String[]{"spring/spring.xml"};
}

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}

private void init(){
    proxy = getActionProxy("/account/loginAccount.action");
    assertNotNull(proxy);
    action = (AccountAction) proxy.getAction();
    assertNotNull(action);
}

public void testGetActionMapping() throws Exception {
    ActionMapping mapping = getActionMapping("/account/loginAccount.action");
    assertNotNull(mapping);
    assertEquals("/account", mapping.getNamespace());
    assertEquals("loginAccount", mapping.getName());
}

public void testGetActionProxy() throws Exception {
    request.setParameter("loginPara.username","haha");
    request.setParameter("loginPara.password","123456");

    init();
    String result = proxy.execute();
    assertEquals(Action.SUCCESS, result);
    assertEquals("haha", action.getLoginPara().getUsername());
    assertEquals("123456", action.getLoginPara().getPassword());
}

///**
//*
//* Method: getJsonMap()
//*
//*/
//@Test
//public void testGetJsonMap() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: getLoginPara()
//*
//*/
//@Test
//public void testGetLoginPara() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: setLoginPara(LoginPara loginPara)
//*
//*/
//@Test
//public void testSetLoginPara() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: getAuthenticateErrMsg()
//*
//*/
//@Test
//public void testGetAuthenticateErrMsg() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: setAuthenticateErrMsg(String authenticateErrMsg)
//*
//*/
//@Test
//public void testSetAuthenticateErrMsg() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: login()
//*
//*/
//@Test
//public void testLogin() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: getJwtAccessToken(LoginPara loginPara)
//*
//*/
//@Test
//public void testGetJwtAccessTokenLoginPara() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: getJwtAccessToken()
//*
//*/
//@Test
//public void testGetJwtAccessToken() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: setJwtAccessToken(JwtAccessToken jwtAccessToken)
//*
//*/
//@Test
//public void testSetJwtAccessToken() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: getSalt()
//*
//*/
//@Test
//public void testGetSalt() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: setSalt(String salt)
//*
//*/
//@Test
//public void testSetSalt() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: addParam(String s, String s1)
//*
//*/
//@Test
//public void testAddParam() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: setParams(Map<String, String> map)
//*
//*/
//@Test
//public void testSetParams() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: getParams()
//*
//*/
//@Test
//public void testGetParams() throws Exception {
////TODO: Test goes here...
//}
//
///**
//*
//* Method: setSession(Map<String, Object> map)
//*
//*/
//@Test
//public void testSetSession() throws Exception {
////TODO: Test goes here...
//}


} 
