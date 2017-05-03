package com.yishu.shiro.dao;

import com.yishu.shiro.model.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */
public interface ResourceDao {
    Integer add(Resource res);

    Integer update(Resource res);

    Integer delete(int id);

    Resource load(int id);

    List<Resource> listResource();
}
