package com.yishu.bean;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

//import org.junit.contrib.java.lang.system.StandardOutputStreamLog;

/** 
* CDPlayerConfig Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 17, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig.class)
public class CDPlayerTest {
    @Autowired
    private CompactDisc cd;
    @Test
    public void testCompactDisc(){
        assertNotNull(cd);
        cd.play();
    }

    @Autowired
    private MediaPlayer player;
    @Test
    public void testMediaPlayer(){
        player.play();
        assertEquals("Playing Sgt.Pepper's Lonely Hearts Club Band by The Beatles",log.getLog());
        assertEquals("Playing Sgt.Pepper's Lonely Hearts Club Band by The Beatles",systemOutRule.getLog());
    }

    @Rule
    public final StandardOutputStreamLog log=new StandardOutputStreamLog();
    @Rule
    public final SystemOutRule systemOutRule=new SystemOutRule().enableLog();
}
