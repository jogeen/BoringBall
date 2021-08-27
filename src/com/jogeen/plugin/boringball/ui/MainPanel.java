package com.jogeen.plugin.boringball.ui;

import com.jogeen.plugin.boringball.model.Ball;
import com.jogeen.plugin.boringball.model.CodeBlock;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @Author jogeen
 * @Date 19:56 2021/8/19
 * @Version v1.0
 * @Description
 */
public class MainPanel extends JPanel {

    private List<CodeBlock> codeBlocks;
    private Ball ball;
    Rectangle visibleArea;
    //Image img;


    public MainPanel(int width, int height, List<CodeBlock> codeBlocks, Ball ball, Rectangle visibleArea) {

        this.setLocation(0, 0);//面板的位置
        this.setSize(width, height);//面板的宽,高
        this.setOpaque(false);
        this.codeBlocks = codeBlocks;
        this.ball = ball;
        this.visibleArea = visibleArea;
        //img = new ImageIcon(MainPanel.class.getClassLoader().getResource("/img/heart.png")).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
/*        for (CodeBlock codeBlock : codeBlocks) {
            Rectangle rectangle = codeBlock.getRectangle();
            g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        g.drawRect(visibleArea.x, visibleArea.y, visibleArea.width, visibleArea.height);*/
        g.setColor(ball.color);
        g.fillOval(ball.x, ball.y, ball.r, ball.r);
        //g.drawImage(img, visibleArea.x + ball.x, visibleArea.y + ball.y, ball.r, ball.r, null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
