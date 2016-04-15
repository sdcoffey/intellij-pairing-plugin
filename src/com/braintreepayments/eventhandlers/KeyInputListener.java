package com.braintreepayments.eventhandlers;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import org.jetbrains.annotations.NotNull;

public class KeyInputListener implements TypedActionHandler {


    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {
        int currentOffset = editor.getCaretModel().getCurrentCaret().getOffset();
        editor.getDocument().insertString(currentOffset, Character.toString(charTyped));
        editor.getCaretModel().moveToOffset(currentOffset + 1);

        System.out.println(charTyped);
    }
}
