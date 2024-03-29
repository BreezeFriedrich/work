package com.yishu.shiro.realm;

import com.yishu.shiro.model.Resource;
import com.yishu.shiro.model.User;
import com.yishu.shiro.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by admin on 2017/4/26.
 */
public class MyRealm extends AuthorizingRealm {

    private static final Logger logger= LoggerFactory.getLogger(MyRealm.class);
    @Autowired
    private IUserService userService;

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("--- MyRealm doGetAuthenticationInfo ---");
        String username=authenticationToken.getPrincipal().toString();
        String password=new String((char[]) authenticationToken.getCredentials());

        // 可以在登录的逻辑里面抛出各种异常
        // 再到 subject.login(token) 里面去捕获对应的异常

        User user=userService.login(username,password);
        if (user!=null){
            SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user,user.getPassword(),getName());
            info.setCredentialsSalt(ByteSource.Util.bytes(username.getBytes()));
            return info;
        }
        return null;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("--- MyRealm doGetAuthorizationInfo ---");

        User user=(User) principalCollection.getPrimaryPrincipal();
        Integer userId=user.getId();
        List<Resource> resourceList=userService.listAllResource(userId);
        List<String> roleSnList =userService.listRoleSnByUser(userId);

        List<String> resStrList=new ArrayList<>();
        for (Resource resource:resourceList){
            resStrList.add(resource.getUrl());
        }

        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setRoles(new HashSet<>(roleSnList));
        info.setStringPermissions(new HashSet<>(resStrList));

        logger.debug("role => " + roleSnList);
        logger.debug("permission => " + resStrList);
        return info;
    }

    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        Cache c=getAuthenticationCache();
        logger.info("before clear authtication cache");
        for(Object o : c.keys()){
            logger.info( o + " , " + c.get(o));
        }
        super.clearCachedAuthenticationInfo(principals);
        logger.info("after clear authtication cache by parent class");
        for(Object o : c.keys()){
            logger.info( o + " , " + c.get(o));
        }

        User user=(User)principals.getPrimaryPrincipal();
        SimplePrincipalCollection spc=new SimplePrincipalCollection(user.getUsername(),getName());
        super.clearCachedAuthenticationInfo(spc);
        logger.info("添加了代码清除【认证】缓存之后");
        int cacheSize = c.keys().size();
        logger.info("【认证】缓存的大小:" + c.keys().size());
        if (cacheSize == 0){
            logger.info("说明【认证】缓存被清空了。");
        }
    }

    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        logger.info("清除【授权】缓存之前");
        Cache c = getAuthorizationCache();
        for(Object o : c.keys()){
            logger.info( o + " , " + c.get(o));
        }
        super.clearCachedAuthorizationInfo(principals);
        logger.info("清除【授权】缓存之后");
        int cacheSize = c.keys().size();
        logger.info("【授权】缓存的大小:" + cacheSize);

        for(Object o : c.keys()){
            logger.info( o + " , " + c.get(o));
        }
        if(cacheSize == 0){
            logger.info("说明【授权】缓存被清空了。");
        }
    }

}
