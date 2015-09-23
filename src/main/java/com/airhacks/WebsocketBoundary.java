package com.airhacks;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/actions")
public class WebsocketBoundary {

    @Inject
    Sessions sessions;

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String message) {
        sessions.sendMessageToAll(message);
    }

    @OnClose
    public void onClose(Session s) {
        sessions.remove(s);
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
