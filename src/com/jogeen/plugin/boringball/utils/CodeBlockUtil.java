package com.jogeen.plugin.boringball.utils;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.text.StringUtil;
import com.jogeen.plugin.boringball.model.CodeBlock;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author jogeen
 * @Date 16:01 2021/8/19
 * @Version v1.0
 * @Description
 */
public final class CodeBlockUtil {

    static AffineTransform atf = new AffineTransform();
    static FontRenderContext frc = new FontRenderContext(atf, true, true);

    public static CodeBlock createCodeBlock(int logicRow, int contentRow, String rawText, Rectangle scrollEditorRectangle, int lineHeight, int fontSize) {//123123123123123123
        CodeBlock codeBlock = new CodeBlock();
        codeBlock.logicRow = logicRow;
        codeBlock.contentRow = contentRow;
        codeBlock.text = rawText;


        int spaceWidth = getStringWidth(" ", fontSize);
        int startNoEmptyIndexOfString = startNoEmptyIndexOfString(rawText);

        //起始x坐标
        if (startNoEmptyIndexOfString * spaceWidth > scrollEditorRectangle.x) {//如果行首的空格宽度大于水平滚动偏移
            codeBlock.x = scrollEditorRectangle.x + startNoEmptyIndexOfString * spaceWidth - scrollEditorRectangle.x;
            codeBlock.width = getStringWidth(rawText.trim(), fontSize);//宽度
        } else {
            codeBlock.x = scrollEditorRectangle.x + 0;
            codeBlock.width = getStringWidth(rawText.trim(), fontSize) - scrollEditorRectangle.x;//宽度
        }


        int firstLineOffset = scrollEditorRectangle.y % lineHeight;//首行偏移量
        int offsetLine = firstLineOffset == 0 ? 0 : 1;
        if (logicRow == 1) {
            codeBlock.y = scrollEditorRectangle.y + 0;
            codeBlock.height = firstLineOffset == 0 ? lineHeight : firstLineOffset;
        } else {
            codeBlock.y = scrollEditorRectangle.y + firstLineOffset + (logicRow - 1 - offsetLine) * lineHeight;
            codeBlock.height = lineHeight;
        }
        return codeBlock;
    }

    public static int startNoEmptyIndexOfString(String text) {
        char[] value = text.toCharArray();
        int len = value.length;
        int st = 0;
        char[] val = value;

        while ((st < len) && (val[st] <= ' ')) {
            st++;
        }
        return st;

    }

    public static int endNoEmptyIndexOfString(String text) {
        char[] value = text.toCharArray();
        int len = value.length;
        int st = 0;
        char[] val = value;

        while ((st < len) && (val[len - 1] <= ' ')) {
            len--;
        }
        return len;
    }

    public static int getStringWidth(String str, int fontSize) {

        if (str == null || str.isEmpty() || fontSize == 0) {
            return 0;
        }
        String s = str.replaceAll("[\u4e00-\u9fa5]", "");
        //英文字符宽度和中文字符宽度
        return (s.length()) * (fontSize / 2 + 1) + (str.length() - s.length()) * fontSize;

    }

    public static List<CodeBlock> getCurrentScreenCodeBlocks(Editor editor) {
        String text = editor.getDocument().getText();
        String[] lines = text.split("\\n");
        int verticalScrollOffset = editor.getScrollingModel().getVerticalScrollOffset();//垂直偏移像素
        int horizontalScrollOffset = editor.getScrollingModel().getHorizontalScrollOffset();//水平偏移像素

        Rectangle scrollEditorRectangle = editor.getScrollingModel().getVisibleArea();
        JComponent contentComponent = editor.getContentComponent();
        int fontSize = contentComponent.getFont().getSize();

        int lineHeight = editor.getLineHeight();//行高
        int startLine = (verticalScrollOffset) / (lineHeight) + 1;
        int endLine = (scrollEditorRectangle.height / lineHeight) + startLine;

        List list = new ArrayList<>();
        int logicRow = 0;
        for (int i = startLine; i <= endLine; i++) {
            logicRow++;
            if (i < lines.length && !StringUtil.isEmpty(lines[i - 1].trim())) {
                CodeBlock codeBlock = CodeBlockUtil.createCodeBlock(logicRow, i, lines[i - 1], scrollEditorRectangle, lineHeight, fontSize);
                list.add(codeBlock);
            }
        }
        return list;
    }


}
