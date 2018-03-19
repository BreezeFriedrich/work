package com.yishu.bean2;

import com.yishu.bean.CDPlayer;
import com.yishu.bean.CompactDisc;
import com.yishu.bean.MediaPlayer;
import com.yishu.bean.SgtPeppers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 装配机制 2.JavaConfig显示装配
 */
@Configuration
public class CDPlayerConfig2 {
    @Bean(name="lonelyHeartsClubBand")
    public CompactDisc sgtPeppers(){
        return new SgtPeppers();
    }

//    @Bean
//    public CompactDisc randomBeatlesCD(){
//        int choice=(int)Math.floor(Math.random()*4);
//        if(choice==0){
//            return new SgtPeppers();
//        }else if(choice==1){
//            return new WhiteAlbum();
//        }else if(choice==2){
//            return new HardDaysNight();
//        }
//        else {
////            return new Revolver();
//            return new HardDaysNight();
//        }
//    }

//    @Bean
//    public MediaPlayer cdPlayer(){
//        return new CDPlayer(sgtPeppers());//JavaConfig注入方式1.调用产生bean的方法注入该bean.
//    }
//    @Bean
//    public MediaPlayer cdPlayer(CompactDisc cd){
//        return new CDPlayer(cd);//JavaConfig注入方式2.请求bean参数注入.//构造器实现DI.
//    }
    @Bean
    public MediaPlayer cdPlayer(CompactDisc cd){
        CDPlayer player=new CDPlayer();
//        player.setCompactDisc(cd);
        player.insertCompactDisc(cd);//JavaConfig注入方式3.请求bean参数注入.//任意方法返回该bean(包括Setter)实现DI.
        return player;
    }
}
