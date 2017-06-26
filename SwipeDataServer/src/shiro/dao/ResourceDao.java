package shiro.dao;

import shiro.model.Resource;

import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */
public interface ResourceDao {

    Integer add(Resource res);
    Integer delete(int id);
    Integer update(Resource res);
    List<Resource> listResource();
    Resource load(int id);

}
