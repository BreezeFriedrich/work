package com.yishu.pojo;

import java.util.List;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-01-17 10:27 admin
 * @since JDK1.7
 */
public class UnlockAuthorization {
    private List<IdentityCard> unlockIds;
    private UnlockPwds unlockPwds;
    private int unlockIdSize;
    private int unlockPwdSize;

    public List<IdentityCard> getUnlockIds() {
        return unlockIds;
    }

    public void setUnlockIds(List<IdentityCard> unlockIds) {
        this.unlockIds = unlockIds;
    }

    public UnlockPwds getUnlockPwds() {
        return unlockPwds;
    }

    public void setUnlockPwds(UnlockPwds unlockPwds) {
        this.unlockPwds = unlockPwds;
    }

    public int getUnlockIdSize() {
        return unlockIdSize;
    }

    public void setUnlockIdSize(int unlockIdSize) {
        this.unlockIdSize = unlockIdSize;
    }

    public int getUnlockPwdSize() {
        return unlockPwdSize;
    }

    public void setUnlockPwdSize(int unlockPwdSize) {
        this.unlockPwdSize = unlockPwdSize;
    }

    public UnlockAuthorization getUnlockAuthorization(List<IdentityCard> unlockIds, UnlockPwds unlockPwds){
        this.unlockIds=unlockIds;
        this.unlockPwds=unlockPwds;
        this.unlockIdSize=unlockIds.size();
        this.unlockPwdSize=unlockPwds.getPasswordList().size();
        return this;
    }
}
