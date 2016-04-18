package com.braintreepayments.eventhandlers;

import com.braintreepayments.actions.EditorAction;
import com.braintreepayments.actions.TextDeleteAction;
import com.braintreepayments.service.ActionService;
import com.braintreepayments.service.WebsocketService;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import org.jetbrains.annotations.Nullable;

public class TextDeleteHandler extends EditorActionHandler {

    private WebsocketService mService;

    public TextDeleteHandler(WebsocketService service) {
        mService = service;
    }

    @Override
    protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
        super.doExecute(editor, caret, dataContext);

        caret = (Caret) dataContext.getData(CommonDataKeys.CARET.getName());

        EditorAction action;
        if (caret.getSelectionEnd() - caret.getSelectionStart() > 0) {
            action = new TextDeleteAction(caret.getSelectionStart(), caret.getSelectionEnd());
        } else {
            action = new TextDeleteAction(caret.getOffset() - 1, caret. getOffset());
        }
        mService.sendAction(action);
        ActionService.getInstance().doAction(action, editor.getProject());
    }
}
