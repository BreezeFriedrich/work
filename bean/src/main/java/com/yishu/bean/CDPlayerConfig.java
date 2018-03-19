package com.yishu.bean;

import com.yishu.bean.CompactDisc;
import com.yishu.bean.MediaPlayer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-19 9:52 admin
 * @since JDK1.7
 */
@Configuration
//@ComponentScan
//@ComponentScan("com.yishu.bean")
//@ComponentScan(basePackages = {"com.yishu.bean","com.yishu.pojo"})
@ComponentScan(basePackageClasses = {CompactDisc.class, MediaPlayer.class})
public class CDPlayerConfig {
}
