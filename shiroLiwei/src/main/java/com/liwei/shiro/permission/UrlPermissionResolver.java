package com.liwei.shiro.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Liwei on 2016/9/19.
 */
public class UrlPermissionResolver implements PermissionResolver {
    private static final Logger logger = LoggerFactory.getLogger(UrlPermissionResolver.class);

    /**
     * 经过调试发现
     *
     *
     * @param s
     * @return
     */
    @Override
    public Permission resolvePermission(String s) {
        logger.debug("s => " + s);

        if(s.startsWith("/")){
            return new UrlPermission(s);
        }
        return new WildcardPermission(s);
    }
}
