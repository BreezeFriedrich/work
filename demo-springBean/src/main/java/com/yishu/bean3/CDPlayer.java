package com.yishu.bean3;

import com.yishu.bean.CompactDisc;
import com.yishu.bean.MediaPlayer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by WindSpring on 2018/3/18.
 */
public class CDPlayer implements MediaPlayer {
    private CompactDisc compactDisc;

    @Autowired
    public void setCompactDisc(CompactDisc compactDisc) {
        this.compactDisc = compactDisc;
    }

    public void play() {
        compactDisc.play();
    }
}
