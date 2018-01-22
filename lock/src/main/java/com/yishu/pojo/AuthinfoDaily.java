package com.yishu.pojo;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-22 18:14 admin
 * @since JDK1.7
 */
public class AuthinfoDaily {
    private String date;
    private int[] idIndex;
    private int[] pwdIndex;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int[] getIdIndex() {
        return idIndex;
    }

    public void setIdIndex(int[] idIndex) {
        this.idIndex = idIndex;
    }

    public int[] getPwdIndex() {
        return pwdIndex;
    }

    public void setPwdIndex(int[] pwdIndex) {
        this.pwdIndex = pwdIndex;
    }
}
