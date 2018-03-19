package com.yishu.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-19 9:50 admin
 * @since JDK1.7
 */
@Component
//@Named
public class CDPlayer implements MediaPlayer {
    private CompactDisc compactDisc;

    public CDPlayer() {
    }

//    @Autowired
    public CDPlayer(CompactDisc compactDisc) {
        this.compactDisc = compactDisc;
    }

//    @Autowired
    public void setCompactDisc(CompactDisc compactDisc) {
        this.compactDisc = compactDisc;
    }

//    @Autowired
//    @Inject
//    @Resource
    public void insertCompactDisc(CompactDisc compactDisc){
        this.compactDisc=compactDisc;
    }

    public void play() {
        compactDisc.play();
    }
}
