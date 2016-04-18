package com.braintreepayments.eventhandlers;

import com.braintreepayments.actions.EditorAction;
import com.braintreepayments.actions.KeyInputAction;
import com.braintreepayments.service.ActionService;
import com.braintreepayments.service.WebsocketService;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import org.jetbrains.annotations.NotNull;

public class KeyInputListener implements TypedActionHandler {

    private WebsocketService mSerivce;

    public KeyInputListener(WebsocketService service) {
        mSerivce = service;
    }

    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {
        int currentOffset = editor.getCaretModel().getCurrentCaret().getOffset();
        EditorAction action = new KeyInputAction(currentOffset, Character.toString(charTyped));
        if (mSerivce.isOpen()) {
            mSerivce.sendAction(action);
        }
        ActionService.getInstance().doAction(action, editor.getProject());
    }
}
