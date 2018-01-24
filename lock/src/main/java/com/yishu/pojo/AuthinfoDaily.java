package com.yishu.pojo;

import java.util.ArrayList;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-22 18:14 admin
 * @since JDK1.7
 */
public class AuthinfoDaily {
    private String date;
//    private int[] idIndex;
//    private int[] pwdIndex;
    private ArrayList<Integer> idIndexes;
    private ArrayList<Integer> pwdIndexes;
    private long time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Integer> getIdIndexes() {
        return idIndexes;
    }

    public void setIdIndexes(ArrayList<Integer> idIndexes) {
        this.idIndexes = idIndexes;
    }

    public ArrayList<Integer> getPwdIndexes() {
        return pwdIndexes;
    }

    public void setPwdIndexes(ArrayList<Integer> pwdIndexes) {
        this.pwdIndexes = pwdIndexes;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
