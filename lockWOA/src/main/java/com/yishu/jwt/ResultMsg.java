package com.yishu.jwt;

public class ResultMsg {
    private int errcode;
    private String errmsg;
    private Object jwtAccessToken;

    public ResultMsg(int errcode, String errmsg, Object jwtAccessToken) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.jwtAccessToken = jwtAccessToken;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Object getJwtAccessToken() {
        return jwtAccessToken;
    }

    public void setJwtAccessToken(Object jwtAccessToken) {
        this.jwtAccessToken = jwtAccessToken;
    }
}