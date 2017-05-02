package com.yishu.shiro.service.impl;

import com.yishu.shiro.dao.ResourceDao;
import com.yishu.shiro.model.Resource;
import com.yishu.shiro.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */
@Service
public class ResourceService implements IResourceService {
    @Autowired
    private ResourceDao resourceDao;


    @Override
    public Integer add(Resource res) {
        return resourceDao.add(res);
    }

    @Override
    public Integer delete(int id) {
        return resourceDao.delete(id);
    }

    @Override
    public Integer update(Resource res) {
        return resourceDao.update(res);
    }

    @Override
    public List<Resource> listResource() {
        return resourceDao.listResoure();
    }

    @Override
    public Resource load(int id) {
        return resourceDao.load(id);
    }
}
