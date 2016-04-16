package com.braintreepayments.actions;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;

public class CaretMovementAction extends EditorAction {

    private int mColumn;
    private int mLine;

    public CaretMovementAction(int lineShift, int columnShift) {
        mColumn = columnShift;
        mLine = lineShift;
    }

    protected CaretMovementAction() {}

    @Override
    public String serialize() {
        return String.format("m%d,%d", mLine, mColumn);
    }

    @Override
    public void deserializeInternal(String serizliation) {
        String[] parts = serizliation.split(",");
        mLine = Integer.valueOf(parts[0]);
        mColumn = Integer.valueOf(parts[1]);
    }

    @Override
    public void run(Editor editor) {
        LogicalPosition newPosition = new LogicalPosition(mLine, mColumn);
        editor.getCaretModel().moveToLogicalPosition(newPosition);
    }
}
