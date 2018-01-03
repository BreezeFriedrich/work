/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-14 16:01 admin
 * @since JDK1.7
 */
public class Records<X> {
    private int totalSize;
    private List<X> rows=new ArrayList<X>();

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<X> getRows() {
        return rows;
    }

    public void setRows(List<X> rows) {
        this.rows = rows;
    }
}
