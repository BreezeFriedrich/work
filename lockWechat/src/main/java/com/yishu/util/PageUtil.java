package com.yishu.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/14.
 */
public class PageUtil<X> {
    private List<X> list=new ArrayList<X>();
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

    public void remodel(int pageSize,int startIndex){
        List<X> newList=new ArrayList<>(pageSize<list.size()?pageSize:list.size());
        for(int i=startIndex;i<startIndex+pageSize && i<list.size();i++){
            newList.add(list.get(i));
        }
        this.list=newList;
    }
}
