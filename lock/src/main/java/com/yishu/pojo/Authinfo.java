package com.yishu.pojo;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-22 16:12 admin
 * @since JDK1.7
 */
public class Authinfo {
    private AuthinfoDaily[] authinfoDaily;
    private IdentityCard[] ids;
    private UnlockPwd[] pwds;

    public AuthinfoDaily[] getAuthinfoDaily() {
        return authinfoDaily;
    }

    public void setAuthinfoDaily(AuthinfoDaily[] authinfoDaily) {
        this.authinfoDaily = authinfoDaily;
    }

    public IdentityCard[] getIds() {
        return ids;
    }

    public void setIds(IdentityCard[] ids) {
        this.ids = ids;
    }

    public UnlockPwd[] getPwds() {
        return pwds;
    }

    public void setPwds(UnlockPwd[] pwds) {
        this.pwds = pwds;
    }
}

/*
Authinfo{
    [{String,int[],int[]},{},...],
        list,list
}
*/