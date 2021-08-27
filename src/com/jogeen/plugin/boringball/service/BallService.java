package com.jogeen.plugin.boringball.service;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.editor.Editor;
import com.jogeen.plugin.boringball.model.Ball;
import com.jogeen.plugin.boringball.model.Board;
import com.jogeen.plugin.boringball.model.CodeBlock;
import com.jogeen.plugin.boringball.ui.MainPanel;
import com.jogeen.plugin.boringball.utils.CodeBlockUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @Author jogeen
 * @Date 10:31 2021/8/19
 * @Version v1.0
 * @Description
 */
@Service
public class BallService {

    boolean isOpen = false;
    Editor editor;//当前的编辑器
    JPanel panel;// 显示面板
    List<CodeBlock> codeBlocks; //代码块
    Ball ball; //球状
    Thread thread;
    Board board;
    Rectangle visibleArea;
    JLabel mComboLabel, mMaxComboLabel;
    JPanel mComboPanel;
    int mClickCombo;

    public void switchStatus() {
        isOpen = !isOpen;
        if (isOpen) {
            start();
        } else {
            stop();
        }
    }

    public void start() {
        initPanel();
        startTimeClock();
    }

    public void stop() {
        removePanel();
        thread = null;
    }

    private void removePanel() {
        JComponent contentComponent = editor.getContentComponent();
        if (panel != null) {
            contentComponent.remove(panel);
        }
        if (mComboPanel != null) {
            contentComponent.remove(mComboPanel);
        }
    }

    private void initPanel() {
        mMaxComboLabel = initMaxComboLabel();
        mComboLabel = initComboLabel();
        mComboPanel = initComboPanel();
        JComponent contentComponent = editor.getContentComponent();
        initBallPanel();
        //addComboLabel(contentComponent, -contentComponent.getX(), -contentComponent.getY());

    }


    private void initBallPanel() {

        JComponent contentComponent = editor.getContentComponent();
        visibleArea = editor.getScrollingModel().getVisibleArea();

        int width = contentComponent.getWidth();//编辑器的宽度
        int height = contentComponent.getHeight();//编辑器的高度
        contentComponent.setLayout(null);
        if (panel != null) {
            contentComponent.remove(panel);
        }

        codeBlocks = CodeBlockUtil.getCurrentScreenCodeBlocks(editor);//扫描代码块

        ball = new Ball(visibleArea.x + visibleArea.width - editor.getLineHeight(), visibleArea.y + visibleArea.height - editor.getLineHeight(), editor.getLineHeight() / 2, Color.RED, -1, -1);
        panel = new MainPanel(width, height, codeBlocks, ball, visibleArea);
        contentComponent.add(panel);

    }


    private void addComboLabel(JComponent contentComponent, int x, int y) {
        if (contentComponent != null && contentComponent.getParent() != null && mComboPanel != null && mMaxComboLabel != null && mComboLabel != null) {

            mMaxComboLabel.setText(String.valueOf("Bam"));
            panel.remove(mMaxComboLabel);
            panel.remove(mComboLabel);
            panel.add(mMaxComboLabel, BorderLayout.NORTH);
            panel.add(mComboLabel, BorderLayout.CENTER);

        }
    }


    private JPanel initComboPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(null);
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        return panel;
    }

    private JLabel initMaxComboLabel() {
        //JLabel comboLabel = new JLabel(String.valueOf("Max " + Config.getInstance().state.MAX_CLICK_COMBO));
        JLabel comboLabel = new JLabel(String.valueOf("Bam"));
        comboLabel.setHorizontalAlignment(SwingConstants.CENTER);
        comboLabel.setBackground(new Color(0x00FFFFFF, true));
        comboLabel.setForeground(Color.GREEN);

        try {
            InputStream fontInputStream = getClass().getResourceAsStream("/font/PressStart2P-Regular.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontInputStream);
            font = font.deriveFont(Font.BOLD, 30f);
            comboLabel.setFont(font);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            comboLabel.setFont(new Font("Default", Font.BOLD, 30));
        }

        return comboLabel;
    }

    private JLabel initComboLabel() {
        JLabel comboLabel = new JLabel("0");
        comboLabel.setHorizontalAlignment(SwingConstants.CENTER);
        comboLabel.setBackground(new Color(0x00FFFFFF, true));
        comboLabel.setForeground(Color.GREEN);

        try {
            InputStream fontInputStream = getClass().getResourceAsStream("/font/PressStart2P-Regular.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontInputStream);
            font = font.deriveFont(Font.BOLD, 30f);
            comboLabel.setFont(font);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            comboLabel.setFont(new Font("Default", Font.BOLD, 30));
        }
        return comboLabel;
    }


    public void startTimeClock() {
        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (isOpen) {
                        ball.move();
                        collisionDetection();
                        panel.repaint();
                        try {
                            TimeUnit.MICROSECONDS.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            thread.start();
        }
    }


    public Editor getEditor() {
        return editor;
    }

    public BallService setEditor(Editor editor) {
        this.editor = editor;
        return this;
    }


    public void collisionDetection() {
        if (ball.x <= visibleArea.x || ball.x >= visibleArea.x+visibleArea.width) {
            ball.rebound(Ball.REBOUND_TYPE_X);
            return;
        }
        if (ball.y <= visibleArea.y || ball.y >= visibleArea.y+visibleArea.height) {
            ball.rebound(Ball.REBOUND_TYPE_Y);
            return;
        }
        for (CodeBlock codeBlock : codeBlocks) {
            boolean collisionWithRect = isCollisionWithRect(ball.x, ball.y, ball.r, ball.r,
                    codeBlock.x, codeBlock.y, codeBlock.width, codeBlock.height);
            if (collisionWithRect) {
                mComboLabel.setText(String.valueOf(mClickCombo++));
                if (ball.yv < 0 && ball.y == codeBlock.y + codeBlock.height || ball.yv > 0 && ball.y + ball.r == codeBlock.y) {
                    ball.rebound(Ball.REBOUND_TYPE_Y);
                }
                if (ball.xv < 0 && ball.x == codeBlock.x + codeBlock.width || ball.xv > 0 && ball.x + ball.r == codeBlock.x) {
                    ball.rebound(Ball.REBOUND_TYPE_X);
                }
            }

        }

    }

    //碰撞检测
    public boolean isCollisionWithRect(int x1, int y1, int w1, int h1,
                                       int x2, int y2, int w2, int h2) {
        if (x1 > x2 && x1 > x2 + w2) {
            return false;
        } else if (x1 < x2 && x1 + w1 < x2) {
            return false;
        } else if (y1 > y2 && y1 > y2 + h2) {
            return false;
        } else if (y1 < y2 && y1 + h1 < y2) {
            return false;
        }
        return true;
    }

}
