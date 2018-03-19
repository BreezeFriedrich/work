package com.yishu.bean2;

import com.yishu.bean.CDPlayer;
import com.yishu.bean.CompactDisc;
import com.yishu.bean.MediaPlayer;
import com.yishu.bean.SgtPeppers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.print.attribute.standard.Media;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-19 10:30 admin
 * @since JDK1.7
 */
@Configuration
public class CDPlayerConfig2 {
    @Bean
    public CompactDisc sgtPeppers(){
        return new SgtPeppers();
    }

    //返回bean的方法注入bean
//    @Bean
//    public MediaPlayer cdPlayer(){
//        return new CDPlayer(sgtPeppers());
//    }

    //有参构造函数注入bean
//    @Bean
//    public MediaPlayer cdPlayer(CompactDisc compactDisc){
//        return new CDPlayer(compactDisc);
//    }

    @Bean
    public MediaPlayer cdPlayer(CompactDisc compactDisc){
        CDPlayer player=new CDPlayer();
//        player.setCompactDisc(compactDisc);
        player.insertCompactDisc(compactDisc);
        return player;
    }
}
