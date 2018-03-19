package com.yishu.bean; 

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/** 
* CDPlayerConfig Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 19, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig.class)
public class CDPlayerConfigTest {
    @Autowired
    private CompactDisc cd;
    @Autowired
    private MediaPlayer player;

    @Test
    public void testCDPlayer(){
        player.play();
        assertEquals("Playing Sgt.Pepper's Lonely Hearts Club Band by The Beatles",systemOutRule.getLog());
    }
    @Rule
    public final SystemOutRule systemOutRule=new SystemOutRule().enableLog();
} 
