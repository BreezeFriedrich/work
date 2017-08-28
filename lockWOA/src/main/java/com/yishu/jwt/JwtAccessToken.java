package com.yishu.jwt;

public class JwtAccessToken {
    private String access_token;
    private String token_type;
    private long expiration;
    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getToken_type() {
        return token_type;
    }
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
    public long getExpires_in() {
        return expiration;
    }
    public void setExpires_in(long expires_in) {
        this.expiration = expires_in;
    }
}