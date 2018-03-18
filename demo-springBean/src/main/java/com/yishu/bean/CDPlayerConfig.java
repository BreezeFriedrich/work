package com.yishu.bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 装配机制 1.隐式自动化装配bean : 组件扫描(@ComponentScan+@Component)+自动装配(@Autowired)
 */
@Configuration
//@ComponentScan
//@ComponentScan("com.yishu.bean")
//@ComponentScan(basePackages = {"com.yishu.bean","com.yishu.pojo"})//扫描多个包
@ComponentScan(basePackageClasses = {CompactDisc.class,SgtPeppers.class})//自动扫描应用类或其接口
public class CDPlayerConfig {
}
