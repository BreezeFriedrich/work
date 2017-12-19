/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.util;

/**
 * 用来给FilterList做函数钩子的接口.
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-28 9:35 admin
 * @since JDK1.7
 */
public interface FilterListHook<T> {
    public boolean test(T t);
}
