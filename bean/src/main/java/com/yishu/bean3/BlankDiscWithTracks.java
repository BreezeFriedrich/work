package com.yishu.bean3;

import com.yishu.bean.CompactDisc;

import java.util.List;

/**
 * Created by WindSpring on 2018/3/19.
 */
public class BlankDiscWithTracks implements CompactDisc {
    private String title;
    private String artist;
    private List<String> tracks;

    public BlankDiscWithTracks(String title, String artist, List<String> tracks) {
        this.title = title;
        this.artist = artist;
        this.tracks = tracks;
    }

    public void play() {
        System.out.println("Playing "+title+" by "+artist);
        for(String track:tracks){
            System.out.println("-Track: "+track);
        }
    }
}
