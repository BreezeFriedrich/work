package com.yishu.pojo;

import com.yishu.jwt.JwtAccessToken;

public class Struts2PortfolioConstants {
    public static User USER;
    public static JwtAccessToken jwtAccessToken;

    public static JwtAccessToken getJwtAccessToken() {
        return jwtAccessToken;
    }

    public static void setJwtAccessToken(JwtAccessToken jwtAccessToken) {
        Struts2PortfolioConstants.jwtAccessToken = jwtAccessToken;
    }

    public User getUser() {
        return USER;
    }

    public void setUser(User user) {
        this.USER = user;
    }
}