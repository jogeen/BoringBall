package com.jogeen.plugin.boringball;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.ProjectManager;
import com.jogeen.plugin.boringball.listener.BallProjectManagerListener;

/**
 * @Author jogeen
 * @Date 10:29 2021/8/13
 * @Version v1.0
 * @Description
 */
public class BoringBallApplicationPlugin implements ApplicationComponent {
    @Override
    public void initComponent() {
        ProjectManager.getInstance().addProjectManagerListener(new BallProjectManagerListener());
    }
}
