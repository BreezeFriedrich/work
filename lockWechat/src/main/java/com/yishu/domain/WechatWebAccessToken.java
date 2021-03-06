/*
 * Copyright (C) 2006-${YEAR} 南京亿数信息科技有限公司 版权所有
 * Nanjing yishu information technology co., LTD. All Rights Reserved.
 */

package com.yishu.domain;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2017-11-03 17:44 admin
 * @since JDK1.7
 */
public class WechatWebAccessToken {
//    access_token	网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
//    expires_in	access_token接口调用凭证超时时间，单位（秒）
//    refresh_token	用户刷新access_token
//    openid	用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
//    scope	用户授权的作用域，使用逗号（,）分隔
    private String access_token,refresh_token,openid,scope;
    private int expires_in;
    private long fetchtime,deadline;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public long getFetchtime() {
        return fetchtime;
    }

    public void setFetchtime(long fetchtime) {
        this.fetchtime = fetchtime;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }
}