package com.braintreepayments.actions;

import com.intellij.openapi.editor.Editor;
import org.apache.http.util.TextUtils;

public abstract class EditorAction {

    /**
     * @return A string describing this action which may be sent over the wire
     */
    public abstract String serialize();

    /**
     * Reconstructs this object from serialized params
     *
     * @param data Serialized form of this action, as created by {@link EditorAction#serialize()}
     *
     *             Serialization format is a one letter abbreviation of the action, followed by a comma separated
     *             list of parameters. Subclasses should be able to reconstruct themselves with no additional context
     *
     *             E.g. move cursor 1 space to the left: m0,-1
     */
    protected abstract void deserializeInternal(String data);

    /**
     * Subclasses must implement this method to actually run the action
     */
    public abstract void run(Editor editor);

    /**
     * Returns a new {@link EditorAction} based on the serialized string
     *
     * @param serialization Serialized wire format of this action
     * @return
     */
    public EditorAction deserialize(String serialization) {
        if (TextUtils.isEmpty(serialization)) {
            return null;
        }

        String data = serialization.substring(2, serialization.length()-1);
        char actionKey = serialization.charAt(0);
        EditorAction action = null;
        switch (actionKey) {
            case 'm':
                action = new CaretMovementAction();
                action.deserializeInternal(data);
                break;
        }

        return action;
    }
}
