package com.braintreepayments.actions;

import com.intellij.openapi.editor.Editor;

public class KeyInputAction extends  EditorAction {

    private String mInput;
    private int mOffset;

    public KeyInputAction(int offset, String input) {
        mOffset = offset;
        mInput = input;
    }

    @Override
    public String serialize() {
        return String.format("a%d,%s", mOffset, mInput);
    }

    @Override
    protected void deserializeInternal(String data) {
        String[] parts = data.split(",");
        mOffset = Integer.valueOf(parts[0]);
        mInput = parts[1];
    }

    @Override
    public void run(Editor editor) {
        editor.getDocument().insertString(mOffset, mInput);
        editor.getCaretModel().moveToOffset(mOffset + mInput.length());
    }
}
