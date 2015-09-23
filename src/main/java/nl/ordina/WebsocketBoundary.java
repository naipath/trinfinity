package nl.ordina;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/actions")
public class WebsocketBoundary {

    @Inject
    private Game game;

    @OnOpen
    public void onOpen(Session session) {
        game.addUser(session);
    }

    @OnMessage
    public void onMessage(Session s, String message) {
        game.addOccupation(message, s.getId());
    }

    @OnClose
    public void onClose(Session s) {
        game.clearBord();
        game.removeUser(s);
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
