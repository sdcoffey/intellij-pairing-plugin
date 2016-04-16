package com.braintreepayments.eventhandlers;

import com.braintreepayments.actions.CaretMovementAction;
import com.braintreepayments.actions.EditorAction;
import com.braintreepayments.service.WebsocketService;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import org.jetbrains.annotations.Nullable;

public class CaretMovementHandler extends EditorActionHandler {

    public static final long TYPE_CARET_DOWN = 0;
    public static final long TYPE_CARET_UP = 1 << 1;
    public static final long TYPE_CARET_LEFT = 1 << 2;
    public static final long TYPE_CARET_RIGHT = 1 << 3;

    private long mType;
    private WebsocketService mWebsocketService;

    public CaretMovementHandler(long type, WebsocketService service) {
        mType = type;
        mWebsocketService = service;
    }

    @Override
    protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
        super.doExecute(editor, caret, dataContext);

        int lineShift = 0;
        int columnShift = 0;

        if (mType == 0) {
            lineShift = 1;
        } else if ((mType & TYPE_CARET_UP) > 0) {
            lineShift = -1;
        } else if ((mType & TYPE_CARET_LEFT) > 0) {
            columnShift = -1;
        } else if ((mType  & TYPE_CARET_RIGHT) > 0) {
            columnShift = 1;
        }

        EditorAction action = new CaretMovementAction(lineShift, columnShift);
        mWebsocketService.sendAction(action);
        action.run(editor);
    }
}
