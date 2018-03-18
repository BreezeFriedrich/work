package com.yishu.bean2;

import com.yishu.bean.CompactDisc;

/**
 * Created by WindSpring on 2018/3/18.
 */
public class HardDaysNight implements CompactDisc {
    private String title="Sgt.Pepper's HardDaysNight";
    private String artist="The Beatles";

    public void play() {
        System.out.println(this+" -> Playing "+title+" by "+artist);
    }
}
