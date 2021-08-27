package com.jogeen.plugin.boringball.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerAdapter;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @Author jogeen
 * @Date 10:37 2021/8/13
 * @Version v1.0
 * @Description
 */
public class PonyoProjectManagerListener extends ProjectManagerAdapter {


    ImageIcon i;
    JLabel lable;
    JPanel jPanel;

    @Override
    public void projectOpened(@NotNull Project project) {
        MessageBusConnection connection = project.getMessageBus().connect();
        connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
            @Override
            public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                super.fileOpened(source, file);

                System.out.println("fileOpened" + file.getName());
                //initDocument(source.getProject(), FileDocumentManager.getInstance().getDocument(file));
            }

            @Override
            public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                super.fileClosed(source, file);
                System.out.println("fileClosed" + file.getName());
            }

            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
 /*               BufferedImage areaImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_BGR);
                Graphics2D areaGraphics = areaImage.createGraphics();
                areaGraphics.setBackground(new Color(0x00FFFFFF, true));
                areaGraphics.clearRect(0, 0, 100 * 2, 100 * 2);
                areaGraphics.setColor(Color.blue);
                areaGraphics.fillRect(0, 0, 100,100);*/

                i = new ImageIcon("d:\\0.gif");
                lable = new JLabel(i);
                jPanel = initComboPanel();
                jPanel.add(lable);

                super.selectionChanged(event);
                VirtualFile newFile = event.getNewFile();
                System.out.println("selectionChanged" + newFile.getName());
                Editor selectedTextEditor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                int lineHeight = selectedTextEditor.getLineHeight();
                int verticalScrollOffset = selectedTextEditor.getScrollingModel().getVerticalScrollOffset();
                int j = (verticalScrollOffset - 1) / (lineHeight) + 1;

                JComponent contentJComponent = selectedTextEditor.getContentComponent();
                System.out.println("------------>" + j);

/*
                JViewport jvp = (JViewport) contentJComponent.getParent();
                jvp.addChangeListener(e -> addComboLabel(contentJComponent, -contentJComponent.getX(), -contentJComponent.getY()));
*/

                addComboLabel(contentJComponent, -contentJComponent.getX(), -contentJComponent.getY());


                /*selectedTextEditor.addEditorMouseMotionListener(new EditorMouseMotionListener() {
                    @Override
                    public void mouseMoved(@NotNull EditorMouseEvent e) {
                        System.out.println("mouseMoved");
                    }

                    @Override
                    public void mouseDragged(@NotNull EditorMouseEvent e) {
                        System.out.println("mouseDragged");
                    }
                });*/
            }
        });


    }

    private JPanel initComboPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(null);
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());

        return panel;
    }

    private void addComboLabel(JComponent contentComponent, int x, int y) {
        contentComponent.setLayout(null);
        contentComponent.remove(jPanel);
        //jPanel.setBackground(Color.BLUE);//面板背景色
        jPanel.setLocation(0, 0);//面板的位置
        jPanel.setSize(contentComponent.getWidth(), contentComponent.getHeight());//面板的宽,高
        contentComponent.add(jPanel);
    }
}
