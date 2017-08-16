package com.yishu.pojo;

/**
 * Entity for: Wechat access_token & jsapi_token
 */
public class WechatTokenAndTicket {
    private String access_token;
    private long token_ctime; //access_token 创建时间
    private long token_expiresIn; //access_token 失效时间
    private String jsapi_ticket; //js api token
    private long ticket_ctime;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getToken_ctime() {
        return token_ctime;
    }

    public void setToken_ctime(long token_ctime) {
        this.token_ctime = token_ctime;
    }

    public long getToken_expiresIn() {
        return token_expiresIn;
    }

    public void setToken_expiresIn(long token_expiresIn) {
        this.token_expiresIn = token_expiresIn;
    }

    public String getJsapi_ticket() {
        return jsapi_ticket;
    }

    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
    }

    public long getTicket_ctime() {
        return ticket_ctime;
    }

    public void setTicket_ctime(long ticket_ctime) {
        this.ticket_ctime = ticket_ctime;
    }
}