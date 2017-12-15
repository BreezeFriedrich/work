/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.pojo;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-12-15 14:23 admin
 * @since JDK1.7
 */
public class DataWithNote<X,Y> {
    private X data;
    private String key;
    private int size;
    private Y note;

    public X getData() {
        return data;
    }

    public void setData(X data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Y getNote() {
        return note;
    }

    public void setNote(Y note) {
        this.note = note;
    }
}
