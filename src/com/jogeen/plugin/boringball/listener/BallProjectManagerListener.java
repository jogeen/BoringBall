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
import com.jogeen.plugin.boringball.service.BallService;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @Author jogeen
 * @Date 10:37 2021/8/13
 * @Version v1.0
 * @Description
 */
public class BallProjectManagerListener extends ProjectManagerAdapter {

    JPanel panel;


    @Override
    public void projectOpened(@NotNull Project project) {
        MessageBusConnection connection = project.getMessageBus().connect();
        connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
            @Override
            public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                super.fileOpened(source, file);
                System.out.println("fileOpened" + file.getName());
            }

            @Override
            public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                super.fileClosed(source, file);
                System.out.println("fileClosed" + file.getName());
            }

            @Override
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {

                super.selectionChanged(event);
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                BallService ballService = project.getComponent(BallService.class);
                ballService.setEditor(editor);

      /*              JComponent contentComponent = editor.getContentComponent();
                Document document = editor.getDocument();

                int fontSize = contentComponent.getFont().getSize();//??????
                int lineHeight = editor.getLineHeight();//??????
                int width = contentComponent.getWidth();//??????????????????
                int height = contentComponent.getHeight();//??????????????????


                contentComponent.setLayout(null);
                if(panel!=null){
                    contentComponent.remove(panel);
                }
                panel  = new JPanel();
                panel.setBackground(Color.BLUE);//???????????????
                panel.setLocation(0, 0);//???????????????
                panel.setSize(width, height);//????????????,???
                panel.setOpaque(true);
                contentComponent.add(panel);

                int verticalScrollOffset = editor.getScrollingModel().getVerticalScrollOffset();
                int j = (verticalScrollOffset - 1) / (lineHeight) + 1;

                System.out.println("------------>" + j);*/

/*                JViewport jvp = (JViewport) contentComponent.getParent();
                jvp.addChangeListener(e -> {
                    //TODO??????
                });*/
            }
        });


    }


}
