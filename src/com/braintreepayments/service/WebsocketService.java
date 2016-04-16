package com.braintreepayments.service;

import com.braintreepayments.actions.EditorAction;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebsocketService extends WebSocketClient {

    public static final String BASE_URL = "ws://localhost:4000";

    private static WebsocketService sWebSocketService;
    private String mSessionId;

    public static WebsocketService connect(String sessionId) {
        if (sWebSocketService == null) {
            URI uri = URI.create(String.format("%s/%s", BASE_URL, sessionId));
            sWebSocketService = new WebsocketService(uri);
        }
        return sWebSocketService;
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
    public void onMessage(String s) {
        // todo: deserialize event, add to event queue
    }

    @Override
    public void onClose(int i, String s, boolean b) {
    }

    @Override
    public void onError(Exception e) {
    }
}
