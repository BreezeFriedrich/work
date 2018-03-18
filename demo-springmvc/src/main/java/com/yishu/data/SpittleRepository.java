package com.yishu.data;

import com.yishu.Spittle;

import java.util.List;

/**
 * Created by WindSpring on 2018/3/18.
 */
public interface SpittleRepository {
    List<Spittle> findSpittles(long max, int count);
}
