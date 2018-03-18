package com.yishu.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Created by WindSpring on 2018/3/18.
 */
public class SpringmvcWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RootConfig.class};//此处返回的带有@Configuration注解的类,用来配置ContextLoaderListener创建的应用上下文中的bean.
    }

    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};//此处返回的带有@Configuration类,用来定义DispatcherServlet应用上下文中的bean.
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
