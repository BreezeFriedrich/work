package com.yishu.bean3;

import com.yishu.bean.CompactDisc;

import java.util.List;

/**
 * Created by WindSpring on 2018/3/18.
 */
public class Discography {
    private String artist;
    private List<CompactDisc> cds;

    public Discography(String artist, List<CompactDisc> cds) {
        this.artist = artist;
        this.cds = cds;
    }
}
