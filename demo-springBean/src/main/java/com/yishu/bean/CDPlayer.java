package com.yishu.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import javax.inject.Named;
//import javax.inject.Inject;


/**
 * Created by WindSpring on 2018/3/18.
 */
@Component
//@Named
public class CDPlayer implements MediaPlayer {
    private CompactDisc cd;
    @Autowired
    public CDPlayer(CompactDisc cd){
        this.cd=cd;
    }
    @Autowired
    public void setCompactDisc(CompactDisc cd){
        this.cd=cd;
    }
    @Autowired
//    @Inject
    public void insertCompactDisc(CompactDisc cd){
        this.cd=cd;
    }

    public void play() {
        cd.play();
    }
}
