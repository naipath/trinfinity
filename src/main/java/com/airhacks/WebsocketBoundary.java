package com.airhacks;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "trinfinty")
public class WebsocketBoundary {

    @Inject
    RandomQuoteService randomQuoteService;

    @Inject
    BordLogicService bordLogicService;

    @OnOpen
    public void onOpen(Session session) {
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        bordLogicService.processMove(message)
                .zipWith(randomQuoteService.getQuote(), (s, s2) -> s + " | " + s2)
                .subscribe(s1 -> session.getAsyncRemote().sendText(s1));
    }

    @OnClose
    public void onClose() {
    }

    @OnError
    public void onError(Exception e) {
        e.printStackTrace();
    }
}
