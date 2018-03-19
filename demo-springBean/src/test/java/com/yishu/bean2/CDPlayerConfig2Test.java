package com.yishu.bean2; 

import com.yishu.bean.CompactDisc;
import com.yishu.bean.MediaPlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** 
* CDPlayerConfig2 Tester. 
* 
* @author <Authors name> 
* @since <pre>三月 19, 2018</pre> 
* @version 1.0 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CDPlayerConfig2.class)
public class CDPlayerConfig2Test {
    @Autowired
    private CompactDisc cd;
    @Autowired
    private MediaPlayer player;

    @Test
    public void testCDPlayerConfig2() throws Exception {
        player.play();
    }

}
