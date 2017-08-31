package com.yishu.jwt;

public enum ResultStatusCode {
    OK(0, "OK"),
    SYSTEM_ERR(30001, "System error"),

    //错误码
    INVALID_CLIENTID(30003, "Invalid clientid"),
    INVALID_PASSWORD(30004, "User name or password is incorrect"),
    INVALID_CAPTCHA(30005, "Invalid captcha or captcha overdue"),
    INVALID_TOKEN(30006, "Invalid token"),

    PERMISSION_DENIED(30007,"PERMISSION_DENIED");

    private int errcode;
    private String errmsg;

    ResultStatusCode(int errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
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
}