package com.liwei.shiro.cache;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * Created by liwei on 16/9/21.
 *
 *
 */

public class BaseCacheService implements InitializingBean{
    /**
     *
     */
    @Autowired
    private CacheManager cacheManager;
    private Cache cache;
    private String cacheName;

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    /**
     *
     *
     *
     *
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        cache = cacheManager.getCache(cacheName);
    }

    
    /**
     * 清空缓存中所有的对象
     */
    public void clear(){
        cache.clear();
    }

    /**
     *
     * @param key
     * @param value
     */
    public void put(String key,Object value){
        cache.put(key,value);
    }

    /**
     *
     * @param key
     */
    public void evict(String key){
        cache.evict(key);
    }

    /**
     *
     * @param key
     * @return
     */
    public Object get(String key){
        Cache.ValueWrapper vw = cache.get(key);
        if(vw!=null){
            return vw.get();
        }
        return null;
    }
}
