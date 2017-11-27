/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-27 16:58 admin
 * @since JDK1.7
 */
public class PageUtil<X> {
    private List<X> list=null;
    private int total=0;

    public PageUtil(List<X> list) {
        this.list = list;
        this.total=list.size();
    }

    public List<X> getList() {
        return list;
    }

    public void setList(List<X> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void remodel(int pageSize,int pageNum){
        List<X> newList=new ArrayList<>(pageSize<list.size()?pageSize:list.size());
        int startIndex=pageSize*(pageNum-1);
        for(int i=startIndex;i<startIndex+pageSize && i<list.size();i++){
            newList.add(list.get(i));
        }
        this.list=newList;
    }
}
