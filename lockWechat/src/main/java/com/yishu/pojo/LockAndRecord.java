package com.yishu.pojo;

import java.util.List;

/**
 * Created by WindSpring on 2018/4/4.
 */
public class LockAndRecord {
    private Lock lock;
    private List<UnlockRecord> unlockRecords;
    private int size;

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public List<UnlockRecord> getUnlockRecords() {
        return unlockRecords;
    }

    public void setUnlockRecords(List<UnlockRecord> unlockRecords) {
        this.unlockRecords = unlockRecords;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
