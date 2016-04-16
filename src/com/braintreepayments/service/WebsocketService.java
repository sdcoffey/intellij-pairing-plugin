package com.braintreepayments.service;

import com.braintreepayments.actions.EditorAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebsocketService extends WebSocketClient {

    public static final String BASE_URL = "ws://localhost:4000";

    private String mSessionId;
    private Project mProject;

    public WebsocketService(String sessionId, Project project) {
        super(URI.create(String.format("%s/%s", BASE_URL, sessionId)));
        mSessionId = sessionId;
        mProject = project;
        connect();
    }

    protected WebsocketService(URI serverURI) {
        super(serverURI);
    }

    public synchronized void sendAction(EditorAction action) {
        send(action.serialize());
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("connected!");
    }

    @Override
    public void onMessage(final String message) {
        System.out.println(message);
        WriteCommandAction.runWriteCommandAction(mProject, new Runnable() {
            @Override
            public void run() {
                EditorAction action = EditorAction.deserialize(message);
                Editor currentEditor = FileEditorManager.getInstance(mProject).getSelectedTextEditor();
                if (action != null) {
                    action.run(currentEditor);
                }
            }
        });
    }

    @Override
    public void onClose(int i, String s, boolean b) {
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
