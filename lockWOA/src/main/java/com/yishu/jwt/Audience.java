package com.yishu.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("audience")
@Configuration
@PropertySource(value = "classpath:jwt.properties",ignoreResourceNotFound = ture)
public class Audience {
    //注册到相应的环境(jdk\spring\servlet)对象env中.可用env.getProperty("key")获取.
//    @Autowired
//    Environment env;

    @Value("${audience.clientId:dtnsge}")
    private String clientId;

    @Value("${audience.base64Secret}")
    private String base64Secret;

    @Value("${audience.name}")
    private String name;

    @Value("${audience.expiresSecond:300000}")
    private int expiresSecond;

    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getBase64Secret() {
        return base64Secret;
    }
    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getExpiresSecond() {
        return expiresSecond;
    }
    public void setExpiresSecond(int expiresSecond) {
        this.expiresSecond = expiresSecond;
    }
}