package com.jogeen.plugin.boringball.model;

import java.awt.*;

/**
 * @Author jogeen
 * @Date 15:19 2021/8/18
 * @Version v1.0
 * @Description
 */
public class Ball {

    public static final int REBOUND_TYPE_X = 0;
    public static final int REBOUND_TYPE_Y = 1;


    public int x; //原点X坐标
    public int y; //原点y坐标
    public int r; //半径
    public Color color; //颜色
    public int xv; //x方向速度
    public int yv; //y方向速度

    public Ball(int x, int y, int r, Color color) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
    }

    public Ball(int x, int y, int r, Color color, int xv, int yv) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
        this.xv = xv;
        this.yv = yv;
    }

    public Point getPoint() {
        return new Point(x, y);
    }

    public void move() {
        this.x += xv;
        this.y += yv;
    }

    public void rebound(int type) {
        if (REBOUND_TYPE_X == type) {
            this.xv = -this.xv;
        } else if (REBOUND_TYPE_Y == type) {
            this.yv = -this.yv;
        }
    }


}
