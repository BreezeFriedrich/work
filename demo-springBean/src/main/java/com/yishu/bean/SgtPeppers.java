package com.yishu.bean;

import org.springframework.stereotype.Component;

/**
 * Created by WindSpring on 2018/3/17.
 */
@Component("lonelyHeartsClub")
public class SgtPeppers implements CompactDisc {
    private String title="Sgt.Pepper's Lonely Hearts Club Band";
    private String artist="The Beatles";

    public void play() {
        System.out.println("Playing "+title+" by "+artist);
    }
}
