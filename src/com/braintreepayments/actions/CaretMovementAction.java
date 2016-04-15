package com.braintreepayments.actions;

import com.intellij.openapi.editor.Editor;

public class CaretMovementAction extends EditorAction {

    private int mColumnShift;
    private int mLineShift;

    public CaretMovementAction(int lineShift, int columnShift) {
        mColumnShift = columnShift;
        mLineShift = lineShift;
    }

    protected CaretMovementAction() {}

    @Override
    public String serialize() {
        return String.format("m%d,%d", mLineShift, mColumnShift);
    }

    @Override
    public void deserializeInternal(String serizliation) {

    }

    @Override
    public void run(Editor editor) {
        editor.getCaretModel().getCurrentCaret().moveCaretRelatively(mColumnShift, mLineShift, false, true);
    }
}