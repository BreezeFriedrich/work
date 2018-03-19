package com.yishu.bean3; 

import com.yishu.bean.CompactDisc;
import com.yishu.bean.MediaPlayer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

/** 
* BlankDisc Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 19, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = {"classpath:beans.xml"})
public class XMLBeansTest {
    @Autowired
    private CompactDisc cd;
    @Autowired
    private MediaPlayer player;

    @Test
    public void testXMLBeans() throws Exception {
        player.play();
        assertEquals("Playing Sgt.Pepper's Lonely Hearts Club Band by The Beatles",systemOutRule.getLog());
    }

    @Rule
    public final SystemOutRule systemOutRule=new SystemOutRule().enableLog();

} 
