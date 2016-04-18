package com.braintreepayments.eventhandlers;

import com.braintreepayments.actions.CaretMovementAction;
import com.braintreepayments.actions.EditorAction;
import com.braintreepayments.service.WebsocketService;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
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

        caret = (Caret) dataContext.getData(CommonDataKeys.CARET.getName());
        System.out.println(caret.getLogicalPosition());

        LogicalPosition currentPosition = editor.getCaretModel().getLogicalPosition();
        int newLine = currentPosition.line;
        int newColumn = currentPosition.column;

        if (mType == 0) {
            newLine++;
        } else if ((mType & TYPE_CARET_UP) > 0) {
            newLine = Math.max(newLine-1, 0);
        } else if ((mType & TYPE_CARET_LEFT) > 0) {
            newColumn = Math.max(newColumn-1, 0);
        } else if ((mType  & TYPE_CARET_RIGHT) > 0) {
            newColumn++;
        }

        EditorAction action = new CaretMovementAction(newLine, newColumn);
        if (mWebsocketService.isOpen()) {
            mWebsocketService.sendAction(action);
        }
        action.run(editor);
    }
}
