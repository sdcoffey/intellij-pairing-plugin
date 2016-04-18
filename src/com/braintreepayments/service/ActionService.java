package com.braintreepayments.service;

import com.braintreepayments.actions.EditorAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;

public class ActionService {

    private static ActionService sActionService;
    private Project mProject;

    private ActionService() {}

    public static ActionService getInstance() {
        if (sActionService == null) {
            sActionService = new ActionService();
        }
        return sActionService;
    }

    public synchronized void doAction(final EditorAction action, final Project project) {
        WriteCommandAction.runWriteCommandAction(project, new Runnable() {
            @Override
            public void run() {
                Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                action.run(editor);
            }
        });
    }
}
