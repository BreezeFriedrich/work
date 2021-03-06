package com.yishu.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
//import javax.inject.Named;
//import javax.inject.Inject;


/**
 * Created by WindSpring on 2018/3/18.
 */
@Component
//@Named
public class CDPlayer implements MediaPlayer {
    private CompactDisc compactDisc;

    public CDPlayer() {
    }

    @Autowired
//    @Inject
    public CDPlayer(CompactDisc compactDisc){
        this.compactDisc=compactDisc;
    }

    @Autowired
    public void setCompactDisc(CompactDisc compactDisc) {
        this.compactDisc = compactDisc;
    }

    @Autowired
    public void insertCompactDisc(CompactDisc compactDisc){
        this.compactDisc=compactDisc;
    }

    public void play() {
        compactDisc.play();
    }
}
