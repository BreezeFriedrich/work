package com.yishu.pojo;

import java.util.List;

/**
 * Created by WindSpring on 2018/4/4.
 */
public class RoomAndRecord {
    private Room room;
    private List<UnlockRecord> unlockRecords;
    private int size;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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
