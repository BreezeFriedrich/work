package com.yishu.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/14.
 */
public class PageUtil<T> {
    private List<T> list=new ArrayList<T>();
    private int total=0;

    public PageUtil(List<T> list) {
        this.list = list;
        this.total=list.size();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void remodel(int pageSize,int startIndex){
        List<T> newList=new ArrayList<>(pageSize<list.size()?pageSize:list.size());
        for(int i=startIndex;i<startIndex+pageSize && i<list.size();i++){
            newList.add(list.get(i));
        }
        this.list=newList;
    }

    public static List page(List list,int pageSize, int startIndex){
        List newList=new ArrayList<>(pageSize<list.size()?pageSize:list.size());
        for(int i=startIndex;i<startIndex+pageSize && i<list.size();i++){
            newList.add(list.get(i));
        }
        return newList;
    }
}
