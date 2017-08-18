package com.yishu.util;

import com.yishu.pojo.AccessToken;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* WechatUtil Tester. 
* 
* @author <Authors name> 
* @since <pre>八月 18, 2017</pre> 
* @version 1.0 
*/ 
public class WechatUtilTest {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getAccessToken() 
* 
*/ 
@Test
public void testGetAccessToken() throws Exception { 
//TODO: Test goes here...
    logger.debug(">>>>> @Test WechatUtil.getAccessToken() ----->----->----->----->");
    AccessToken accessToken= WechatUtil.getAccessToken();
    logger.info("access_token: {token: "+accessToken.getToken()+",expiresIn: "+accessToken.getExpiresIn()+"}");
    logger.debug("<<<<< @Test WechatUtil.getAccessToken() -----<-----<-----<-----<");
} 

/** 
* 
* Method: upload(String filePath, String accessToken, String type) 
* 
*/ 
@Test
public void testUpload() throws Exception { 
//TODO: Test goes here...
    logger.debug(">>>>> @Test WechatUtil.upload() ----->----->----->----->");
    String filePath="C:/Users/admin/Pictures/shy.png";
    AccessToken accessToken=WechatUtil.getAccessToken();
    String type="image";
    WechatUtil.upload(filePath,accessToken.getToken(),type);
    logger.debug("<<<<< @Test WechatUtil.upload() -----<-----<-----<-----<");
} 


} 
