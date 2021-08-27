package com.jogeen.plugin.boringball.model;

import java.awt.*;

/**
 * @Author jogeen
 * @Date 10:20 2021/8/19
 * @Version v1.0
 * @Description
 */
public class CodeBlock {
    public int logicRow;
    public int contentRow;
    public int x;
    public int y;
    public int width;
    public int height;
    public String text;
    public Rectangle rectangle;

    public Rectangle getRectangle() {

        return new Rectangle(x, y, width, height);
    }
}
