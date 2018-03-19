package com.yishu.bean;

import org.springframework.stereotype.Component;

/**
 * @author <a href="http://www.yishutech.com">Nanjing yishu information technology co., LTD</a>
 * @version 1.0.0.0 2018-03-19 9:53 admin
 * @since JDK1.7
 */
@Component
public class SgtPeppers implements CompactDisc {
    private String title="Sgt.Pepper's Lonely Hearts Club Band";
    private String artist="The Beatles";
    public void play() {
        System.out.print("Playing "+title+" by "+artist);
    }
}
