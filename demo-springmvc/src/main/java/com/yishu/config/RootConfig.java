package com.yishu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by WindSpring on 2018/3/18.
 */
@Configuration
@ComponentScan(basePackages = {"com.yishu"},
        excludeFilters = {@Filter(type= FilterType.ANNOTATION,value= EnableWebMvc.class)}
        )
public class RootConfig {
}
