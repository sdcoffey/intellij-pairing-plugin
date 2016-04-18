package com.braintreepayments.actions;

import com.intellij.openapi.editor.Editor;

public class TextDeleteAction extends EditorAction {

    private int mStartOffset;
    private int mEndOffset;

    public TextDeleteAction(String data) {
        deserializeInternal(data);
    }

    public TextDeleteAction(int start, int end) {
        mStartOffset = start;
        mEndOffset = end;
    }

    @Override
    public String serialize() {
        return String.format("d%d,%d", mStartOffset, mEndOffset);
    }

    @Override
    protected void deserializeInternal(String data) {
        String[] parts = data.split(",");
        mStartOffset = Integer.valueOf(parts[0]);
        mEndOffset = Integer.valueOf(parts[1]);
    }

    @Override
    public void run(Editor editor) {
        if (mStartOffset < 0) {
            mStartOffset = 0;
        }
        if (mEndOffset > editor.getDocument().getTextLength()) {
            mEndOffset = editor.getDocument().getTextLength();
        }
        editor.getDocument().deleteString(mStartOffset, mEndOffset);
    }
}
