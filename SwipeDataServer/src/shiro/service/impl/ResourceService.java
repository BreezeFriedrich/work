package shiro.service.impl;

import shiro.dao.ResourceDao;
import shiro.model.Resource;
import shiro.service.IResourceService;
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
        return resourceDao.listResource();
    }

    @Override
    public Resource load(int id) {
        return resourceDao.load(id);
    }
}
