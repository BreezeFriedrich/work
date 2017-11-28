/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-27 17:17 admin
 * @since JDK1.7
 */
public class FilterUtil<T> {
    private List<T> list=null;
    private int total=0;

//    Class XCls=x.getClass();
//    Field[] fields=XCls.getDeclaredFields();
    /*
    public List<X> filterByField(Field field){
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();//获取当前new对象的泛型的父类类型
        Class clazz = (Class<X>) parameterizedType.getActualTypeArguments()[0];
        Field[] fields=clazz.getDeclaredFields();
        for(Field i :fields){
            if ()
        }
    }
    */

    /*
    public List<T> filter(T t){
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();//获取当前new对象的泛型的父类类型
        Class clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        Field[] fields=clazz.getDeclaredFields();
        for(Field i :fields){
            if ()
        }
    }
    */
}
