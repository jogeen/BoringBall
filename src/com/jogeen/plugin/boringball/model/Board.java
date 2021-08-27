package com.jogeen.plugin.boringball.model;

import java.util.List;

/**
 * @Author jogeen
 * @Date 15:17 2021/8/18
 * @Version v1.0
 * @Description
 */
public class Board {
    public int[][] grid;
    public int x;
    public int y;

    public Board(int x, int y, List<CodeBlock> codeBlocks) {
        this.x = x;
        this.y = y;
        init(codeBlocks);
    }

    private void init(List<CodeBlock> codeBlocks) {
        grid = new int[x][y];



    }
}
