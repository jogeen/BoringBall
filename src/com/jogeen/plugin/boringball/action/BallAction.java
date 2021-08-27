package com.jogeen.plugin.boringball.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.jogeen.plugin.boringball.service.BallService;

/**
 * @Author jogeen
 * @Date 10:27 2021/8/19
 * @Version v1.0
 * @Description
 */
public class BallAction extends AnAction {


    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        assert project != null;
        BallService ballService = project.getComponent(BallService.class);
        ballService.switchStatus();

    }
}
