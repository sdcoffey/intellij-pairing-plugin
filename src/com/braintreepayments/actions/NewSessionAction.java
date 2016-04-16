package com.braintreepayments.actions;

import com.braintreepayments.eventhandlers.CaretMovementHandler;
import com.braintreepayments.eventhandlers.KeyInputListener;
import com.braintreepayments.service.WebsocketService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;

public class NewSessionAction extends AnAction {

    private KeyInputListener mKeyInputListener;

    public NewSessionAction() {
        super("Sample Action");
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        EditorActionManager manager = EditorActionManager.getInstance();
        WebsocketService service = WebsocketService.connect("testSession");
        service.connect();

        mKeyInputListener = new KeyInputListener(service);
        manager.setActionHandler(IdeActions.ACTION_EDITOR_MOVE_CARET_DOWN, new CaretMovementHandler(CaretMovementHandler.TYPE_CARET_DOWN, service));
        manager.setActionHandler(IdeActions.ACTION_EDITOR_MOVE_CARET_LEFT, new CaretMovementHandler(CaretMovementHandler.TYPE_CARET_LEFT, service));
        manager.setActionHandler(IdeActions.ACTION_EDITOR_MOVE_CARET_UP, new CaretMovementHandler(CaretMovementHandler.TYPE_CARET_UP, service));
        manager.setActionHandler(IdeActions.ACTION_EDITOR_MOVE_CARET_RIGHT, new CaretMovementHandler(CaretMovementHandler.TYPE_CARET_RIGHT, service));
        manager.getTypedAction().setupHandler(mKeyInputListener);
    }
}
