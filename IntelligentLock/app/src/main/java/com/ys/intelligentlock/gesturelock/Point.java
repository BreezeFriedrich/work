package com.ys.intelligentlock.gesturelock;

/**
 * Created by admin on 2017/7/10.
 */

// gesture lock point

public class Point  {
    public static int STATE_UNCHECKED=0;
    public static int STATE_CHECKED=1;
    public static int STATE_ERROR=2;

    private int x;
    private int y;
    private int row;
    private int column;
    private int index;

    public Point(int x,int y,int row,int column,int index){
        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;
        this.index = index;
    }
}
